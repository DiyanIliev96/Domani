package com.iliev.domani.web;

import com.iliev.domani.model.enums.CategoryNameEnum;
import com.iliev.domani.model.view.ProductView;
import com.iliev.domani.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class MenuController {

    private final ProductService productService;

    public MenuController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/menu")
    private String getMenu(Model model) {
        List<ProductView> allLunchProducts = productService.getAllProductsByCategory(CategoryNameEnum.Lunch);
        List<ProductView> allPizzaProducts = productService.getAllProductsByCategory(CategoryNameEnum.Pizza);
        model.addAttribute("lunchProducts",allLunchProducts);
        model.addAttribute("pizzaProducts",allPizzaProducts);
        return "menu";
    }
}
