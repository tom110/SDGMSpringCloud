package com.tom.authrest.repository;


import com.tom.authrest.domian.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority,Integer>{
}
