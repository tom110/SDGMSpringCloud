package com.tom.clientmgr.services;

import com.tom.clientmgr.domian.Authority;
import com.tom.clientmgr.domian.Role;
import com.tom.clientmgr.repository.AuthorityRepository;
import com.tom.clientmgr.repository.RoleRepository;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.beans.Transient;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private AuthorityRepository authorityRepository;
    @Override
    public List<?> listAll() {
        return roleRepository.findAll();
    }

    @Override
    public Role getById(Integer id) {
        return roleRepository.findOne(id);
    }

    @Override
    public Role saveOrUpdate(Role domainObject) {
        return roleRepository.save(domainObject);
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        roleRepository.delete(id);
    }

    @Override
    @Transactional
    public void roleBindAuthorities(Integer roleId, String authoritiesIds) throws ParseException {
        JSONArray jsonArray = (JSONArray) (new JSONParser().parse(authoritiesIds));
        Role role= getById(roleId);
        role.getAuthorities().clear();
        jsonArray.stream().map(authorityId->{
            return authorityRepository.getOne(Integer.parseInt(authorityId.toString()));
        }).forEach(authority->{
            if(!role.getAuthorities().contains((Authority) authority))
                role.getAuthorities().add((Authority) authority);
        });
    }
}
