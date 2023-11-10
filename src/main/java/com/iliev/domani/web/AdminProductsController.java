package com.iliev.domani.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminProductsController {

    @GetMapping("/menu/management")
    private String getMenuManagement() {

        return "menu-management";
    }

    @GetMapping("/menu/add")
    private String getAddProduct() {

        return "add-menu-item";
    }
}
