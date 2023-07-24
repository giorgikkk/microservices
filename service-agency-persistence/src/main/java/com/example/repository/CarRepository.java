package com.example.repository;

import com.example.domain.Car;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.Pattern;
import java.util.Optional;

@Repository
public interface CarRepository extends PagingAndSortingRepository<Car, Long> {
    Optional<Car> findCarByLicensePlate(@Pattern(regexp = "[A-Z]{2}-\\d{3}-[A-Z]{2}") String licensePlate);

    Optional<Car> findCarByVinCode(@Pattern(regexp = "(?=.*\\d|=.*[A-Z])(?=.*[A-Z])[A-Z0-9]{17}") String vinCode);

    @Query("SELECT c " +
            "FROM Car c " +
            "WHERE c.licensePlate = :licensePlate " +
            "AND c.carOwner.personalNo = :personalNo")
    Optional<Car> findCarOwnerByPersonalNo(@Pattern(regexp = "[A-Z]{2}-\\d{3}-[A-Z]{2}") String licensePlate,
                                           @Pattern(regexp = "\\d{11}") String personalNo);
}
