package com.tom.authrest.services;

import com.fasterxml.jackson.databind.annotation.JsonAppend;
import com.rabbitmq.client.AMQP;
import com.tom.authrest.domian.Authority;
import com.tom.authrest.domian.Users;
import com.tom.authrest.repository.AuthorityRepository;
import com.tom.authrest.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AuthorityServiceImpl implements AuthorityService {

    @Autowired
    private AuthorityRepository authorityRepository;

    @Autowired
    private UsersRepository usersRepository;
    @Override
    public List<?> listAll() {
        return authorityRepository.findAll();
    }

    @Override
    public Authority getById(Integer id) {
        return authorityRepository.getOne(id);
    }

    @Override
    public Authority saveOrUpdate(Authority domainObject) {
        return authorityRepository.save(domainObject);
    }

    @Override
    public void delete(Integer id) {
        authorityRepository.delete(id);
    }

    @Override
    public List<Authority> getAuthoritiesByUsername(String username) {
        List<Authority> authorities=new ArrayList<Authority>();
        Optional<Users> users= usersRepository.findByName(username);
        users.orElseThrow(() -> new UsernameNotFoundException("Username not found!"));
        users.get().getRoles().stream().forEach(role -> {
            role.getAuthorities().stream().forEach(authority -> {
                authorities.add(authority);
            });
        });
        return authorities;
    }
}
