package com.iliev.domani.web;

import com.iliev.domani.model.dto.EditUserDto;
import com.iliev.domani.model.view.ProductView;
import com.iliev.domani.service.ProductService;
import com.iliev.domani.service.UserService;
import com.iliev.domani.user.DomaniUserDetail;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
public class HomeController {

    private final UserService userService;
    private final ProductService productService;

    public HomeController(UserService userService, ProductService productService) {
        this.userService = userService;
        this.productService = productService;
    }


    @GetMapping("/profile")
    public String getProfile(@AuthenticationPrincipal DomaniUserDetail domaniUserDetail,Model model) {
        if (!model.containsAttribute("profile")) {
            model.addAttribute("profile",userService.findById(domaniUserDetail.getId()));
        }
        return "profile";
    }

    @GetMapping("/profile/edit")
    public String getEditProfile(@AuthenticationPrincipal DomaniUserDetail domaniUserDetail, Model model) {
        if (!model.containsAttribute("editProfile")) {
            EditUserDto byId = userService.findById(domaniUserDetail.getId());
            model.addAttribute("editProfile",byId);
        }
        return "edit-profile";
    }

    @PatchMapping("/profile/edit")
    public String doEditProfile(@AuthenticationPrincipal DomaniUserDetail domaniUserDetail, @Valid EditUserDto editUserDto, BindingResult bindingResult,
                                RedirectAttributes redirectAttributes) {
        EditUserDto profileToEdit = userService.findById(domaniUserDetail.getId());
        if (bindingResult.hasErrors() && !profileToEdit.getFullName().equals(editUserDto.getFullName()) &&
                !profileToEdit.getEmail().equals(editUserDto.getEmail())) {
            redirectAttributes.addFlashAttribute("editUser",profileToEdit);
            redirectAttributes.addFlashAttribute("wrong_email",true);
            redirectAttributes.addFlashAttribute("wrong_fullName",true);
            return "redirect:/profile/edit";
        }

        if (bindingResult.hasErrors() && !profileToEdit.getEmail().equals(editUserDto.getEmail())) {
            redirectAttributes.addFlashAttribute("editUser",profileToEdit);
            redirectAttributes.addFlashAttribute("wrong_email",true);
            return "redirect:/profile/edit" ;
        }

        if (bindingResult.hasFieldErrors("fullName") && !profileToEdit.getFullName().equals(editUserDto.getFullName())) {
            redirectAttributes.addFlashAttribute("editUser",profileToEdit);
            redirectAttributes.addFlashAttribute("wrong_fullName", true);
            return "redirect:/profile/edit";
        }

        if (!profileToEdit.getEmail().equals(editUserDto.getEmail()) ||
        !profileToEdit.getFullName().equals(editUserDto.getFullName())) {
            userService.doEditProfile(domaniUserDetail.getId(), editUserDto);
        }
        return "redirect:/profile";
    }

    @RequestMapping("/specialties")
    @ResponseBody
    public ResponseEntity<List<ProductView>> getSpecialties() {
        List<ProductView> randomSpecialties = productService.getRandomSpecialties();
        if (randomSpecialties.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(randomSpecialties);
    }

}
