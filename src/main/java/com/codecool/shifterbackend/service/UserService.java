package com.codecool.shifterbackend.service;

import com.codecool.shifterbackend.entity.ShifterUser;
import com.codecool.shifterbackend.model.Role;
import com.codecool.shifterbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public List<ShifterUser> getAll() {
        return userRepository.findAll();
    }

    public ShifterUser returnById(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }

    public ShifterUser findByName(String username) {
        return userRepository.findShifterUserByUsername(username);
    }

    public ShifterUser register(String username, String password, String firstName, String lastName,
                                String email, Set<Role> roles) {
        return userRepository.save(
                ShifterUser.builder()
                        .username(username)
                        .hashedPassword(encoder.encode(password))
                        .firstName(firstName)
                        .lastName(lastName)
                        .email(email)
                        .roles(roles)
                        .calendarColor(ShifterUser.generateColor())
                        .build()
        );
    }
}
