package com.codecool.shifterbackend.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Shift {

    @Id
    private Long id;
    private String name;
    private LocalDateTime timeFrame;
    private Long userId;
}
