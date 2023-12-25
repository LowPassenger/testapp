package com.expandapis.testapp.service;

import com.expandapis.testapp.exception.ResourceNotFoundException;
import com.expandapis.testapp.model.entity.UserEntity;
import com.expandapis.testapp.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserEntity getByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(
                () -> new ResourceNotFoundException("User", "username", username)
        );
    }

    public UserEntity create(UserEntity user) {
        log.info("User {} with role(s) {} was created", user.getUsername(), user.getRoles());
        return userRepository.save(user);
    }
}
