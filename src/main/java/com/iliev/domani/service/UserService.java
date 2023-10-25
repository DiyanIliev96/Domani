package com.iliev.domani.service;

import com.iliev.domani.model.entity.RoleEntity;
import com.iliev.domani.model.entity.UserEntity;
import com.iliev.domani.repository.RoleRepository;
import com.iliev.domani.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserService {


    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final RoleRepository roleRepository;
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }


    public void initAdmin() {
        if (userRepository.count() == 0) {
            UserEntity admin = new UserEntity();
            admin.setEmail("admin@admin.com");
            admin.setFullName("Admin Adminov");
            admin.setPassword(passwordEncoder.encode("admin"));
            Set<RoleEntity> roles = new HashSet<>(roleRepository.findAll());
            admin.setRoles(roles);
            userRepository.save(admin);
        }
    }
}
