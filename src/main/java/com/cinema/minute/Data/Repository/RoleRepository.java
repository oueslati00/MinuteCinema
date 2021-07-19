package com.cinema.minute.Data.Repository;

import com.cinema.minute.Data.Entity.ERole;
import com.cinema.minute.Data.Entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
