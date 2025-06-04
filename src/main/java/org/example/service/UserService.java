package org.example.service;

import org.example.model.User;
import org.example.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean saveUser(User user) {
        System.out.println("Saving user: " + user.getUsername() + "started");
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            System.out.println("Saving user: " + user.getUsername() + "failed: username is already taken");
            return false;
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        System.out.println("Saving user: " + user.getUsername() + "password is setted");
        userRepository.save(user);
        System.out.println("Saving user: " + user.getUsername() + "saved successfully");
        return true;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        System.out.println("loadUserByUsername called with: " + username);
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }
}
