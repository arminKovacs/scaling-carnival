package com.codecool.shifterbackend.service;

import com.codecool.shifterbackend.controller.dto.ShiftAssignmentDetails;
import com.codecool.shifterbackend.entity.Shift;
import com.codecool.shifterbackend.entity.WorkerShift;
import com.codecool.shifterbackend.entity.ShifterUser;
import com.codecool.shifterbackend.repository.ShiftRepository;
import com.codecool.shifterbackend.repository.WorkerShiftRepository;
import com.codecool.shifterbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
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
    public void assignShiftToUser(Long userId, ShiftAssignmentDetails shiftAssignmentDetails) {
        ShifterUser user = userRepository.getOne(userId);
        Shift shift = shiftRepository.getOne(shiftAssignmentDetails.getShiftId());
        WorkerShift workerShift = new WorkerShift(shift, shiftAssignmentDetails.getStartDate(),
                shiftAssignmentDetails.getEndDate(), user);
        user.addToShifts(workerShift);
        workerShiftRepository.save(workerShift);
        userRepository.save(user);
    }

    public List<WorkerShift> getAllWorkerShifts() {
        return workerShiftRepository.findAll();
    }

    public boolean shiftIsAlreadyAssigned(Long userId, ShiftAssignmentDetails shiftAssignmentDetails) {
        ShifterUser user = userRepository.getOne(userId);
        Shift shift = shiftRepository.getOne(shiftAssignmentDetails.getShiftId());
        for (WorkerShift workerShift : user.getWorkerShifts()) {
            if (workerShift.getName().equals(shift.getName()) &&
                dateInRange(workerShift, shiftAssignmentDetails.getStartDate()) ||
                dateInRange(workerShift, shiftAssignmentDetails.getEndDate())
            ) {
                return true;
            }
        }
        return false;
    }

    private boolean dateInRange(WorkerShift workerShift, String dateToCheck){
        LocalDate workShiftStartDate = LocalDate.parse(workerShift.getStartDate());
        LocalDate workShiftEndDate = LocalDate.parse(workerShift.getEndDate());
        LocalDate date = LocalDate.parse(dateToCheck);
        return !date.isBefore(workShiftStartDate) && !date.isAfter(workShiftEndDate);
    }

    public void addNewShiftToRepository(Shift shift) {
        shiftRepository.save(shift);
    }

    public boolean newDateIsBeforeToday(ShiftAssignmentDetails shiftAssignmentDetails){
        LocalDate today = LocalDate.now();
        LocalDate newStartDate = LocalDate.parse(shiftAssignmentDetails.getStartDate());
        LocalDate newEndDate = LocalDate.parse(shiftAssignmentDetails.getEndDate());
        return newStartDate.isBefore(today) || newEndDate.isBefore(today);
    }
}
