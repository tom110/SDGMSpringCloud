package com.tom.clientmgr.services;

import com.tom.clientmgr.domian.Users;
import com.tom.clientmgr.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class UsersService {
    @Autowired
    private UsersRepository usersRepository;

    public Collection<Users> getUsers(){
        return usersRepository.findAll();
    }
}
