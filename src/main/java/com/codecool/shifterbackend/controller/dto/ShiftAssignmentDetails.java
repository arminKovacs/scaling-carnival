package com.codecool.shifterbackend.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShiftAssignmentDetails {
    private String workerId;
    private String shiftId;
    private String startDate;
    private String endDate;
}
