package com.iliev.domani.init;

import com.iliev.domani.service.CategoryService;
import com.iliev.domani.service.RoleService;
import com.iliev.domani.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class AppInit implements CommandLineRunner {

    private final RoleService roleService;
    private final UserService userService;

    private final CategoryService categoryService;

    public AppInit(RoleService roleService, UserService userService, CategoryService categoryService) {
        this.roleService = roleService;
        this.userService = userService;
        this.categoryService = categoryService;
    }

    @Override
    public void run(String... args) throws Exception {
        roleService.initRoles();
        userService.initUsers();
        categoryService.initCategories();
    }
}
