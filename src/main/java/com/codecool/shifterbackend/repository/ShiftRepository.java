package com.codecool.shifterbackend.repository;

import com.codecool.shifterbackend.entity.Shift;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShiftRepository extends JpaRepository<Shift, Long> {
}
