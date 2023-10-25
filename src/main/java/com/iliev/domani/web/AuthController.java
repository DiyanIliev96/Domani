package com.iliev.domani.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/user")
public class AuthController {




    @GetMapping("/login")
    private String getLogin() {

        return "login";
    }

    @PostMapping("/login-success")
    private String success() {
        return "redirect:/";
    }

    @GetMapping("/login-failed")
    private String failedLogin(RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("bad_credentials",true);
        return "redirect:/user/login";
    }
}
