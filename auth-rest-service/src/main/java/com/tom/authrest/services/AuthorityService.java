package com.tom.authrest.services;

import com.tom.authrest.domian.Authority;

import java.util.List;

public interface AuthorityService extends CRUDService<Authority>{
    List<Authority> getAuthoritiesByUsername(String username);
}
