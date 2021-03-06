package com.tom.clientmgr.domian;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="authority")
public class Authority {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name="authority")
    private String authority;
    @Column(name="authorityname")
    private String authorityName;
    @Column(name="parentId")
    private Integer parentId;

    @ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinTable(name = "role_authority", joinColumns =
    @JoinColumn(name = "authority_id"), inverseJoinColumns =
    @JoinColumn(name = "role_id"))
    private List<Role> roles;

    public Authority() {
    }

    public Authority(String authority, String authorityName, Integer parentId) {
        this.authority = authority;
        this.authorityName = authorityName;
        this.parentId = parentId;
    }

    public Authority(Integer id,String authority, String authorityName, Integer parentId) {
        this.id=id;
        this.authority = authority;
        this.authorityName = authorityName;
        this.parentId = parentId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public String getAuthorityName() {
        return authorityName;
    }

    public void setAuthorityName(String authorityName) {
        this.authorityName = authorityName;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}
