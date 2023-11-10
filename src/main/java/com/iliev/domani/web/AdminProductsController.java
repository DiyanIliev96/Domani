package com.iliev.domani.web;

import com.iliev.domani.model.dto.AddProductDto;
import com.iliev.domani.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

@Controller
@RequestMapping("/admin")
public class AdminProductsController {

    private final ProductService productService;

    public AdminProductsController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/menu/management")
    private String getMenuManagement() {

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
    private String addProduct(AddProductDto addProductDto) throws IOException {

        productService.addProduct(addProductDto);
        return "redirect:/admin/menu/management";
    }
}
