package com.example.repository;

import com.example.domain.CarOwner;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.Pattern;
import java.util.Optional;

@Repository
public interface CarOwnerRepository extends CrudRepository<CarOwner, Long> {
    Optional<CarOwner> findCarOwnerByPersonalNo(@Pattern(regexp = "\\d{11}") String personalNo);
}
