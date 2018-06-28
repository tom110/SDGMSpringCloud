package com.tom.authrest.services;

import com.tom.authrest.domian.Role;
import org.json.simple.parser.ParseException;

public interface RoleService extends CRUDService<Role>{

    void roleBindAuthorities(Integer roleId, String authoritiesIds) throws ParseException;
}
