package com.codecool.shifterbackend.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Data
public class User {
    @Id
    private Long id;
    private String username;
    private String password;
    @ElementCollection
    private Set<Shift> shifts;
}
