package com.iliev.domani.web;

import com.iliev.domani.service.UserService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/admin")
public class AdminUsersController {

    private final UserService userService;

    public AdminUsersController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    private String getUsers(Model model,@PageableDefault(size = 4) Pageable pageable) {
        model.addAttribute("users",userService.getAllUsers(pageable));
        return "users";
    }

   @DeleteMapping("/users/{id}")
    private String deleteUser(@PathVariable("id") Long id) {
        userService.deleteUserById(id);
        return "redirect:/admin/users";
    }
}
