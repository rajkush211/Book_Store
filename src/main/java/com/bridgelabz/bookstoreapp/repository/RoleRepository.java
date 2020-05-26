package com.bridgelabz.bookstoreapp.repository;

import com.bridgelabz.bookstoreapp.entity.Role;
import com.bridgelabz.bookstoreapp.model.ERole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
