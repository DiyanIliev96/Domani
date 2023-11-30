package com.iliev.domani.web;

import com.iliev.domani.model.dto.CreateUserDto;
import com.iliev.domani.model.dto.EditUserDto;
import com.iliev.domani.model.view.UserView;
import com.iliev.domani.service.UserService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/admin")
public class AdminUsersController {

    private final UserService userService;

    public AdminUsersController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/users")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    private String getUsers(Model model, @PageableDefault(size = 3,sort = "id") Pageable pageable) {
        Page<UserView> allUsers = userService.getAllUsers(pageable);
        int totalPages = allUsers.getTotalPages();
        List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages - 1).boxed().toList();
        model.addAttribute("users",allUsers);
        model.addAttribute("pageNumbers",pageNumbers);
        return "users";
    }

    @DeleteMapping("/users/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    private String deleteUser(@PathVariable("id") Long id) {
        userService.deleteUserById(id);
        return "redirect:/admin/users";
    }

    @GetMapping("/users/create")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    private String getCreateUser(Model model) {
        if (!model.containsAttribute("createUser")) {
            model.addAttribute("createUser",new CreateUserDto());
        }
        return "create-user";
    }

    @PostMapping("/users/create")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
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
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    private String editUser(@PathVariable Long id, Model model) {
        if (!model.containsAttribute("editUser")) {
            model.addAttribute("editUser",userService.findById(id));
        }
        return "edit-user";
    }

    @PatchMapping("/users/edit/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    private String doEdit(@Valid EditUserDto editUserDto, BindingResult bindingResult,
                          RedirectAttributes redirectAttributes,
                          @PathVariable String id) {
        EditUserDto userToEdit = userService.findById(Long.parseLong(id));

//      /*If full name and email inputs are wrong display error messages for both.Then redirect to edit form again with
//      populated fields.*/
        if (bindingResult.hasErrors() && !userToEdit.getFullName().equals(editUserDto.getFullName()) &&
        !userToEdit.getEmail().equals(editUserDto.getEmail())) {
            redirectAttributes.addFlashAttribute("editUser",userToEdit);
            redirectAttributes.addFlashAttribute("wrong_email",true);
            redirectAttributes.addFlashAttribute("wrong_fullName",true);
            return "redirect:/admin/users/edit/" + id;
        }

        /*Display error message only for email if input is wrong.Then redirect to edit form again with populated fields.*/
        if (bindingResult.hasErrors() && !userToEdit.getEmail().equals(editUserDto.getEmail())) {
            redirectAttributes.addFlashAttribute("editUser",userToEdit);
            redirectAttributes.addFlashAttribute("wrong_email",true);
            return "redirect:/admin/users/edit/" + id;
        }

        /*Display error message only for full name if input is wrong.Then return to edit form with populated fields.*/
        if (bindingResult.hasFieldErrors("fullName") && !userToEdit.getFullName().equals(editUserDto.getFullName())) {
            redirectAttributes.addFlashAttribute("editUser",userToEdit);
            redirectAttributes.addFlashAttribute("wrong_fullName", true);
            return "redirect:/admin/users/edit/" + id;
        }
        userService.doEditUser(id,editUserDto);
        return "redirect:/admin/users";
    }
}
