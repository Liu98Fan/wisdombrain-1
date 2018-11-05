package com.wisdombrain.system.service;

import com.wisdombrain.system.entities.Permission;
import com.wisdombrain.system.entities.Vuser;

import java.util.List;

public interface userService {
    //shiro认证
    public Vuser getUserToConfirm(String username);
    //注册验证用户名是否重复
    public Vuser getVuserToCheckRepeat(String username);
    //插入拜访用户
    public void insertVuser(Vuser vuser);

    //获取权限信息
    public List<Permission> getPermissionById(int id);
    //根据uuid获取用户对象
    public Vuser getVuserByUUID(String uuid);
}
