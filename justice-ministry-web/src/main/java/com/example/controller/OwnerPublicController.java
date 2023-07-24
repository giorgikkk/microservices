package com.example.controller;

import com.example.domain.Owner;
import com.example.service.OwnerService;
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
public class OwnerPublicController {
    private final OwnerService service;

    @Autowired
    public OwnerPublicController(OwnerService service) {
        this.service = service;
    }

    @GetMapping("{personalNo}")
    @Operation(summary = "get owner by personalNo")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "get owner by personalNo", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "owner not found", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "server error", content = {@Content(mediaType = "application/json")})})
    public Owner getByPersonalNo(@Valid @PathVariable final String personalNo) {
        log.info("getting owner by personalNo");
        log.debug("personalNo:{}", personalNo);

        return service.getByPersonalNo(personalNo);
    }
}
