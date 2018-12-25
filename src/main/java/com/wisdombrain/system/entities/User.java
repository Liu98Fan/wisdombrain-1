package com.wisdombrain.system.entities;

import org.apache.shiro.util.ByteSource;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * <p>title:User</p>
 * <p>Description:用户信息类与数据库中User_tb对应</p>
 * <p>college:HYIT</p>
 *
 * @author 刘凡
 * @date
 */
public class User extends BaseEntity {
    private String username;
    private String password;
    private String salt;
    private List<Role> roles = new ArrayList<>();
    private String newdate;
    private ByteSource saltByte = ByteSource.Util.bytes("default");

    public User(String id) {
        super(id);
    }

    public User() {
        super();
    }

    public ByteSource getSaltByte() {
        return saltByte;
    }

    public void setSaltByte(ByteSource saltByte) {
        this.saltByte = saltByte;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getNewdate() {
        return newdate;
    }

    public void setNewdate(String newdate) {
        this.newdate = newdate;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public Set<Role> getRoleConvertSet() {
        Set<Role> rset = new HashSet();
        for (Role role : roles) {
            rset.add(role);
        }
        return rset;
    }

    public Set<String> getRoleConvertStringSet() {
        Set<String> rset = new HashSet<>();
        for (Role role : roles) {
            rset.add(role.getRole());
        }
        return rset;
    }

    public Set<String> getPermissionCovertStringSet() {
        Set<String> pset = new HashSet<>();
        for (Role role : roles) {
            for (Permission permission : role.getPermissions()) {
                pset.add(permission.getPermission());
            }
        }
        return pset;
    }

}
