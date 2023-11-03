package com.iliev.domani.web.admin;

import com.iliev.domani.model.dto.CreateUserDto;
import com.iliev.domani.service.AdminService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
public class AdminUsersCreateController {


    private final AdminService adminService;

    public AdminUsersCreateController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/users/create")
    private String getCreateUser(Model model) {
        if (!model.containsAttribute("createUser")) {
            model.addAttribute("createUser",new CreateUserDto());
        }
        return "createUser";
    }

    @PostMapping("/users/create")
    private String doCreateUser(@Valid CreateUserDto createUserDto, BindingResult bindingResult,
                                RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("createUser",createUserDto);
            redirectAttributes.addFlashAttribute(BindingResult.MODEL_KEY_PREFIX + "createUser",bindingResult);
            return "redirect:/admin/users/create";
        }

        adminService.createUser(createUserDto);
        return "redirect:/admin/users";
    }
}
