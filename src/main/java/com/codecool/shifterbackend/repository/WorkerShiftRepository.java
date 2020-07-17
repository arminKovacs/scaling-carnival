package com.codecool.shifterbackend.repository;

import com.codecool.shifterbackend.entity.WorkerShift;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface WorkerShiftRepository extends JpaRepository<WorkerShift, Long> {


}
