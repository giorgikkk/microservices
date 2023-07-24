package com.example.repository;

import com.example.domain.Insurer;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface InsurerRepository extends CrudRepository<Insurer, Long> {
    Optional<Insurer> findInsurerByPersonalNo(String personalNo);
}
