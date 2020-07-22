package com.codecool.shifterbackend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Shift {
    @Id
    @GeneratedValue
    protected Long id;

    @NotBlank(message = "Shift name is missing!")
    protected String name;
    @NotBlank(message = "Please add a start time!")
    protected String startTime;
    @NotBlank(message = "Please add an end time!")
    protected String endTime;
}
