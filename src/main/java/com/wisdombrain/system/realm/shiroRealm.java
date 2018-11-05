package com.wisdombrain.system.realm;

import com.wisdombrain.system.entities.Permission;
import com.wisdombrain.system.entities.Vuser;
import com.wisdombrain.system.serviceImpl.userServiceImpl;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

public class shiroRealm extends AuthorizingRealm {

    @Autowired
    userServiceImpl userService;

    /**
     * 授权用户权限
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //从principals获取主身份信息
        //将getPrimaryPrincipal方法返回值转为真实身份信息(在上边的doGetAuthecticationInfo认证通过填充到SimpleAuthenticationInfo)
        Vuser vuser = (Vuser) principalCollection.getPrimaryPrincipal();
        //根据信息获取权限信息
        //连接数据库。。。
        //模拟从数据库获取到数据
        List<Permission> permissions = null;
        try {
            permissions = userService.getPermissionById(vuser.getId());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //...
        List<String> permission = new ArrayList<String>();
        for(Permission syspermission:permissions) {
            permission.add(syspermission.getPermission());
        }
        //查询到权限数据，返回
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        //将上边查询到授权信息填充到simpleAuthorizationInfo对象中
        simpleAuthorizationInfo.addStringPermissions(permission);
        return simpleAuthorizationInfo;
    }

    /**
     * 验证用户身份
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String username = token.getUsername();

        Vuser vuser = null;
        try {
            vuser = userService.getUserToConfirm(username);
        }catch (Exception e){
            e.printStackTrace();
        }
        if(vuser==null){
           return null;
        }
        String PIN = vuser.getPassword();
        return new SimpleAuthenticationInfo(vuser,PIN,getName());
    }
}
