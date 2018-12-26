package com.wisdombrain.system.service;

import com.wisdombrain.system.entities.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserService {

    //判断当前学习注册的学号在学生信息表中是否存在
    public User getStudentNumberToCheck(@Param("number") String number,@Param("role") String role);
    //判断当前注册的学生的账户是否已经注册
    public User getStudentInfoToCheck(@Param("role") String role, @Param("number") String number);
    //插入学生和老师的信息
    public void insertUser(@Param("number") String number,@Param("passwd") String passwd,@Param("salt") String salt,
                           @Param("date") String date,@Param("del_flag") int del_flag,@Param("role") String role);
    //得到用户注册时的盐
    public User getUser(@Param("number") String number,@Param("role") String role);

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
