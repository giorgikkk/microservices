package com.example.receiverService;

import com.example.domain.Owner;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;

@Service
public class OwnerReceiverService {
    private final RestTemplate restTemplate;
    private final UriComponentsBuilder uriBuilder;

    public OwnerReceiverService(RestTemplate restTemplate, UriComponentsBuilder uriBuilder) {
        this.restTemplate = restTemplate;
        this.uriBuilder = uriBuilder;
    }

    public Owner getOwnerByPersonalNo(final String personalNo) throws URISyntaxException {
        URI uri = new URI(uriBuilder.cloneBuilder().path(String.format("public/%s", personalNo)).toUriString());
        try {
            ResponseEntity<Owner> responseEntity = restTemplate.getForEntity(uri, Owner.class);

            return responseEntity.getBody();
        } catch (Exception ex) {
            return null;
        }
    }
}

