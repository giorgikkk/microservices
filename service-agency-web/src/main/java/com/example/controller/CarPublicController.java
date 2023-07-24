package com.example.controller;

import com.example.domain.Car;
import com.example.service.CarService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("public")
@Slf4j
public class CarPublicController {
    private final CarService service;

    @Autowired
    public CarPublicController(CarService service) {
        this.service = service;
    }

    @GetMapping("{personalNo}/{licensePlate}")
    @Operation(summary = "get registered car and owner")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "get registered car and owner", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "not found", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "server error", content = {@Content(mediaType = "application/json")})})
    public Car getByPersonalNoAndLicensePlate(@Valid @PathVariable final String personalNo, @Valid @PathVariable final String licensePlate) {
        log.info("getting registered car and owner");
        log.debug("personalNo:{} license plate:{}", personalNo, licensePlate);

        return service.getByPersonalNoAndLicensePlate(personalNo, licensePlate);
    }
}
