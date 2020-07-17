package com.codecool.shifterbackend.repository;

import com.codecool.shifterbackend.entity.WorkerShift;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkerShiftRepository extends JpaRepository<WorkerShift, Long> {

    WorkerShift findShiftByName(String name);
}
