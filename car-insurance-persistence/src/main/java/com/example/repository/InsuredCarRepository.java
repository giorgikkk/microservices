package com.example.repository;

import com.example.domain.InsuredCar;
import org.springframework.data.repository.PagingAndSortingRepository;

import javax.validation.constraints.Pattern;
import java.util.Optional;

public interface InsuredCarRepository extends PagingAndSortingRepository<InsuredCar, Long> {
    Optional<InsuredCar> findInsuredCarByLicensePlate(@Pattern(regexp = "[A-Z]{2}-\\d{3}-[A-Z]{2}") String licensePlate);

    Optional<InsuredCar> findInsuredCarByVinCode(@Pattern(regexp = "(?=.*\\d|=.*[A-Z])(?=.*[A-Z])[A-Z0-9]{17}") String vinCode);
}
