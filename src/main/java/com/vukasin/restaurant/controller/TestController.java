package com.vukasin.restaurant.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/api/test")
    public String hello() {
        return "Backend radi!";
    }
}
