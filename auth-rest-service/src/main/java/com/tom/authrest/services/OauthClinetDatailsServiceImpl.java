package com.tom.authrest.services;

import com.tom.authrest.domian.OauthClientDetails;
import com.tom.authrest.repository.OauthClientDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OauthClinetDatailsServiceImpl implements OauthClientDetailsService{

    @Autowired
    private OauthClientDetailsRepository oauthClientDetailsRepository;

    @Override
    public List<?> listAll() {
        return oauthClientDetailsRepository.findAll();
    }

    @Override
    public OauthClientDetails getById(String id) {
        return oauthClientDetailsRepository.findOne(id);
    }


    @Override
    public OauthClientDetails saveOrUpdate(OauthClientDetails domainObject) {
        return oauthClientDetailsRepository.save(domainObject);
    }

    @Override
    public void delete(String id) {
        oauthClientDetailsRepository.delete(id);
    }


}
