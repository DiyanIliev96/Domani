package com.iliev.domani.web;

import com.iliev.domani.model.dto.EditUserDto;
import com.iliev.domani.model.view.UserView;
import com.iliev.domani.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping(value = "/admin")
public class AdminUsersController {

    private final UserService userService;

    public AdminUsersController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    private String getUsers(Model model,@PageableDefault(size = 3,sort = "id") Pageable pageable) {
        Page<UserView> allUsers = userService.getAllUsers(pageable);
        int totalPages = allUsers.getTotalPages();
        List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages - 1).boxed().toList();
        model.addAttribute("users",allUsers);
        model.addAttribute("pageNumbers",pageNumbers);
        return "users";
    }

   @DeleteMapping("/users/{id}")
    private String deleteUser(@PathVariable("id") Long id) {
        userService.deleteUserById(id);
        return "redirect:/admin/users";
    }

    @GetMapping("/users/edit/{id}")
    private String editUser(@PathVariable Long id,Model model) {
        model.addAttribute("editUser",userService.findById(id));
        return "editProfile";
    }

    @PostMapping("/users/edit/{id}")
    private String doEdit(@PathVariable String id, EditUserDto editUserDto, BindingResult bindingResult) {
        userService.doEditUser(id,editUserDto);
        return "redirect:/admin/users";
    }
}
