package com.codecool.shifterbackend.entity;

import com.codecool.shifterbackend.model.Role;
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
    private String password;
    private String email;
    @OneToMany(mappedBy = "shifterUserId")
    private Set<Shift> shifts;
    @ElementCollection
    private Set<Role> roles;
}
