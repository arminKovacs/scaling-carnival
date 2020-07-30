package com.codecool.shifterbackend.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
public class RequestShift extends Shift {

    private String startDate;
    private String endDate;

    @ManyToOne
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private ShifterUser shifterUser;

}
