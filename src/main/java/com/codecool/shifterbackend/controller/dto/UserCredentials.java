package com.codecool.shifterbackend.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCredentials {

    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;

    public UserCredentials(String username, String password) {
        this.username = username;
        this.password = password;
    }
}