package com.aniketmore.springsec1.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
public class IndexController {

    @GetMapping({ "/", "" })
    public String getHealthCheck() {
        return "ok";
    }

    @GetMapping("/hello")
    public String getHello() {
        return "world";
    }

    @GetMapping("/admin/hello")
    public String getHelloAdmin() {
        return "world";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

}
