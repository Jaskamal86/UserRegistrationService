package com.kamal.challenge.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kamal.challenge.controller.CreateUserController;
import com.kamal.challenge.model.CreateUserRequest;
import com.kamal.challenge.model.CreateUserResponse;
import com.kamal.challenge.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(CreateUserController.class)
@AutoConfigureMockMvc
public class CreateUserControllerTest {

  private static final String BASIC_URL = "/user/create";

  @MockBean private UserService userService;

  @Autowired private MockMvc mockMvc;
  @Autowired private ObjectMapper objectMapper;
  @Autowired WebApplicationContext webApplicationContext;

  @Before
  public void setUp() {
    mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
  }

  @Test
  public void createUserAccountTest() throws Exception {

    CreateUserRequest createUserRequest =
        CreateUserRequest.builder()
            .username("kamal")
            .password("Password1#")
            .ipaddress("24.48.0.1")
            .build();

    ResponseEntity<CreateUserResponse> entity =
        new ResponseEntity<>(
            CreateUserResponse.builder().message("Welcome").build(), HttpStatus.OK);

    when(userService.createUser(createUserRequest)).thenReturn(entity);

    mockMvc
        .perform(
            MockMvcRequestBuilders.post(BASIC_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createUserRequest)))
        .andDo(print())
        .andExpect(status().isOk());
  }
}
