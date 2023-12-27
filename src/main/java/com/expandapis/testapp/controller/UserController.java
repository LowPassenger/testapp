package com.expandapis.testapp.controller;

import com.expandapis.testapp.config.SecurityConfig;
import com.expandapis.testapp.model.dto.UserDto;
import com.expandapis.testapp.security.jwt.JwtAuthResponse;
import com.expandapis.testapp.service.AuthenticationService;
import com.expandapis.testapp.service.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Import(SecurityConfig.class)
@RestController
@RequestMapping("api/v1/user")
public class UserController {
    private final AuthenticationService authenticationService;
    private final UserService userService;

    @Autowired
    public UserController(AuthenticationService authenticationService, UserService userService) {
        this.authenticationService = authenticationService;
        this.userService = userService;
    }

    @PostMapping("authenticate")
    public ResponseEntity<JwtAuthResponse> authentication(
            @Valid @RequestBody UserDto userDto) {
        String token = authenticationService.authentication(userDto);
        log.info("User {} was successfully authenticated", userDto.getUsername());
        return ResponseEntity.ok(new JwtAuthResponse(token));
    }

    @PostMapping("add")
    public ResponseEntity<String> registration(@Valid @RequestBody UserDto userDto) {
        String username = userDto.getUsername();
        if (userService.existByUserName(username)) {
            log.info("User registration with username " + username
                    + " failed. Username already exist!");
            return new ResponseEntity<>("Registration failed! Username is already taken!",
                    HttpStatus.BAD_REQUEST);
        }
        authenticationService.registerUser(userDto);
        log.info("User with username {} was successfully created.", userDto.getUsername());
        return new ResponseEntity<>("User registered successfully", HttpStatus.CREATED);
    }
}
