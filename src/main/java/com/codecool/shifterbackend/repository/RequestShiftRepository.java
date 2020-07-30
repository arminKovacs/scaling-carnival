package com.codecool.shifterbackend.repository;

import com.codecool.shifterbackend.entity.RequestShift;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestShiftRepository extends JpaRepository<RequestShift, Long> {
}
