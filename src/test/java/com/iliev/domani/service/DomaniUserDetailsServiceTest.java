package com.iliev.domani.service;

import com.iliev.domani.model.entity.RoleEntity;
import com.iliev.domani.model.enums.RoleNameEnum;
import com.iliev.domani.model.entity.UserEntity;
import com.iliev.domani.user.DomaniUserDetail;
import com.iliev.domani.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;
import java.util.Set;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DomaniUserDetailsServiceTest {
    @Mock
    private UserRepository mockUserRepository;

    private DomaniUserDetailService toTest;

    @BeforeEach
    void setUp() {
        toTest = new DomaniUserDetailService(mockUserRepository);
    }

    @Test
    void testLoadUserByUsername_UserExists() {
        UserEntity testUserEntity = new UserEntity()
                .setFullName("Test Testov")
                .setEmail("test@test.com")
                .setPassword("test")
                .setRoles(Set.of(new RoleEntity().setName(RoleNameEnum.USER),
                        new RoleEntity().setName(RoleNameEnum.ADMIN)));

        when(mockUserRepository.findByEmail(testUserEntity.getEmail()))
                .thenReturn(Optional.of(testUserEntity));

        DomaniUserDetail userDetails = (DomaniUserDetail) toTest.loadUserByUsername(testUserEntity.getEmail());

        Assertions.assertEquals(testUserEntity.getEmail(),userDetails.getEmail());
        Assertions.assertEquals(testUserEntity.getPassword(),userDetails.getPassword());
        Assertions.assertEquals(testUserEntity.getFullName(),userDetails.getFullName());

        var authorities = userDetails.getAuthorities();

        Assertions.assertEquals(2, authorities.size());
    }

    @Test
    void testLoadUserByUsername_UserDoesNotExist() {

        // arrange
        // NOTE: No need to arrange anything, mocks return empty optionals.

        // act && assert
        Assertions.assertThrows(
                UsernameNotFoundException.class,
                () -> toTest.loadUserByUsername("non-existant@example.com")
        );
    }

}
