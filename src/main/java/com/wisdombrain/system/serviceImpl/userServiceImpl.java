package com.wisdombrain.system.serviceImpl;

import com.wisdombrain.system.entities.Permission;
import com.wisdombrain.system.entities.Vuser;
import com.wisdombrain.system.mapper.userMapper;
import com.wisdombrain.system.service.userService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class userServiceImpl implements userService {

    @Autowired
    userMapper userMapper;


    @Override
    public Vuser getUserToConfirm(String username) {
        return userMapper.getUserToConfirm(username);
    }

    @Override
    public Vuser getVuserToCheckRepeat(String username){
        return userMapper.getVuserToCheckRepeat(username);
    }

    @Override
    public void insertVuser(Vuser vuser) {
        userMapper.insertVuser(vuser);
    }

    @Override
    public List<Permission> getPermissionById(int id) {
        return userMapper.getPermissionById(id);
    }

    @Override
    public Vuser getVuserByUUID(String uuid) {
        return userMapper.getVuserByUUID(uuid);
    }


}
