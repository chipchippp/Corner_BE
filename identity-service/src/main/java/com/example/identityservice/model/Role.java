package com.example.identityservice.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "tbl_role")
public class Role extends AbstractEntity<Integer> {
    String name;
    String description;

    @ManyToMany
    Set<Permission> permissions;
}
