package com.tom.auth;
import com.tom.auth.domain.Role;
import com.tom.auth.domain.Users;
import com.tom.auth.repository.UsersRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FillUser {

    @Autowired
    private UsersRepository usersRepository;

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
        Set<Role> roles=new HashSet<Role>();
        roles.add(role);
        users.setRoles(roles);
        usersRepository.save(users);
    }
}
