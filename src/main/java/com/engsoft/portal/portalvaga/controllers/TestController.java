package com.engsoft.portal.portalvaga.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestController {
    @GetMapping("/test")
    public String test(Model model) {
        model.addAttribute("mensagem", "Thymeleaf está funcionando com variáveis!");
        return "test";
    }
}
