package com.kamal.challenge.service;

import com.kamal.challenge.client.RestClient;
import com.kamal.challenge.exception.BadRequestException;
import com.kamal.challenge.model.CreateUserRequest;
import com.kamal.challenge.model.CreateUserResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@Slf4j
public class UserService {

  @Autowired RestClient client;

  public ResponseEntity<CreateUserResponse> createUser(CreateUserRequest createUserRequest) {

    validatePassword(createUserRequest.getPassword());

    return client.createUser(createUserRequest);
  }

  private void validatePassword(String password) {

    boolean valid;

    if (password.length() < 8) {
      throw new BadRequestException("Password must be a minimum of 8 characters");
    } else {
      Pattern letter = Pattern.compile("[A-Z]");
      Pattern digit = Pattern.compile("[0-9]");
      Pattern special = Pattern.compile("[$%#_.]");
      Matcher hasLetter = letter.matcher(password);
      Matcher hasDigit = digit.matcher(password);
      Matcher hasSpecial = special.matcher(password);

      valid = hasLetter.find() && hasDigit.find() && hasSpecial.find();
    }
    if (!valid) throw new BadRequestException("Password do not meet minimum criteria");
  }
}
