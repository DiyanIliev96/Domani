package com.iliev.domani.service;

import com.iliev.domani.model.dto.RegisterDto;
import com.iliev.domani.model.entity.RoleEntity;
import com.iliev.domani.model.enums.InitUserNamesEnum;
import com.iliev.domani.model.enums.RoleNameEnum;
import com.iliev.domani.model.entity.UserEntity;
import com.iliev.domani.model.view.UserView;
import com.iliev.domani.repository.RoleRepository;
import com.iliev.domani.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
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

            RoleEntity userRole = roleRepository.findByName(RoleNameEnum.USER).get();
            List<UserEntity> userEntities = Arrays.stream(InitUserNamesEnum.values())
                    .map(initUserNamesEnum -> new UserEntity()
                            .setFullName(initUserNamesEnum.name())
                            .setEmail(initUserNamesEnum.name() + "@example.com")
                            .setPassword(passwordEncoder.encode(initUserNamesEnum.name()))
                            .setRoles(Set.of(userRole))).toList();
            userRepository.saveAll(userEntities);
        }
    }

    public void register(RegisterDto registerDto) {
        UserEntity newUser = modelMapper.map(registerDto, UserEntity.class);
        newUser.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        newUser.setRoles(Set.of(roleRepository.findByName(RoleNameEnum.USER).get()));
        userRepository.save(newUser);
    }

    public Page<UserView> getAllUsers(Pageable pageable) {

        return userRepository.findAll(pageable)
                .map(user -> modelMapper.map(user, UserView.class));
    }

    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }
}
