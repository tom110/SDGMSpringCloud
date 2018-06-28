package com.tom.authrest.domian;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "role_id")
    private Integer roleId;
    @Column(name = "role",unique = true,nullable = false)
    private String role;
    @Column(name = "rolename")
    private String roleName;

    @Column(name="parentId",nullable = false)
    private Integer parentId;

    @ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinTable(name = "user_role", joinColumns =
    @JoinColumn(name = "role_id"), inverseJoinColumns =
    @JoinColumn(name = "user_id"))
    private List<Users> users;

    @ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinTable(name = "role_authority", joinColumns =
    @JoinColumn(name = "role_id"), inverseJoinColumns =
    @JoinColumn(name = "authority_id"))
    private List<Authority> authorities;

    public Role() {
    }

    public Role(String role, String roleName, Integer parentId) {
        this.role = role;
        this.roleName = roleName;
        this.parentId = parentId;
    }

    public Role(Integer roleId, String role, String roleName, Integer parentId) {
        this.roleId=roleId;
        this.role = role;
        this.roleName = roleName;
        this.parentId = parentId;
    }



    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public List<Users> getUsers() {
        return users;
    }

    public void setUsers(List<Users> users) {
        this.users = users;
    }

    public List<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<Authority> authorities) {
        this.authorities = authorities;
    }
}
