package com.codecool.shifterbackend;

import com.codecool.shifterbackend.repository.ShiftRepository;
import com.codecool.shifterbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ShifterBackendApplication {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ShiftRepository shiftRepository;

    public static void main(String[] args) {
        SpringApplication.run(ShifterBackendApplication.class, args);
    }

}
