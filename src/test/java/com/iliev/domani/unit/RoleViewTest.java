package com.iliev.domani.unit;

import com.iliev.domani.model.view.RoleView;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RoleViewTest {
    RoleView roleView;

    @BeforeEach
    void setUp() {
        roleView = new RoleView().setName("USER");
    }

    @Test
    void testRoleViewGetNameMethod() {
        Assertions.assertEquals("USER",roleView.getName());
    }
}
