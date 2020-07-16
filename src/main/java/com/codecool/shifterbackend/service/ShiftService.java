package com.codecool.shifterbackend.service;

import com.codecool.shifterbackend.entity.Shift;
import com.codecool.shifterbackend.repository.ShiftRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShiftService {

    @Autowired
    private ShiftRepository shiftRepository;

    public List<Shift> getAll() {
        return shiftRepository.findAll();
    }

    public void registerNewShift(String name, String startTime, String endTime, String startDate, String endDate){
        Shift newShift = Shift.builder()
                .name(name)
                .startTime(startTime)
                .endTime(endTime)
                .startDate(startDate)
                .endDate(endTime)
                .build();
        shiftRepository.save(newShift);
    }


}
