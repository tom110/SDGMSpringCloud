package com.tom.clientmgr.repository;

import com.tom.clientmgr.domian.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.NamedNativeQuery;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
}
