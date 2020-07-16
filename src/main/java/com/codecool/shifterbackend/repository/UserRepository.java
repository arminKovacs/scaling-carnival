package com.codecool.shifterbackend.repository;

import com.codecool.shifterbackend.entity.ShifterUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<ShifterUser, Long> {

    ShifterUser findShifterUserByUsername(String username);
}
