package com.codecool.shifterbackend.repository;

import com.codecool.shifterbackend.entity.Shift;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ShiftRepository extends JpaRepository<Shift, Long> {

    @Query("SELECT s FROM Shift s WHERE TYPE (s) = Shift ")
    List<Shift> findAll();
}
