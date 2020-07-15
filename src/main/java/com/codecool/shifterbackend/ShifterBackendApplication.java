package com.codecool.shifterbackend;

import com.codecool.shifterbackend.entity.User;
import com.codecool.shifterbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class ShifterBackendApplication {

    @Autowired
    private UserRepository userRepository;

    public static void main(String[] args) {
        SpringApplication.run(ShifterBackendApplication.class, args);
    }

    @Bean
    public CommandLineRunner init(){
        return args -> {
            User taki = User.builder()
                    .username("Taki Bá")
                    .email("taki@freemail.hu")
                    .build();

            User feri = User.builder()
                    .username("Vágási Feri")
                    .email("feri@freemail.hu")
                    .build();

            User gabor = User.builder()
                    .username("Gábor Gábor")
                    .email("gg@freemail.hu")
                    .build();

            userRepository.saveAll(Arrays.asList(taki, feri, gabor));
        };
    }
}
