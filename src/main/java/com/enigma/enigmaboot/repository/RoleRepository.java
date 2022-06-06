package com.enigma.enigmaboot.repository;

import com.enigma.enigmaboot.constant.ERole;
import com.enigma.enigmaboot.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findRoleByName (ERole name);
}
