package com.tom.authrest.services;

import com.tom.authrest.domian.Users;
import org.json.simple.parser.ParseException;

public interface UsersService extends CRUDService<Users>{

    void userBindRoles(Integer userId, String roleIds) throws ParseException;
}
