package com.iliev.domani.service;

import com.iliev.domani.model.entity.RoleEntity;
import com.iliev.domani.model.entity.UserEntity;
import com.iliev.domani.user.DomaniUserDetail;
import com.iliev.domani.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class DomaniUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    public DomaniUserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .map(this::map)
                .orElseThrow(() -> new UsernameNotFoundException("User with " + email + " not found!"));
    }

    private UserDetails map(UserEntity user) {
        return new DomaniUserDetail(user.getFullName(),
                user.getEmail(),
                user.getPassword(),
                user.getRoles().stream().map(this::map)
                        .toList());
    }

    private GrantedAuthority map(RoleEntity role) {
        return new SimpleGrantedAuthority("ROLE_" + role);
    }
}
