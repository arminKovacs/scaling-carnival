package com.codecool.shifterbackend.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Shift {

    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String startTime;
    private String endTime;
    private String startDate;
    private String endDate;

    @ManyToOne
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private ShifterUser shifterUser;
}
