package com.iliev.domani.unit;

import com.iliev.domani.model.entity.RoleEntity;
import com.iliev.domani.model.entity.UserEntity;
import com.iliev.domani.model.enums.RoleNameEnum;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

public class UserEntityTest {

    UserEntity user;

    @BeforeEach
    void setUp() {
        user = new UserEntity().setFullName("gosho")
                .setEmail("ot@pochivka.bg")
                .setId(1l)
                .setRoles(Set.of(new RoleEntity().setName(RoleNameEnum.USER)));
    }

    @Test
    void testUserEntityGetIdMethod() {
        Assertions.assertEquals(1l,user.getId());
    }
}
