package com.codecool.shifterbackend.service;

import com.codecool.shifterbackend.entity.WorkerShift;
import com.codecool.shifterbackend.entity.ShifterUser;
import com.codecool.shifterbackend.repository.WorkerShiftRepository;
import com.codecool.shifterbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class ShiftService {

    @Autowired
    private WorkerShiftRepository workerShiftRepository;

    @Autowired
    private UserRepository userRepository;


    public List<WorkerShift> getAll() {
        return workerShiftRepository.findAll();
    }

    public void registerNewShift(String name, String startTime, String endTime,
                                 String startDate, String endDate){
        WorkerShift newWorkerShift = WorkerShift.builder()
                .name(name)
                .startTime(startTime)
                .endTime(endTime)
                .startDate(startDate)
                .endDate(endDate)
                .build();
        workerShiftRepository.save(newWorkerShift);
    }

    @Transactional
    public void assignShiftToUser(Long shiftId, Long userId){
        ShifterUser user = userRepository.getOne(userId);
        WorkerShift workerShift = workerShiftRepository.getOne(shiftId);
        workerShift.setShifterUser(user);
        user.addToShifts(workerShift);
        workerShiftRepository.save(workerShift);
        userRepository.save(user);
    }

}
