package com.example.controller;

import com.example.domain.Insurance;
import com.example.domain.InsuredCar;
import com.example.service.InsuredCarService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("cars")
@Slf4j
public class InsuredCarController {
    private final InsuredCarService service;

    public InsuredCarController(InsuredCarService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "get cars")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "get cars", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "server error", content = {@Content(mediaType = "application/json")})})
    public List<InsuredCar> getAll(@Min(0) @Parameter(description = "page index") @RequestParam(required = false, defaultValue = "${page}", name = "page") final int page,
                                   @Min(1) @Max(10) @Parameter(description = "page size") @RequestParam(required = false, defaultValue = "${pageSize}", name = "size") final int pageSize) {
        log.info("getting all cars");
        log.debug("page:{} pageSize:{}", page, pageSize);

        return service.getAll(page, pageSize);
    }

    @GetMapping("{id}")
    @Operation(summary = "get car by id")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "get car by id", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "car not found", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "server error", content = {@Content(mediaType = "application/json")})})
    public InsuredCar getById(@PathVariable final long id) {
        log.info("getting car by id");
        log.debug("id:{}", id);

        return service.getById(id);
    }

    @PostMapping("{personalNo}/{licensePlate}")
    @ResponseStatus(code = HttpStatus.CREATED)
    @Operation(summary = "create car")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "created car", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "invalid format", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "car not found", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "406", description = "not acceptable", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "409", description = "already exists", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "server error", content = {@Content(mediaType = "application/json")})})
    public InsuredCar create(@Valid @PathVariable final String personalNo, @Valid @PathVariable final String licensePlate, @Valid @RequestBody final Insurance insurance) throws URISyntaxException {
        log.info("insured new car");
        log.debug("personalNo:{} license plate:{} insurance:{}", personalNo, licensePlate, insurance);

        return service.create(personalNo, licensePlate, insurance);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "delete car")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "deleted car", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "car not found", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "server error", content = {@Content(mediaType = "application/json")})})
    public InsuredCar delete(@PathVariable final long id) {
        log.info("deleting car");
        log.debug("id:{}", id);

        return service.delete(id);
    }
}
