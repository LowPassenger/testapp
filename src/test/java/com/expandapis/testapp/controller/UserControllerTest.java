package com.expandapis.testapp.controller;

import com.expandapis.testapp.model.dto.UserDto;
import com.expandapis.testapp.model.entity.User;
import com.expandapis.testapp.service.AuthenticationService;
import com.expandapis.testapp.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthenticationService authenticationService;

    @MockBean
    private UserService userService;

    @Test
    void testAuthentication() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setUsername("testuser");
        userDto.setPassword("passwordfortestuser");

        when(authenticationService.authentication(any(UserDto.class))).thenReturn("bla-bla-blaToken");

        mockMvc.perform(post("/api/v1/user/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(userDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").value("bla-bla-blaToken"));

        verify(authenticationService, times(1)).authentication(any(UserDto.class));
    }

    @Test
    void testRegistration() throws Exception {
        UserDto testUserDto = new UserDto();
        testUserDto.setUsername("any username");
        testUserDto.setPassword("any password");

        when(userService.existByUserName(anyString())).thenReturn(false);
        when(authenticationService.registerUser(any(UserDto.class))).thenReturn(any(User.class));

        mockMvc.perform(post("/api/v1/user/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(testUserDto)))
                .andExpect(status().isCreated())
                .andExpect(content().string("User registered successfully"));

        verify(authenticationService, times(1)).registerUser(any(UserDto.class));
    }
}