package org.example.pioneer.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/api/pioneer")
    public String home() {
        return "Bienvenido a mi aplicación!";
    }
}
