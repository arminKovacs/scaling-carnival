package com.codecool.shifterbackend.service;

import com.codecool.shifterbackend.controller.dto.ShiftAssignmentDetails;
import com.codecool.shifterbackend.entity.RequestShift;
import com.codecool.shifterbackend.entity.Shift;
import com.codecool.shifterbackend.entity.WorkerShift;
import com.codecool.shifterbackend.entity.ShifterUser;
import com.codecool.shifterbackend.repository.RequestShiftRepository;
import com.codecool.shifterbackend.repository.ShiftRepository;
import com.codecool.shifterbackend.repository.WorkerShiftRepository;
import com.codecool.shifterbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class ShiftService {

    @Autowired
    private ShiftRepository shiftRepository;

    @Autowired
    private WorkerShiftRepository workerShiftRepository;

    @Autowired
    private RequestShiftRepository requestShiftRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Shift> getAllBaseShifts() {
        return shiftRepository.findAll();
    }

    public void registerNewShift(String name, String startTime, String endTime, String color) {
        Shift newShift = Shift.builder()
                .name(name)
                .startTime(startTime)
                .endTime(endTime)
                .shiftColor(color)
                .build();
        shiftRepository.save(newShift);
    }

    @Transactional
    public void assignShiftToUser(ShiftAssignmentDetails shiftAssignmentDetails) {
        ShifterUser user = userRepository.getOne(shiftAssignmentDetails.getShifterUserId());
        Shift shift = shiftRepository.getOne(shiftAssignmentDetails.getShiftId());
        WorkerShift workerShift = new WorkerShift(shift, shiftAssignmentDetails.getStartDate(),
                shiftAssignmentDetails.getEndDate(), user);
        user.addToShifts(workerShift);
        workerShiftRepository.save(workerShift);
        userRepository.save(user);
    }

    @Transactional
    public void assignRequestShiftToUser(ShiftAssignmentDetails shiftAssignmentDetails) {
        ShifterUser user = userRepository.getOne(shiftAssignmentDetails.getShifterUserId());
        Shift shift = shiftRepository.getOne(shiftAssignmentDetails.getShiftId());
        RequestShift requestShift = RequestShift.builder()
                .shifterUser(user)
                .name(shift.getName())
                .shiftColor(shift.getShiftColor())
                .requestedShiftId(shiftAssignmentDetails.getShiftId())
                .startDate(shiftAssignmentDetails.getStartDate())
                .endDate(shiftAssignmentDetails.getEndDate())
                .startTime(shiftAssignmentDetails.getStartTime())
                .endTime(shiftAssignmentDetails.getEndTime())
                .build();
        user.addToRequestShifts(requestShift);
        requestShiftRepository.save(requestShift);
        userRepository.save(user);
    }

    public List<WorkerShift> getAllWorkerShifts() {
        return workerShiftRepository.findAll();
    }

    public boolean shiftIsAlreadyAssigned(ShiftAssignmentDetails shiftAssignmentDetails) {
        ShifterUser user = userRepository.getOne(shiftAssignmentDetails.getShifterUserId());
        for (WorkerShift workerShift : user.getWorkerShifts()) {
            if (timeInRange(workerShift, shiftAssignmentDetails) &&
                (dateInRange(workerShift, shiftAssignmentDetails.getStartDate()) ||
                dateInRange(workerShift, shiftAssignmentDetails.getEndDate()))
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

    private boolean timeInRange(WorkerShift workerShift, ShiftAssignmentDetails shiftAssignmentDetails){
        LocalDateTime workShiftStartTime = LocalDateTime.now().with(LocalTime.parse(workerShift.getStartTime()));
        LocalDateTime workShiftEndTime = LocalDateTime.now().with(LocalTime.parse(workerShift.getEndTime()));
        LocalDateTime newStartTime = LocalDateTime.now().with(LocalTime.parse(shiftAssignmentDetails.getStartTime()));
        LocalDateTime newEndTime = LocalDateTime.now().with(LocalTime.parse(shiftAssignmentDetails.getEndTime()));
        if (workShiftEndTime.isBefore(workShiftStartTime)) {
            workShiftEndTime = workShiftEndTime.plusDays(1L);
        }
        if (newEndTime.isBefore(newStartTime)) {
            newEndTime = newEndTime.plusDays(1L);
        }
        if(newStartTime.equals(workShiftStartTime) && newEndTime.equals(workShiftEndTime)){
            return true;
        }
        return (newStartTime.isAfter(workShiftStartTime) && newStartTime.isBefore(workShiftEndTime)) ||
                (newEndTime.isAfter(workShiftStartTime) && newEndTime.isBefore(workShiftEndTime)) &&
                (workShiftStartTime.isAfter(newStartTime) && workShiftStartTime.isBefore(newEndTime)) ||
                (workShiftEndTime.isAfter(newStartTime) && workShiftEndTime.isBefore(newEndTime));
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

    public void deleteWorkerShift(Long workerShiftId) {
        workerShiftRepository.deleteById(workerShiftId);
    }

    public List<RequestShift> getAllRequestShifts() {
        return requestShiftRepository.findAll();
    }

    public void deleteRequestShift(Long requestShiftId) {
        requestShiftRepository.deleteById(requestShiftId);
    }
}
