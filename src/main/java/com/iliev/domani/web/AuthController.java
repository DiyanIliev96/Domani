package com.iliev.domani.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AuthController {




    @GetMapping("/user/login")
    private String getLogin() {

        return "login";
    }

    @PostMapping("/login-success")
    private String success() {
        return "redirect:/";
    }
}
