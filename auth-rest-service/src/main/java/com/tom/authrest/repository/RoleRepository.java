package com.tom.authrest.repository;

import com.tom.authrest.domian.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
}
