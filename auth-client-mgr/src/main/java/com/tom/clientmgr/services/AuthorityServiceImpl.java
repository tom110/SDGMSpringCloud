package com.tom.clientmgr.services;

import com.tom.clientmgr.domian.Authority;
import com.tom.clientmgr.repository.AuthorityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jca.cci.RecordTypeNotSupportedException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AuthorityServiceImpl implements AuthorityService {

    @Autowired
    private AuthorityRepository authorityRepository;
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
}
