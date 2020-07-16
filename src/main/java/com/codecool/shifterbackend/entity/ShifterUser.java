package com.codecool.shifterbackend.entity;

import com.codecool.shifterbackend.model.Role;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Data
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class ShifterUser {
    @Id
    @GeneratedValue
    private Long id;

    private String username;
    private String hashedPassword;
    private String email;
    private String firstName;
    private String lastName;

    @OneToMany(mappedBy = "shifterUserId")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<Shift> shifts;

    @ElementCollection
    @Singular
    private Set<Role> roles;
}
