package com.tom.authrest.domian;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private Integer id;
    @Column(name = "email")
    private String email;
    @Column(name = "name",unique = true,nullable = false)
    private String name;
    @Column(name = "password")
    private String password;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "active")
    private Integer active;

    @ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinTable(name = "user_role", joinColumns =
    @JoinColumn(name = "user_id"), inverseJoinColumns =
    @JoinColumn(name = "role_id"))
    private List<Role> roles;



    public Users() {
    }

    public Users(Integer id,String name,String lastName,String password,Integer active,String email){
        this.id=id;
        this.active = active;
        this.email = email;
        this.lastName =lastName;
        this.name = name;
        this.password = password;
    }

    public Users(String name,String lastName,String password,Integer active,String email){
        this.active = active;
        this.email = email;
        this.lastName =lastName;
        this.name = name;
        this.password = password;
    }

    public Users(Users users) {

        this.active = users.active;
        this.email = users.email;
        this.id = users.id;
        this.lastName = users.lastName;
        this.name = users.name;
        this.password = users.password;
        this.roles = users.roles;

    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getActive() {
        return active;
    }

    public void setActive(Integer active) {
        this.active = active;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}
