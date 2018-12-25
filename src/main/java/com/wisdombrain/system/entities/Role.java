package com.wisdombrain.system.entities;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Role extends BaseEntity {
    private String describe;
    private String role;
    private List<Permission> permissions = new ArrayList<>();
    private String newdate;

    public Role() {
        super();
    }

    public String getNewdate() {
        return newdate;
    }

    public void setNewdate(String newdate) {
        this.newdate = newdate;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }

    public Set<Permission> getPermissionConvertSet() {
        Set<Permission> pset = new HashSet();
        for (Permission p : permissions) {
            pset.add(p);
        }
        return pset;
    }


}
