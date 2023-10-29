package com.iliev.domani.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminUsersController {


    @GetMapping("/users")
    private String getUsers() {

        return "users";
    }

}
