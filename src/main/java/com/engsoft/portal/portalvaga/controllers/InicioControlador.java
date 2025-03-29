    package com.engsoft.portal.portalvaga.controllers;

    import org.springframework.stereotype.Controller;
    import org.springframework.web.bind.annotation.GetMapping;

    @Controller
    public class InicioControlador {

        @GetMapping("/")
        public String mostrarPaginaInicial() {
            return "index";
        }
    }

