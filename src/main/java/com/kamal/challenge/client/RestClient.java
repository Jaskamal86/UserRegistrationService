package com.kamal.challenge.client;

import com.kamal.challenge.domain.GeoLocationResponse;
import com.kamal.challenge.exception.BadRequestException;
import com.kamal.challenge.model.CreateUserRequest;
import com.kamal.challenge.model.CreateUserResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.UUID;

@Component
@Slf4j
public class RestClient {
  private static final String URL = "http://ip-api.com/json/";

  private RestTemplate restTemplate = new RestTemplate();

  public ResponseEntity<CreateUserResponse> createUser(CreateUserRequest createUserRequest) {

    String uuid = UUID.randomUUID().toString();

    ResponseEntity<GeoLocationResponse> response;

    try {
      response =
          restTemplate.exchange(
              UriComponentsBuilder.fromHttpUrl(URL + createUserRequest.getIpaddress())
                  .toUriString(),
              HttpMethod.GET,
              new HttpEntity<>(null, null),
              GeoLocationResponse.class);

    } catch (Exception e) {
      log.info("Exception thrown : " + e.getMessage());
      throw e;
    }

    if (response.getBody() == null
        || response.getBody().getCountryCode() == null
        || !response.getBody().getCountryCode().equalsIgnoreCase("CA")) {
      throw new BadRequestException("Ineligible for registration");
    }

    return new ResponseEntity<>(
        CreateUserResponse.builder()
            .message(
                "Welcome User"
                    + uuid
                    + " with username "
                    + createUserRequest.getUsername()
                    + " from "
                    + response.getBody().getCity())
            .build(),
        HttpStatus.OK);
  }
}
