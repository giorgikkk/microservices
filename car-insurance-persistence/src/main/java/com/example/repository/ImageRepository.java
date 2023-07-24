package com.example.repository;

import com.example.domain.Image;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ImageRepository extends CrudRepository<Image, Long> {
    @Query("SELECT img " +
            "FROM Image img " +
            "WHERE img.car.id=:id")
    List<Image> getImagesByCarId(long id);
}
