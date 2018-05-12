package com.tom.auth.bootstrap;

import com.tom.auth.domain.Role;
import com.tom.auth.domain.Users;
import com.tom.auth.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.thymeleaf.util.ListUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class FillUsersBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private UsersRepository usersRepository;
    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
//        Users users=new Users();
//        users.setActive(1);
//        users.setEmail("254449149@qq.com");
//        users.setName("tom");
//        users.setPassword("tom");
//        users.setLastName("tom");
//        Role role=new Role();
//        role.setRole("admin");
//        Set<Role> roles=new HashSet<Role>();
//        roles.add(role);
//        users.setRoles(roles);
//        usersRepository.save(users);
    }
}
