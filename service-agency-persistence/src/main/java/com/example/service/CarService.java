package com.example.service;

import com.example.repository.CarOwnerRepository;
import com.example.domain.Car;
import com.example.domain.CarOwner;
import com.example.domain.Owner;
import com.example.exception.*;
import com.example.receiverService.OwnerReceiverService;
import com.example.repository.CarRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CarService {
    private final CarRepository repo;
    private final CarOwnerRepository carOwnerRepo;
    private final OwnerReceiverService ownerReceiverService;

    @Autowired
    public CarService(CarRepository repo, CarOwnerRepository carOwnerRepo, OwnerReceiverService ownerReceiverService) {
        this.repo = repo;
        this.carOwnerRepo = carOwnerRepo;
        this.ownerReceiverService = ownerReceiverService;
    }

    public List<Car> getAll(final int page, final int pageSize) {
        log.info("getting car on relevant page");

        Sort sort = Sort.by(Sort.Direction.ASC, "id");
        Pageable pageable = PageRequest.of(page, pageSize, sort);

        return repo.findAll(pageable)
                .stream()
                .collect(Collectors.toList());
    }

    public Car getById(final long id) {
        log.info("getting car by id");

        return repo.findById(id)
                .orElseThrow(() -> new CarNotFoundException(String.format("car with id:%d not found", id)));
    }

    public Car getByPersonalNoAndLicensePlate(final String personalNo, final String licensePlate) {
        log.info("getting car owner by personalNo and car with license plate");

        if (carOwnerRepo.findCarOwnerByPersonalNo(personalNo).isEmpty()) {
            throw new OwnerNotFoundException(String.format("owner with personalNo:%s does not exist", personalNo));
        }

        if (repo.findCarByLicensePlate(licensePlate).isEmpty()) {
            throw new CarNotFoundException(String.format("car with license plate:%s not found", licensePlate));
        }

        return repo.findCarOwnerByPersonalNo(licensePlate, personalNo)
                .orElseThrow(() -> new RegistrationNotFound(String.format("registration with personalNo:%s and license plate:%s not found", personalNo, licensePlate)));
    }

    public Car create(final Car car, final String personalNo) throws URISyntaxException {
        log.info("registered new car");

        Owner owner = ownerReceiverService.getOwnerByPersonalNo(personalNo);
        if (owner == null) {
            throw new OwnerNotFoundException(String.format("owner with personalNo:%s does not exist", personalNo));
        }

        if (carOwnerRepo.findCarOwnerByPersonalNo(personalNo).isPresent()) {
            CarOwner carOwner = carOwnerRepo.findCarOwnerByPersonalNo(personalNo).get();
            if (repo.findCarByVinCode(car.getVinCode()).isPresent()) {
                Car existedCar = repo.findCarByVinCode(car.getVinCode()).get();
                existedCar.setCarOwner(carOwner);

                return repo.save(existedCar);
            }

            car.setCarOwner(carOwner);

            return repo.save(car);
        }

        CarOwner carOwner = new CarOwner();
        registerCarOwner(carOwner, owner);
        carOwnerRepo.save(carOwner);

        if (repo.findCarByVinCode(car.getVinCode()).isPresent()) {
            Car existedCar = repo.findCarByVinCode(car.getVinCode()).get();
            existedCar.setCarOwner(carOwner);

            return repo.save(existedCar);
        }

        car.setCarOwner(carOwner);

        return repo.save(car);
    }

    private void registerCarOwner(CarOwner carOwner, Owner owner) {
        carOwner.setFirstName(owner.getFirstName());
        carOwner.setLastName(owner.getLastName());
        carOwner.setBirthday(owner.getBirthday());
        carOwner.setPersonalNo(owner.getPersonalNo());
    }

    public Car update(final long id, final Car car) {
        log.info("updating car");

        if (!repo.existsById(id)) {
            log.debug(String.format("car with id:%d does not exist", id));
            throw new CarNotFoundException(String.format("car with id:%d not found", id));
        }

        Optional<Car> carByLicensePlate = repo.findCarByLicensePlate(car.getLicensePlate());
        if (carByLicensePlate.isPresent() && carByLicensePlate.get().getId() != id) {
            log.debug(String.format("car with license plate:%s already exists", car.getLicensePlate()));
            throw new IllegalCarLicensePlateException(String.format("car with license plate:%s already exists", car.getLicensePlate()));
        }

        Optional<Car> carByVinCode = repo.findCarByVinCode(car.getVinCode());
        if (carByVinCode.isPresent() && carByVinCode.get().getId() != id) {
            log.debug(String.format("car with vin code:%s already exists", car.getVinCode()));
            throw new IllegalVinCodeException(String.format("car with vin code:%s already exists", car.getVinCode()));
        }

        car.setId(id);

        return repo.save(car);
    }

    public Car delete(final long id) {
        log.info("removing car");

        Optional<Car> car = repo.findById(id);
        car.ifPresent(c -> repo.deleteById(c.getId()));

        return car
                .orElseThrow(() -> new CarNotFoundException(String.format("car with id:%d not found", id)));
    }
}
