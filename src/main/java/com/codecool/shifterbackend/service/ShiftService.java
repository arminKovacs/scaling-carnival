package com.codecool.shifterbackend.service;

import com.codecool.shifterbackend.entity.Shift;
import com.codecool.shifterbackend.entity.WorkerShift;
import com.codecool.shifterbackend.entity.ShifterUser;
import com.codecool.shifterbackend.repository.ShiftRepository;
import com.codecool.shifterbackend.repository.WorkerShiftRepository;
import com.codecool.shifterbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class ShiftService {

    @Autowired
    private ShiftRepository shiftRepository;

    @Autowired
    private WorkerShiftRepository workerShiftRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Shift> getAllBaseShifts() {
        return shiftRepository.findAll();
    }

    public void registerNewShift(String name, String startTime, String endTime) {
        Shift newShift = Shift.builder()
                .name(name)
                .startTime(startTime)
                .endTime(endTime)
                .build();
        shiftRepository.save(newShift);
    }

    @Transactional
    public String assignShiftToUser(Long shiftId, Long userId, String startDate, String endDate) {
        ShifterUser user = userRepository.getOne(userId);
        Shift shift = shiftRepository.getOne(shiftId);
        if (alreadyAssigned(user, shift, startDate, endDate)){
            return "Shift is already assigned to " + user.getUsername() + "!";
        }
        WorkerShift workerShift = new WorkerShift(shift, startDate, endDate, user);
        user.addToShifts(workerShift);
        workerShiftRepository.save(workerShift);
        userRepository.save(user);
        return "Shift has been assigned to " + user.getUsername() + "!";
    }

    public List<WorkerShift> getAllWorkerShifts() {
        return workerShiftRepository.findAll();
    }

    public Boolean alreadyAssigned(ShifterUser user, Shift shift, String startDate, String endDate) {
        for (WorkerShift workerShift : user.getWorkerShifts()){
            if (workerShift.getStartDate().equals(startDate) &&
                workerShift.getEndDate().equals(endDate) &&
                workerShift.getStartTime().equals(shift.getStartTime()) &&
                workerShift.getEndTime().equals(shift.getEndTime())
            ) {
                return true;
            }
        }
        return false;
    }

    public void addNewShiftToRepository(Shift shift) {
        shiftRepository.save(shift);
    }
}
