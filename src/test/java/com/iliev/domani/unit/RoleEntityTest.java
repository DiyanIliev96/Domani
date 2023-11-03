package com.iliev.domani.unit;

import com.iliev.domani.model.entity.RoleEntity;
import com.iliev.domani.model.enums.RoleNameEnum;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RoleEntityTest {

    RoleEntity role;

    @BeforeEach
    void setUp() {
         role = new RoleEntity()
                .setName(RoleNameEnum.USER)
                .setId(1L);
    }

    @Test
    void testRoleEntityGetId() {
        Assertions.assertEquals(role.getId(),1L);
    }
}
