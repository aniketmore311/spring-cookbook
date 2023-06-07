package com.aniketmore.forms.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.aniketmore.forms.dto.UserFormData;

import jakarta.validation.Valid;

@Controller
public class HelloController {
    @GetMapping("/")
    public String hello() {
        return "hello";
    }

    @GetMapping("/user")
    public String renderUserForm(Model model) {
        model.addAttribute("form", new UserFormData());
        return "form";
    }

    @PostMapping("/user")
    public String processUserForm(@Valid @ModelAttribute("form") UserFormData form, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "form";
        }
        model.addAttribute("name", form.getName());
        model.addAttribute("email", form.getEmail());
        return "success";
    }
}
