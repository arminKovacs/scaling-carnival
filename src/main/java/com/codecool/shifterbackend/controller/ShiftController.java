package com.codecool.shifterbackend.controller;

import com.codecool.shifterbackend.controller.dto.ShiftAssignmentDetails;
import com.codecool.shifterbackend.entity.Shift;
import com.codecool.shifterbackend.entity.WorkerShift;
import com.codecool.shifterbackend.service.ShiftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class ShiftController {

    @Autowired
    private ShiftService shiftService;

    @GetMapping("/shifts")
    private List<Shift> getAllShifts() {
        return shiftService.getAllBaseShifts();
    }

    @GetMapping("/worker-shifts")
    private List<WorkerShift> getAllWorkerShifts() {
        return shiftService.getAllWorkerShifts();
    }

    @PostMapping("/assign-shift")
    private List<WorkerShift> assignShiftToWorker(@RequestBody ShiftAssignmentDetails shiftAssignmentDetails) {
        shiftService.assignShiftToUser(
                shiftAssignmentDetails.getShiftId(),
                shiftAssignmentDetails.getWorkerId(),
                shiftAssignmentDetails.getStartDate(),
                shiftAssignmentDetails.getEndDate()
        );
        return shiftService.getAllWorkerShifts();
    }
}
