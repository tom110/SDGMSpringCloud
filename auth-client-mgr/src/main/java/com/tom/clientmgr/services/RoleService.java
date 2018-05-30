package com.tom.clientmgr.services;

import com.tom.clientmgr.domian.Role;
import org.json.simple.parser.ParseException;

public interface RoleService extends CRUDService<Role>{

    void roleBindAuthorities(Integer roleId, String authoritiesIds) throws ParseException;
}
