package com.kamal.challenge.service;

import com.kamal.challenge.exception.BadRequestException;
import com.kamal.challenge.model.CreateUserRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ActiveProfiles("local")
public class UserServiceTest {

  @InjectMocks UserService userService;

  @Test(expected = BadRequestException.class)
  public void createUserMissingSpecialCharacterTest() {

    CreateUserRequest request =
        CreateUserRequest.builder()
            .username("username")
            .password("Password1")
            .ipaddress("24.48.0.1")
            .build();

    userService.createUser(request);
  }

  @Test(expected = BadRequestException.class)
  public void createUserTestMissingUppercaseLetter() {

    CreateUserRequest request =
        CreateUserRequest.builder()
            .username("username")
            .password("password$1")
            .ipaddress("24.48.0.1")
            .build();

    userService.createUser(request);
  }

  @Test(expected = BadRequestException.class)
  public void createUserTestMissingNumber() {

    CreateUserRequest request =
        CreateUserRequest.builder()
            .username("username")
            .password("Password")
            .ipaddress("24.48.0.1")
            .build();

    userService.createUser(request);
  }

  @Test(expected = BadRequestException.class)
  public void createUserTestMissingLengthLessThanEight() {

    CreateUserRequest request =
        CreateUserRequest.builder()
            .username("username")
            .password("pass")
            .ipaddress("24.48.0.1")
            .build();

    userService.createUser(request);
  }
}
