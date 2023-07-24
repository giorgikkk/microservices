package com.example.service;

import com.example.repository.InsuredCarRepository;
import com.example.repository.InsurerRepository;
import com.example.domain.Insurance;
import com.example.domain.InsuredCar;
import com.example.domain.Insurer;
import com.example.domain.Car;
import com.example.domain.CarOwner;
import com.example.domain.enums.InsuranceType;
import com.example.exception.*;
import com.example.receiverService.CarRecieverService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class InsuredCarService {
    private static final int INSURANCE_INIT_PERCENTAGE = 3;
    private static final int INSURANCE_INCREMENT_PERCENTAGE = 1;
    private static final int FRANCHISE_PERCENTAGE = 10;
    private final InsuredCarRepository repo;
    private final InsurerRepository insurerRepo;
    private final CarRecieverService carRecieverService;

    @Autowired
    public InsuredCarService(InsuredCarRepository repo, InsurerRepository insurerRepo, CarRecieverService carRecieverService) {
        this.repo = repo;
        this.insurerRepo = insurerRepo;
        this.carRecieverService = carRecieverService;
    }

    public List<InsuredCar> getAll(final int page, final int pageSize) {
        log.info("getting car on relevant page");

        Sort sort = Sort.by(Sort.Direction.ASC, "id");
        Pageable pageable = PageRequest.of(page, pageSize, sort);

        return repo.findAll(pageable)
                .stream()
                .collect(Collectors.toList());
    }

    public InsuredCar getById(final long id) {
        log.info("getting car by id");

        return repo.findById(id)
                .orElseThrow(() -> new InsuredCarNotFoundException(String.format("insured car with id:%d not found", id)));
    }

    public InsuredCar create(final String personalNo, final String licensePlate, final Insurance insurance) throws URISyntaxException {
        log.info("insured new car");

        if (repo.findInsuredCarByLicensePlate(licensePlate).isPresent()) {
            log.debug(String.format("car with license plate:%s already insured", licensePlate));
            throw new InsuranceAlreadyExistsException(String.format("car with license plate:%s already insured", licensePlate));
        }


        Car car = carRecieverService.getCarBy(personalNo, licensePlate);

        if (car == null) {
            throw new CarNotFoundException("car in service agency does not exist");
        }

        InsuredCar insuredCar = new InsuredCar();
        insure(insuredCar, car);

        double franchiseAmount = insurance.getFranchiseAmount().doubleValue();
        double insuranceAmount = insurance.getInsuranceAmount().doubleValue();
        if (franchiseAmount > insuranceAmount * INSURANCE_INIT_PERCENTAGE / 100) {
            throw new IllegalFranchiseAmountException("franchise amount should be less or equal than one percent of full insurance amount");
        }

        calculateInsurancePension(insuredCar, insuranceAmount, franchiseAmount, insurance);

        return repo.save(insuredCar);
    }

    private void insure(InsuredCar insuredCar, Car car) {
        insuredCar.setReleaseDate(car.getReleaseDate());
        insuredCar.setCarType(car.getCarType());
        insuredCar.setVinCode(car.getVinCode());
        insuredCar.setOdometerDisplay(car.getOdometerDisplay());
        insuredCar.setOdometerUnitType(car.getOdometerUnitType());
        insuredCar.setLicensePlate(car.getLicensePlate());

        Insurer insurer = new Insurer();
        CarOwner carOwner = car.getCarOwner();
        insurer.setFirstName(carOwner.getFirstName());
        insurer.setLastName(carOwner.getLastName());
        insurer.setBirthday(carOwner.getBirthday());
        insurer.setPersonalNo(carOwner.getPersonalNo());

        if (insurerRepo.findInsurerByPersonalNo(insurer.getPersonalNo()).isEmpty()) {
            insurerRepo.save(insurer);
        }

        insurer = insurerRepo.findInsurerByPersonalNo(insurer.getPersonalNo()).get();
        insuredCar.setInsurer(insurer);
    }

    private void calculateInsurancePension(InsuredCar car, double insuranceAmount, double franchiseAmount, Insurance insurance) {
        int carAge = getAge(car.getReleaseDate(), LocalDate.now());

        if (carAge >= 25) {
            throw new IllegalInsuranceException("car over 25 years can not be insured");
        }

        double insurancePension = insuranceAmount * INSURANCE_INIT_PERCENTAGE / 100;
        if (carAge >= 10) {
            insurancePension += insuranceAmount * INSURANCE_INCREMENT_PERCENTAGE / 100;
        }

        int ownerAge = getAge(car.getInsurer().getBirthday(), LocalDate.now());
        if (ownerAge >= 18 && ownerAge <= 24) {
            insurancePension += insuranceAmount * INSURANCE_INCREMENT_PERCENTAGE / 100;
        }

        if (insurance.getInsuranceType() == InsuranceType.FRANCHISE) {
            insurancePension -= franchiseAmount * FRANCHISE_PERCENTAGE / 100;
        } else {
            insurancePension += insuranceAmount * INSURANCE_INCREMENT_PERCENTAGE / 100;
        }

        insurance.setInsurancePension(BigDecimal.valueOf(insurancePension));
        car.setInsurance(insurance);
    }

    private int getAge(LocalDate startDate, LocalDate endDate) {
        return startDate.until(endDate).getYears();
    }

    public InsuredCar delete(final long id) {
        log.info("removing car");

        Optional<InsuredCar> car = repo.findById(id);
        car.ifPresent(c -> repo.deleteById(c.getId()));

        return car
                .orElseThrow(() -> new InsuredCarNotFoundException(String.format("insured car with id:%d not found", id)));
    }
}
