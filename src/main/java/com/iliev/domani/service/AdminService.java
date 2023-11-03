package com.iliev.domani.service;

import com.iliev.domani.model.dto.CreateUserDto;
import com.iliev.domani.model.entity.RoleEntity;
import com.iliev.domani.model.entity.UserEntity;
import com.iliev.domani.model.enums.RoleNameEnum;
import com.iliev.domani.repository.RoleRepository;
import com.iliev.domani.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class AdminService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    private final PasswordEncoder passwordEncoder;

    private final RoleRepository roleRepository;
    public AdminService(UserRepository userRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }


    public void createUser(CreateUserDto createUserDto) {
        UserEntity newUser = modelMapper.map(createUserDto, UserEntity.class)
                .setPassword(passwordEncoder.encode(createUserDto.getPassword()));
        RoleEntity adminRole = roleRepository.findByName(RoleNameEnum.ADMIN).get();
        RoleEntity userRole = roleRepository.findByName(RoleNameEnum.USER).get();
        if (createUserDto.getRole().name().equals("ADMIN")) {
            newUser.setRoles(Set.of(userRole,adminRole));
        } else {
            newUser.setRoles(Set.of(userRole));
        }
        userRepository.save(newUser);
    }
}
