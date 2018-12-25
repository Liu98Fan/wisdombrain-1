package com.wisdombrain.system.service;


import com.wisdombrain.system.entities.*;

import java.util.List;

public interface UserService {
    public User getUserByUserName(String username);

    public boolean checkUsername(String username);

    public boolean register(User user);

    public List<Role> getRoleById(String id);

    public int getRoleCount();

    public List<Role> getAllRole();

    public List<Role> getRoleForPage(Page page);

    public List<User> getAllUser();

    public int getUserCount();

    public List<User> getUserForPage(Page page);

    public List<User> getUserForPage(Page page, String search);

    public boolean deleteUser(String id);

    public boolean bindRole(String userid, String roleid);

    public boolean reliveBindRole(String userid, String roleid);

    public boolean savePermission(Permission permission);

    public boolean saveParentPermission(ParentPermission parentPermission);

    public Object getPermissionForTree();

    public Object saveRole(Role role, String[] permissionList);

    public String deleteRole(Role role);

    public List<Permission> getPermissionByRoleId(String roleid);
}
