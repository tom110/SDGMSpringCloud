package com.tom.clientmgr.services;

import com.tom.clientmgr.domian.Role;
import com.tom.clientmgr.domian.Users;
import com.tom.clientmgr.repository.RoleRepository;
import com.tom.clientmgr.repository.UsersRepository;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service("usersService")
public class UsersServiceImpl implements UsersService{

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private RoleRepository roleRepository;

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

    @Override
    @Transactional
    public void userBindRoles(Integer userId, String roleIds) throws ParseException {
        JSONArray jsonArray = (JSONArray) (new JSONParser().parse(roleIds));
        Users users= getById(userId);
        users.getRoles().clear();
        jsonArray.stream().map(roleId->{
            return roleRepository.getOne(Integer.parseInt(roleId.toString()));
        }).forEach(role->{
            if(!users.getRoles().contains((Role) role))
                users.getRoles().add((Role) role);
        });
    }

    @Override
    public Users getUsersByUsername(String username) {
        Optional<Users> usersOptional = usersRepository.findByName(username);

        usersOptional
                .orElseThrow(() -> new UsernameNotFoundException("Username not found!"));
        return usersOptional
                .map(Users::new)
                .get();
    }
}
