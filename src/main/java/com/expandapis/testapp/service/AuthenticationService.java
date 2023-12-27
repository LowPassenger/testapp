package com.expandapis.testapp.service;

import com.expandapis.testapp.model.dto.UserDto;
import com.expandapis.testapp.model.entity.Role;
import com.expandapis.testapp.model.entity.User;
import com.expandapis.testapp.repository.UserRepository;
import com.expandapis.testapp.security.jwt.JwtTokenProvider;
import java.util.Collections;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider tokenProvider;

    public AuthenticationService(AuthenticationManager authenticationManager,
                                 UserRepository userRepository,
                                 RoleService roleService,
                                 PasswordEncoder passwordEncoder,
                                 JwtTokenProvider tokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
    }

    public String authentication(UserDto userDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userDto.getUsername(), userDto.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        return tokenProvider.generateToken(authentication);
    }

    public User registerUser(UserDto userDto) {
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setEncryptedPassword(passwordEncoder.encode(userDto.getPassword()));
        Role roles = roleService.getByName("USER");
        user.setRoles(Collections.singleton(roles));
        userRepository.save(user);
        return user;
    }
}
