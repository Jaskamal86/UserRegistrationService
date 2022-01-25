package com.kamal.challenge.client;

import com.kamal.challenge.domain.GeoLocationResponse;
import com.kamal.challenge.exception.BadRequestException;
import com.kamal.challenge.model.CreateUserRequest;
import com.kamal.challenge.model.CreateUserResponse;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringRunner.class)
@ActiveProfiles("local")
public class RestClientTest {

  private static final String URL = "http://ip-api.com/json/";

  RestTemplate restTemplate = Mockito.mock(RestTemplate.class);
  @InjectMocks RestClient client;

  @Test
  public void createUserTest() {

    GeoLocationResponse expectedResponse =
        GeoLocationResponse.builder().city("Whitby").countryCode("CA").build();

    ResponseEntity<GeoLocationResponse> response =
        new ResponseEntity<>(expectedResponse, HttpStatus.OK);

    Mockito.doReturn(response)
        .when(restTemplate)
        .exchange(
            ArgumentMatchers.anyString(),
            ArgumentMatchers.any(),
            ArgumentMatchers.any(),
            ArgumentMatchers.eq(GeoLocationResponse.class));

    ResponseEntity<CreateUserResponse> actualResponse =
        client.createUser(
            CreateUserRequest.builder()
                .username("UserName")
                .password("Password1#")
                .ipaddress("24.48.0.1")
                .build());

    Assert.assertNotNull(actualResponse.getBody());
    Assert.assertTrue(actualResponse.getBody().getMessage().contains("Whitby"));
  }

  @Test(expected = BadRequestException.class)
  public void createUserExceptionTest() {

    CreateUserRequest request =
        CreateUserRequest.builder()
            .username("username")
            .password("Password1")
            .ipaddress("24.48.0.1")
            .build();

    GeoLocationResponse expectedResponse =
        GeoLocationResponse.builder().city("Whitby").countryCode("USA").build();

    ResponseEntity<GeoLocationResponse> response =
        new ResponseEntity<>(expectedResponse, HttpStatus.OK);

    Mockito.doReturn(response)
        .when(restTemplate)
        .exchange(
            ArgumentMatchers.anyString(),
            ArgumentMatchers.any(),
            ArgumentMatchers.any(),
            ArgumentMatchers.eq(GeoLocationResponse.class));

        client.createUser(
            CreateUserRequest.builder()
                .username("UserName")
                .password("Password1#")
                .ipaddress("24.48.0.1")
                .build());
  }
}
