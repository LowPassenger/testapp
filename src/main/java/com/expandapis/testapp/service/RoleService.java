package com.expandapis.testapp.service;

import com.expandapis.testapp.exception.ResourceNotFoundException;
import com.expandapis.testapp.model.entity.Role;
import com.expandapis.testapp.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
    private final RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role getByName(String roleName) {
        return roleRepository.findByName(roleName).orElseThrow(
                () -> new ResourceNotFoundException("Role", "name", roleName)
        );
    }
}
