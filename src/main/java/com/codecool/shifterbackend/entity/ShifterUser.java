package com.codecool.shifterbackend.entity;

import com.codecool.shifterbackend.model.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Data
public class ShifterUser {
    @Id
    @GeneratedValue
    private Long id;

    private String username;
    private String hashedPassword;
    private String email;
    private String firstName;
    private String lastName;
    private String calendarColor;

    @OneToMany(mappedBy = "shifterUser")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @Singular
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JsonIgnore
    private Set<WorkerShift> workerShifts;

    @OneToMany(mappedBy = "shifterUser")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @Singular
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JsonIgnore
    private Set<RequestShift> requestShifts;

    @ElementCollection
    @Singular
    private Set<Role> roles;

    public void addToShifts(WorkerShift workerShift) {
        this.workerShifts.add(workerShift);
    }

    public void addToRequestShifts(RequestShift requestShift) {
        this.requestShifts.add(requestShift);
    }

    public static String generateColor(){
        String letters = "0123456789ABCDEF";
        StringBuilder color = new StringBuilder("#");
        for (int i = 0; i < 6; i++) {
            color.append(letters.split("")[(int) Math.floor(Math.random() * 16)]);
        }
        return color.toString();
    }
}
