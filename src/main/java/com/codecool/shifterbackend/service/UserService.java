package com.codecool.shifterbackend.service;

import com.codecool.shifterbackend.entity.ShifterUser;
import com.codecool.shifterbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<ShifterUser> getAll() {
        return userRepository.findAll();
    }

    public ShifterUser returnById(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }

    public ShifterUser findByName(String username) {
        return userRepository.findShifterUserByUsername(username);
    }
}
