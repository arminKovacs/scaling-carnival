package com.codecool.shifterbackend.controller;

import com.codecool.shifterbackend.controller.dto.ShiftAssignmentDetails;
import com.codecool.shifterbackend.entity.Shift;
import com.codecool.shifterbackend.service.ShiftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin
public class ShiftController {

    @Autowired
    private ShiftService shiftService;

    @GetMapping("/shifts")
    private ResponseEntity<Object> getAllShifts() {
        return ResponseEntity.ok(shiftService.getAllBaseShifts());
    }

    @PostMapping("/shifts")
    private ResponseEntity<Object> createNewShift(@Valid @RequestBody Shift shift){
        shiftService.addNewShiftToRepository(shift);
        return ResponseEntity.ok(shiftService.getAllBaseShifts());
    }

    @GetMapping("/worker-shifts")
    private ResponseEntity<Object> getAllWorkerShifts() {
        return ResponseEntity.ok(shiftService.getAllWorkerShifts());
    }

    @PostMapping("/worker-shifts/{workerId}")
    private ResponseEntity<Object> assignShiftToWorker(@PathVariable Long workerId,
                                                       @RequestBody ShiftAssignmentDetails shiftAssignmentDetails) {
        if (shiftService.newDateIsBeforeToday(shiftAssignmentDetails) ||
            shiftService.shiftIsAlreadyAssigned(workerId, shiftAssignmentDetails)) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        shiftService.assignShiftToUser(workerId, shiftAssignmentDetails);
        return ResponseEntity.ok(shiftService.getAllWorkerShifts());
    }

    @DeleteMapping("/worker-shifts/{workerShiftId}")
    private ResponseEntity<Object> deleteWorkerShift(@PathVariable Long workerShiftId){
        shiftService.deleteWorkerShift(workerShiftId);
        return ResponseEntity.ok(shiftService.getAllWorkerShifts());
    }

    @GetMapping("/shift-requests")
    private ResponseEntity<Object> getRequestShifts(){
        return ResponseEntity.ok(shiftService.getAllRequestShifts());
    }

    @PostMapping("/shift-requests/{workerId}")
    private ResponseEntity<Object> sendShiftRequest(@PathVariable Long workerId,
                                                    @RequestBody ShiftAssignmentDetails shiftAssignmentDetails) {
        shiftService.assignRequestShiftToUser(workerId,shiftAssignmentDetails);
        return ResponseEntity.ok(shiftService.getAllRequestShifts());
    }

    @DeleteMapping("/shift-requests/{requestShiftId}")
    private ResponseEntity<Object> deleteRequestShift(@PathVariable Long requestShiftId){
        shiftService.deleteRequestShift(requestShiftId);
        return ResponseEntity.ok(shiftService.getAllRequestShifts());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
