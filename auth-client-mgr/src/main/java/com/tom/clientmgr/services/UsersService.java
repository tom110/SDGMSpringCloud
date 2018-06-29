package com.tom.clientmgr.services;

import com.tom.clientmgr.domian.Users;
import com.tom.clientmgr.repository.UsersRepository;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

public interface UsersService extends CRUDService<Users>{

    void userBindRoles(Integer userId, String roleIds) throws ParseException;

    Users getUsersByUsername(String username);
}
