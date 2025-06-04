package org.example.controller;

import org.example.model.Admin;
import org.example.model.Customer;
import org.example.model.Role;
import org.example.model.User;
import org.example.service.UserService;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/security")
public class RegistrationWebController {
    private final UserService userService;

    @Autowired
    public RegistrationWebController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/registration-form")
    public String registration(Model model) {
        model.addAttribute("roles", Role.values());
        return "registration-form";
    }

    @PostMapping("/registration")
    public String registration(@RequestParam String username, @RequestParam String password, @RequestParam String role) {
        if (role.equals(Role.ADMIN.name())) {
            userService.saveUser(new Admin(username, password, Role.ADMIN));
        } else {
            userService.saveUser(new Customer(username, password, Role.CUSTOMER));
        }
        return "redirect:/security/login";
    }
}
