package com.tom.auth;
import com.tom.auth.domain.Role;
import com.tom.auth.domain.Users;
import com.tom.auth.repository.RoleRepository;
import com.tom.auth.repository.UsersRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FillUser {

    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Test
    public void test(){
        Users users=new Users();
        users.setActive(1);
        users.setEmail("254449149@qq.com");
        users.setName("tom");
        users.setPassword("tom");
        users.setLastName("tom");
        Role role=new Role();
        role.setRole("admin");
        role.setParentId(0);
        role= roleRepository.save(role);
        List<Role> roles=new ArrayList<>();
        roles.add(role);
        users.setRoles(roles);
        usersRepository.save(users);
    }
}
