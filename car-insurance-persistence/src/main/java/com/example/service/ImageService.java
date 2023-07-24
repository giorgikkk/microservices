package com.example.service;

import com.example.repository.ImageRepository;
import com.example.repository.InsuredCarRepository;
import com.example.domain.Image;
import com.example.exception.ImageNotFoundException;
import com.example.exception.InsuredCarNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ImageService {
    private final ImageRepository repo;
    private final InsuredCarRepository insuredCarRepo;

    public ImageService(ImageRepository repo, InsuredCarRepository insuredCarRepo) {
        this.repo = repo;
        this.insuredCarRepo = insuredCarRepo;
    }

    public List<byte[]> getImages(final long id) {
        log.info(String.format("getting images of car by id:%d", id));
        log.debug("id:{}", id);

        if (!insuredCarRepo.existsById(id)) {
            throw new InsuredCarNotFoundException(String.format("insured car with id:%d not found", id));
        }

        List<Image> images = repo.getImagesByCarId(id);

        return images.stream()
                .map(i -> Base64.getDecoder().decode(i.getBase64File()))
                .collect(Collectors.toList());
    }

    public Image create(final Image image) {
        log.info("adding new image");

        return repo.save(image);
    }

    public Image delete(final long id) {
        log.info("removing image");
        log.debug("id:{}", id);

        Optional<Image> image = repo.findById(id);
        image.ifPresent(i -> repo.deleteById(i.getId()));

        return image
                .orElseThrow(() -> new ImageNotFoundException(String.format("image with id:%d not found", id)));
    }
}
