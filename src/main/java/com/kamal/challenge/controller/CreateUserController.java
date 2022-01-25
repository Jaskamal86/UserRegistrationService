package com.kamal.challenge.controller;

import com.kamal.challenge.api.UserApi;
import com.kamal.challenge.model.CreateUserRequest;
import com.kamal.challenge.model.CreateUserResponse;
import com.kamal.challenge.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

@Controller
public class CreateUserController implements UserApi {

  @Autowired private UserService userService;

  @Override
  public ResponseEntity<CreateUserResponse> createUserAccount(CreateUserRequest createUserRequest) {
    return userService.createUser(createUserRequest);
  }
}
