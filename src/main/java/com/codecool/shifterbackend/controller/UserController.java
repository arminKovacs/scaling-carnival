package com.codecool.shifterbackend.controller;

import com.codecool.shifterbackend.entity.ShifterUser;
import com.codecool.shifterbackend.security.JwtUtil;
import com.codecool.shifterbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.Duration;
import java.util.List;

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

    @GetMapping("/{userId}")
    private ShifterUser getUserById(@RequestParam Long userId) {
        return userService.returnById(userId);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserCredentials tripUser, HttpServletResponse response, HttpServletRequest request) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                tripUser.getUsername(),
                tripUser.getPassword()
        ));
        String jwtToken = jwtUtil.generateToken(authentication);
        addTokenToCookie(response, jwtToken);
        return ResponseEntity.ok().body(tripUser.getUsername());
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody UserCredentials tripUser, HttpServletResponse response) {
        tripUserService.registerAllData(tripUser);
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                tripUser.getUsername(),
                tripUser.getPassword()
        ));
        String jwtToken = jwtUtil.generateToken(authentication);
        addTokenToCookie(response, jwtToken);
        return ResponseEntity.status(HttpStatus.CREATED).body(tripUser.getUsername());
    }

    private void addTokenToCookie(HttpServletResponse response, String token) {
        ResponseCookie cookie = ResponseCookie.from("token", token)
                .domain("localhost") // should be parameterized
                .sameSite("Strict")  // CSRF
//                .secure(true)
                .maxAge(Duration.ofHours(24))
                .httpOnly(true)      // XSS
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
                .domain("localhost") // should be parameterized
                .sameSite("Strict")  // CSRF
//                .secure(true)
                .maxAge(0)
                .httpOnly(true)      // XSS
                .path("/")
                .build();
        response.addHeader("Set-Cookie", cookie.toString());
    }

}
