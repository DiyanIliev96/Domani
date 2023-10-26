package com.iliev.domani.web;

import com.iliev.domani.model.dto.RegisterDto;
import com.iliev.domani.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/user")
public class AuthController {


    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @ModelAttribute("registerModel")
    private RegisterDto registerDto() {
        return new RegisterDto();
    }

    @GetMapping("/login")
    private String getLogin() {

        return "login";
    }

    @GetMapping("/register")
    private String getRegister() {

        return "register";
    }

    @PostMapping("/register")
    private String doRegister(@Valid RegisterDto registerDto, BindingResult bindingResult
            ,RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("registerModel",registerDto);
            redirectAttributes.addFlashAttribute(BindingResult.MODEL_KEY_PREFIX + "registerModel",bindingResult);
            return "redirect:/user/register";
        }

        userService.register(registerDto);
        return "redirect:/user/login";
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
