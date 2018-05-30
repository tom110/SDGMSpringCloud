package com.tom.auth.domain;

import com.tom.auth.repository.RoleRepository;
import com.tom.auth.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.thymeleaf.util.ListUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class CustomUserDetails extends Users implements UserDetails {

    @Autowired
    private UsersRepository usersRepository;

    public CustomUserDetails(final Users users) {
        super(users);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

//        return getRoles()
//                .stream()
//                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getRole()))
//                .collect(Collectors.toList());
        List<Authority> authorities=new ArrayList<Authority>();
        getRoles().stream().map(role -> role.getAuthorities())
                .forEach(auths -> authorities.addAll(auths));
        return authorities.stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getAuthority()))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return super.getPassword();
    }

    @Override
    public String getUsername() {
        return super.getName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
