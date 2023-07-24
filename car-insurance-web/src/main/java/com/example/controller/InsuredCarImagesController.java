package com.example.controller;

import com.example.domain.Image;
import com.example.service.ImageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("images")
@Slf4j
public class InsuredCarImagesController {
    private final ImageService service;

    public InsuredCarImagesController(ImageService service) {
        this.service = service;
    }

    @GetMapping("{id}")
    @Operation(summary = "get images")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "get owners", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "insured car not found", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "server error", content = {@Content(mediaType = "application/json")})})
    public List<byte[]> getAll(@PathVariable final long id) {
        log.info("getting all images for car with current id");
        log.debug("id:{}", id);

        return service.getImages(id);
    }

    @PostMapping
    @Operation(summary = "add image")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "get owners", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "server error", content = {@Content(mediaType = "application/json")})})
    public Image create(@RequestBody @Valid final Image image) {
        log.info("add new image");
        log.debug("image:{}", image);

        return service.create(image);
    }

    @DeleteMapping("{id}")
    @Operation(summary = "delete image")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "get owners", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "image not found", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "server error", content = {@Content(mediaType = "application/json")})})
    public Image delete(@PathVariable final long id) {
        log.info("delete image");
        log.debug("id:{}", id);

        return service.delete(id);
    }
}
