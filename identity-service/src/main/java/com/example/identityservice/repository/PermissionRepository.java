package com.example.identityservice.repository;

import com.example.identityservice.model.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Integer> {
    boolean existsByName(String name);

//    List<Permission> findAllById(Set<String> permissions);
}
