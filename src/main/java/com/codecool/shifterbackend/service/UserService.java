package com.codecool.shifterbackend.service;

import com.codecool.shifterbackend.entity.User;
import com.codecool.shifterbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User returnById(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }
}
