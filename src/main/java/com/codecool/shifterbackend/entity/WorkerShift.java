package com.codecool.shifterbackend.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@SuperBuilder
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class WorkerShift extends Shift {

    public WorkerShift(Shift shift,String startDate, String endDate, ShifterUser shifterUser) {
        this.name = shift.getName();
        this.startTime = shift.getStartTime();
        this.endTime = shift.getEndTime();
        this.startDate = startDate;
        this.endDate = endDate;
        this.shifterUser = shifterUser;
    }

    private String startDate;
    private String endDate;

    @ManyToOne
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private ShifterUser shifterUser;
}
