package com.codecool.shifterbackend.controller;

import com.codecool.shifterbackend.controller.dto.UserCredentials;
import com.codecool.shifterbackend.entity.ShifterUser;
import com.codecool.shifterbackend.model.Role;
import com.codecool.shifterbackend.security.JwtUtil;
import com.codecool.shifterbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.time.Duration;
import java.util.List;
import java.util.Set;

@CrossOrigin
@RestController
public class UserController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;
    public static final String TOKEN = "token";

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    private List<ShifterUser> getAllUsers() {
        return userService.getAll();
    }

    @GetMapping("user/{shifterUserId}")
    private ShifterUser getUserById(@PathVariable Long shifterUserId) {
        return userService.returnById(shifterUserId);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserCredentials shifterUser, HttpServletResponse response) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                shifterUser.getUsername(),
                shifterUser.getPassword()
        ));
        String jwtToken = jwtUtil.generateToken(authentication);
        addTokenToCookie(response, jwtToken);
        return ResponseEntity.ok().body(shifterUser.getUsername());
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody UserCredentials newUser, HttpServletResponse response) {
        userService.register(newUser.getUsername(), newUser.getPassword(), newUser.getFirstName(),
                newUser.getLastName(), newUser.getEmail(), Set.of(Role.USER));
        login(newUser, response);
        return ResponseEntity.status(HttpStatus.CREATED).body(newUser.getUsername());
    }

    private void addTokenToCookie(HttpServletResponse response, String token) {
        ResponseCookie cookie = ResponseCookie.from("token", token)
                .domain("localhost")
                .sameSite("Strict")
//                .secure(true)
                .maxAge(Duration.ofHours(24))
                .httpOnly(true)
                .path("/")
                .build();
        response.addHeader("Set-Cookie", cookie.toString());
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletResponse response) {
        createLogoutCookie(response);
        return ResponseEntity.ok().build();
    }

    private void createLogoutCookie(HttpServletResponse response) {
        ResponseCookie cookie = ResponseCookie.from("token", "")
                .domain("localhost")
                .sameSite("Strict")
//                .secure(true)
                .maxAge(0)
                .httpOnly(true)
                .path("/")
                .build();
        response.addHeader("Set-Cookie", cookie.toString());
    }

}
