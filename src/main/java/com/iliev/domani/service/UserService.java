package com.iliev.domani.service;

import com.iliev.domani.model.dto.RegisterDto;
import com.iliev.domani.model.entity.RoleEntity;
import com.iliev.domani.model.entity.RoleNameEnum;
import com.iliev.domani.model.entity.UserEntity;
import com.iliev.domani.model.view.UserView;
import com.iliev.domani.repository.RoleRepository;
import com.iliev.domani.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserService {


    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final RoleRepository roleRepository;

    private final ModelMapper modelMapper;
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
    }


    public void initUsers() {
        if (userRepository.count() == 0) {
            UserEntity admin = new UserEntity();
            admin.setEmail("admin@admin.com");
            admin.setFullName("Admin Adminov");
            admin.setPassword(passwordEncoder.encode("admin"));
            Set<RoleEntity> roles = new HashSet<>(roleRepository.findAll());
            admin.setRoles(roles);
            userRepository.save(admin);

            RoleEntity role = roleRepository.findByName(RoleNameEnum.USER).get();
            UserEntity userOne = new UserEntity()
                    .setFullName("User one")
                    .setEmail("user@one.com")
                    .setPassword(passwordEncoder.encode("userone"))
                    .setRoles(Set.of(role));
            UserEntity userTwo = new UserEntity()
                    .setFullName("User two")
                    .setEmail("user@two.com")
                    .setPassword(passwordEncoder.encode("usertwo"))
                    .setRoles(Set.of(role));
            UserEntity userThree = new UserEntity()
                    .setFullName("User three")
                    .setEmail("user@three.com")
                    .setPassword(passwordEncoder.encode("userthree"))
                    .setRoles(Set.of(role));

            userRepository.save(userOne);
            userRepository.save(userTwo);
            userRepository.save(userThree);
        }
    }

    public void register(RegisterDto registerDto) {
        UserEntity newUser = modelMapper.map(registerDto, UserEntity.class);
        newUser.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        newUser.setRoles(Set.of(roleRepository.findByName(RoleNameEnum.USER).get()));
        userRepository.save(newUser);
        System.out.println();
    }

    public List<UserView> getAllUsers() {
        List<UserView> userViews = userRepository.findAllByOrderById().stream()
                .map(user -> modelMapper.map(user, UserView.class))
                .toList();
        System.out.println();
        return userViews;
    }

    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }
}
