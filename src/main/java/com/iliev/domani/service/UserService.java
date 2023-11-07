package com.iliev.domani.service;

import com.iliev.domani.model.dto.CreateUserDto;
import com.iliev.domani.model.dto.EditUserDto;
import com.iliev.domani.model.dto.RegisterDto;
import com.iliev.domani.model.entity.RoleEntity;
import com.iliev.domani.model.entity.UserEntity;
import com.iliev.domani.model.enums.InitUserNamesEnum;
import com.iliev.domani.model.enums.RoleNameEnum;
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

    public UserView findById(Long id) {
        return modelMapper.map(userRepository.findById(id).get(),UserView.class);
    }

    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

    public void createUserByAdmin(CreateUserDto createUserDto) {
        UserEntity newUser = modelMapper.map(createUserDto, UserEntity.class)
                .setPassword(passwordEncoder.encode(createUserDto.getPassword()));
        RoleEntity adminRole = roleRepository.findByName(RoleNameEnum.ADMIN).get();
        RoleEntity userRole = roleRepository.findByName(RoleNameEnum.USER).get();
        if (createUserDto.getRole().name().equals("ADMIN")) {
            newUser.setRoles(Set.of(userRole, adminRole));
        } else {
            newUser.setRoles(Set.of(userRole));
        }
        userRepository.save(newUser);
    }

    public void doEditUser(String id, EditUserDto editUserDto) {
        UserEntity userToEdit = userRepository.findById(Long.parseLong(id)).get();

        if (!userToEdit.getFullName().equals(editUserDto.getFullName())) {
            userToEdit.setFullName(editUserDto.getFullName());
        }

        if (!userToEdit.getEmail().equals(editUserDto.getEmail())) {
            userToEdit.setEmail(editUserDto.getEmail());
        }

        if (editUserDto.getRole().equals("ADMIN")) {
            userToEdit.getRoles().add(roleRepository.findByName(RoleNameEnum.ADMIN).get());
        }

        if (editUserDto.getRole().equals("USER")) {
            userToEdit.getRoles().remove(roleRepository.findByName(RoleNameEnum.ADMIN).get());
        }
        userRepository.save(userToEdit);
    }
}
