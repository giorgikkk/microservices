package com.example.receiverService;

import com.example.domain.Car;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;

@Service
public class CarRecieverService {
    private final RestTemplate restTemplate;
    private final UriComponentsBuilder uriBuilder;

    @Autowired
    public CarRecieverService(RestTemplate restTemplate, UriComponentsBuilder uriBuilder) {
        this.restTemplate = restTemplate;
        this.uriBuilder = uriBuilder;
    }

    public Car getCarBy(final String personalNo, final String licensePlate) throws URISyntaxException {
        URI uri = new URI(uriBuilder.cloneBuilder().path(String.format("public/%s/%s", personalNo, licensePlate)).toUriString());
        try {
            ResponseEntity<Car> responseEntity = restTemplate.getForEntity(uri, Car.class);

            return responseEntity.getBody();
        } catch (RestClientException ex) {
            return null;
        }
    }
}
