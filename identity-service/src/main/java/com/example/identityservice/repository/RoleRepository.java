package com.example.identityservice.repository;

import com.example.identityservice.model.Role;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    boolean existsByName(String name);
    @EntityGraph(attributePaths = "permissions")
    List<Role> findAll();
}
