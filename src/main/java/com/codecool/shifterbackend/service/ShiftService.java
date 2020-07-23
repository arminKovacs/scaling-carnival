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
    public boolean assignShiftToUser(Long shiftId, Long userId, String startDate, String endDate) {
        ShifterUser user = userRepository.getOne(userId);
        Shift shift = shiftRepository.getOne(shiftId);
        if (alreadyAssigned(user, shift, startDate, endDate)){
            return false;
        }
        WorkerShift workerShift = new WorkerShift(shift, startDate, endDate, user);
        user.addToShifts(workerShift);
        workerShiftRepository.save(workerShift);
        userRepository.save(user);
        return true;
    }

    public List<WorkerShift> getAllWorkerShifts() {
        return workerShiftRepository.findAll();
    }

    private boolean alreadyAssigned(ShifterUser user, Shift shift, String startDate, String endDate) {
        for (WorkerShift workerShift : user.getWorkerShifts()) {
            if (workerShift.getName().equals(shift.getName()) &&
                newDateIsBeforeToday(startDate, endDate) ||
                dateInRange(workerShift, startDate) ||
                dateInRange(workerShift, endDate)
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

    private boolean newDateIsBeforeToday(String startDate, String endDate){
        LocalDate today = LocalDate.now();
        LocalDate newStartDate = LocalDate.parse(startDate);
        LocalDate newEndDate = LocalDate.parse(endDate);
        return newStartDate.isBefore(today) || newEndDate.isBefore(today);
    }
}
