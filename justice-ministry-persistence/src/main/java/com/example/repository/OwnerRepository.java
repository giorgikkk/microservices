package com.example.repository;

import com.example.domain.Owner;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.Pattern;
import java.util.Optional;

@Repository
public interface OwnerRepository extends PagingAndSortingRepository<Owner, Long> {
    Optional<Owner> findByPersonalNo(@Pattern(regexp = "\\d{11}") String personalNo);
}
