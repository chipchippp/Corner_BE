package com.example.identityservice.repository;

import com.example.identityservice.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    boolean existsByName(String name);
    @EntityGraph(attributePaths = "permissions")
    List<Role> findAll();

    @Query("SELECT r FROM Role r LEFT JOIN FETCH r.permissions WHERE r.id = :id")
    Optional<Role> findByIdFetchPermissions(@Param("id") Integer id);
}
