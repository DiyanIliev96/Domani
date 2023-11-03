package com.iliev.domani.web.admin;

import com.iliev.domani.model.dto.EditUserDto;
import com.iliev.domani.model.view.UserView;
import com.iliev.domani.service.AdminService;
import com.iliev.domani.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminUsersEditController {

    private final UserService userService;
    private final AdminService adminService;

    public AdminUsersEditController(UserService userService, AdminService adminService) {
        this.userService = userService;
        this.adminService = adminService;
    }


    @GetMapping("/users/edit/{id}")
    private String editUser(@PathVariable Long id, Model model) {
        model.addAttribute("editUser",userService.findById(id));
        return "editProfile";
    }

    @PostMapping("/users/edit/{id}")
    private String doEdit(@Valid EditUserDto editUserDto, BindingResult bindingResult,
                          @PathVariable String id) {
        UserView byId = userService.findById(Long.parseLong(id));
        if (bindingResult.hasErrors() && !byId.getEmail().equals(editUserDto.getEmail())) {
            return "redirect:/admin/users/edit/" + id;
        }

        if (bindingResult.hasFieldErrors("fullName") && !byId.getFullName().equals(editUserDto.getFullName())) {
            return "redirect:/admin/users/edit/" + id;
        }
        adminService.doEditUser(id,editUserDto);
        return "redirect:/admin/users";
    }
}
