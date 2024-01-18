package com.iliev.domani.service;

import com.iliev.domani.exception.ObjectNotFoundException;
import com.iliev.domani.model.dto.CreateUserDto;
import com.iliev.domani.model.dto.EditUserDto;
import com.iliev.domani.model.dto.RegisterDto;
import com.iliev.domani.model.entity.OrderEntity;
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
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {


    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final RoleRepository roleRepository;

    private final ModelMapper modelMapper;

    private final EmailService emailService;

    private final CartItemService cartItemService;

    private final OrderService orderService;
    private final UserDetailsService userDetailsService;
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository, ModelMapper modelMapper, EmailService emailService, CartItemService cartItemService, OrderService orderService, UserDetailsService userDetailsService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
        this.emailService = emailService;
        this.cartItemService = cartItemService;
        this.orderService = orderService;
        this.userDetailsService = userDetailsService;
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
        newUser.setRoles(Set.of(roleRepository.findByName(RoleNameEnum.USER)
                .orElseThrow(() ->new ObjectNotFoundException("Role not found!"))));
        userRepository.save(newUser);
        emailService.sendRegistrationEmail(newUser.getEmail(),newUser.getFullName());
    }

    public Page<UserView> getAllUsers(Pageable pageable) {

        return userRepository.findAll(pageable)
                .map(user -> modelMapper.map(user, UserView.class));
    }

    public EditUserDto findById(Long id) {
        UserEntity userToEdit = userRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("User not found!"));
        return modelMapper.map(userToEdit,EditUserDto.class);
    }

    public void deleteUserById(Long userId) {
        List<OrderEntity> allOrdersByUser = orderService.getAllOrdersByUserId(userId);
        if (!allOrdersByUser.isEmpty()) {
            allOrdersByUser.forEach(order -> orderService.deleteOrder(order.getId(),userId));
        }
        cartItemService.deleteAllCartItems(userId);
        userRepository.deleteById(userId);
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

        if (editUserDto.getRoles() == 2) {
            userToEdit.getRoles().add(roleRepository.findByName(RoleNameEnum.ADMIN).get());
        }

        if (editUserDto.getRoles() == 1) {
            userToEdit.getRoles().remove(roleRepository.findByName(RoleNameEnum.ADMIN).get());
        }
        userRepository.save(userToEdit);
    }

    public void doEditProfile(Long id, EditUserDto editUserDto) {
        UserEntity userEntity = userRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("User not found."));
        userEntity.setFullName(editUserDto.getFullName());
        userEntity.setEmail(editUserDto.getEmail());
        userRepository.save(userEntity);
    }

    public UserView getUserView(Long id) {
        return userRepository.findById(id).map(u -> modelMapper.map(u,UserView.class))
                .orElseThrow(() -> new UsernameNotFoundException("User not found!"));
    }

    public void createUserIfNotExists(String userEmail,String fullName) {
        var userOpt = this.userRepository.findByEmail(userEmail);
        RoleEntity roleUser = roleRepository.findByName(RoleNameEnum.USER)
                .orElseThrow(() -> new ObjectNotFoundException("Role doesn't exists"));
        if (userOpt.isEmpty()) {
            var newUser = new UserEntity()
                    .setEmail(userEmail)
                    .setPassword(null)
                    .setRoles(Set.of(roleUser))
                    .setFullName(fullName);
            userRepository.save(newUser);
            emailService.sendRegistrationEmail(userEmail,fullName);
        }
    }

    public void login(String userEmail) {
        UserDetails userDetails = userDetailsService
                .loadUserByUsername(userEmail);

        Authentication auth = new UsernamePasswordAuthenticationToken(userDetails,
                userDetails.getPassword(),
                userDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(auth);
    }
}
