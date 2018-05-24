package com.tom.clientmgr.services;

import com.tom.clientmgr.domian.Users;
import com.tom.clientmgr.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service("usersService")
public class UsersServiceImpl implements UsersService{


    private UsersRepository usersRepository;

    @Autowired
    public UsersServiceImpl(UsersRepository usersRepository) {
        this.usersRepository=usersRepository;
    }
    @Override
    public List<?> listAll() {
        return usersRepository.findAll();
    }

    @Override
    public Users getById(Integer id) {
        return usersRepository.findOne(id);
    }

    @Override
    public Users saveOrUpdate(Users domainObject) {
        return usersRepository.save(domainObject);
    }

    @Override
    public void delete(Integer id) {
        usersRepository.delete(id);
    }
}
