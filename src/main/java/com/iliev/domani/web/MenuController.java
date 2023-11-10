package com.iliev.domani.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MenuController {


    @GetMapping("/menu")
    private String getMenu() {

        return "menu";
    }
}
