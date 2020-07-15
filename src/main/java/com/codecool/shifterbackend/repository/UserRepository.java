package com.codecool.shifterbackend.repository;

import com.codecool.shifterbackend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
