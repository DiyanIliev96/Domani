package com.iliev.domani.init;

import com.iliev.domani.service.RoleService;
import com.iliev.domani.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class AppInit implements CommandLineRunner {

    private final RoleService roleService;
    private final UserService userService;

    public AppInit(RoleService roleService, UserService userService) {
        this.roleService = roleService;
        this.userService = userService;
    }

    @Override
    public void run(String... args) throws Exception {
        roleService.initRoles();
        userService.initUsers();

    }
}
