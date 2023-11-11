package com.iliev.domani.web;

import com.iliev.domani.model.dto.AddProductDto;
import com.iliev.domani.model.view.ProductView;
import com.iliev.domani.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminProductsController {

    private final ProductService productService;

    public AdminProductsController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/menu/management")
    private String getMenuManagement(Model model) {
        List<ProductView> allProducts = productService.getAllProducts();

        model.addAttribute("allProducts",allProducts);
        return "menu-management";
    }

    @GetMapping("/menu/add")
    private String getAddProduct(Model model) {
        if (!model.containsAttribute("newProduct")) {
            model.addAttribute("newProduct",new AddProductDto());
        }
        return "add-menu-item";
    }

    @PostMapping("/menu/add")
    private String addProduct(@Valid AddProductDto addProductDto, BindingResult bindingResult,
                              RedirectAttributes redirectAttributes) throws IOException {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("newProduct",addProductDto);
            redirectAttributes.addFlashAttribute(BindingResult.MODEL_KEY_PREFIX + "newProduct",bindingResult);
            return "redirect:/admin/menu/add";
        }
        productService.addProduct(addProductDto);
        return "redirect:/admin/menu/management";
    }
}
