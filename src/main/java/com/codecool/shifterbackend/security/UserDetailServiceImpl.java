package com.codecool.shifterbackend.security;

import com.codecool.shifterbackend.entity.ShifterUser;
import com.codecool.shifterbackend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ShifterUser shifterUser = userService
                .findByName(username);
        if (shifterUser == null) {
            throw new UsernameNotFoundException("Username not found");
        }

        return new User(
                shifterUser.getUsername(),
                shifterUser.getHashedPassword(),
                shifterUser.getRoles()
                        .stream()
                        .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                        .collect(Collectors.toList())
        );
    }
}
