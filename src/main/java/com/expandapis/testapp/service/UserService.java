package com.expandapis.testapp.service;

import com.expandapis.testapp.exception.ResourceNotFoundException;
import com.expandapis.testapp.model.entity.User;
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

    public User getByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(
                () -> new ResourceNotFoundException("User", "username", username)
        );
    }

    public User create(User user) {
        log.info("User {} with role(s) {} was created", user.getUsername(), user.getRoles());
        return userRepository.save(user);
    }

    public boolean existByUserName(String username) {
        return userRepository.existsUserByUsername(username);
    }
}
