package com.codecool.shifterbackend.entity;

import com.codecool.shifterbackend.model.Role;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
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

    @OneToMany(mappedBy = "shifterUser")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @Singular
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JsonIgnore
    private Set<WorkerShift> workerShifts;

    @ElementCollection
    @Singular
    private Set<Role> roles;

    public void addToShifts(WorkerShift workerShift) {
        this.workerShifts.add(workerShift);
    }
}
