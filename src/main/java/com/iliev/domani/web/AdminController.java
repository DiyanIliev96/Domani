package com.iliev.domani.web;

import com.iliev.domani.model.dto.CreateUserDto;
import com.iliev.domani.model.dto.EditUserDto;
import com.iliev.domani.model.view.UserView;
import com.iliev.domani.service.UserService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/users")
    private String getUsers(Model model, @PageableDefault(size = 3,sort = "id") Pageable pageable) {
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

    @GetMapping("/users/create")
    private String getCreateUser(Model model) {
        if (!model.containsAttribute("createUser")) {
            model.addAttribute("createUser",new CreateUserDto());
        }
        return "create-user";
    }

    @PostMapping("/users/create")
    private String doCreateUser(@Valid CreateUserDto createUserDto, BindingResult bindingResult,
                                RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("createUser",createUserDto);
            redirectAttributes.addFlashAttribute(BindingResult.MODEL_KEY_PREFIX + "createUser",bindingResult);
            return "redirect:/admin/users/create";
        }

        userService.createUserByAdmin(createUserDto);
        return "redirect:/admin/users";
    }

    @GetMapping("/users/edit/{id}")
    private String editUser(@PathVariable Long id, Model model) {
        model.addAttribute("editUser",userService.findById(id));
        return "edit-profile";
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
        userService.doEditUser(id,editUserDto);
        return "redirect:/admin/users";
    }
}
