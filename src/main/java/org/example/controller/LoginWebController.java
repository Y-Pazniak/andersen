package org.example.controller;

import org.example.model.Role;
import org.example.model.User;
import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/security")
public class LoginWebController {
    private UserService userService;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public void setUserService(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/login-form")
    public String login() {
        return "login-form";
    }

    @PostMapping("/login")
    public String login(@RequestParam("username") String username, @RequestParam("password") String password) {
        try {
            User user = (User) userService.loadUserByUsername(username);
            System.out.println("user found: "  + user.getUsername());
            if (user != null && passwordEncoder.matches(password, user.getPassword())) {
                System.out.println("user is not null and password do matches");
                if (user.getRole().equals(Role.ADMIN)) {
                    System.out.println("user is admin");
                    return "/admin-dashboard";
                } else {
                    return "/customer-dashboard";
                }
            }
        } catch (UsernameNotFoundException e) {
            System.out.println("user not found: " + username);
            e.printStackTrace();
        }

        return "/login-form";
    }
}
