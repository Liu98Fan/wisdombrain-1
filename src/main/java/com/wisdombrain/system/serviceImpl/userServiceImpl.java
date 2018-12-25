package com.wisdombrain.system.serviceImpl;


import com.wisdombrain.system.entities.*;
import com.wisdombrain.system.mapper.UserDao;
import com.wisdombrain.system.service.UserService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Override
    public User getUserByUserName(String username) {
        try {
            User user = userDao.getUserByUserName(username);
            //进行权限装配
            List<Role> roleList = userDao.getRoleByUserId(user.getId());
            for (Role role : roleList) {
                List<Permission> permissionList = userDao.getPerMissionByRoleId(role.getId());
                role.setPermissions(permissionList);
            }
            user.setRoles(roleList);
            return user;
        } catch (NullPointerException e) {
            return null;
        }
    }

    @Override
    public boolean checkUsername(String username) {
        return getUserByUserName(username) == null ? true : false;
    }

    @Override
    public boolean register(User user) {
        String backValue = userDao.register(user);
        if (backValue.equals("succ")) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<Role> getRoleById(String id) {
        return userDao.getRoleByUserId(id);
    }

    @Override
    public int getRoleCount() {
        return userDao.getRoleCount();
    }

    @Override
    public List<Role> getAllRole() {
        return userDao.getAllRole();
    }


    @Override
    public List<Role> getRoleForPage(Page page) {
        int start = page.getCurrentResult();
        int end = start + page.getLimit();
        List<Role> roleList = userDao.getRoleForPage(start, end);
        return roleList;
    }

    @Override
    public List<User> getAllUser() {
        return userDao.getAllUser();
    }

    @Override
    public int getUserCount() {
        return userDao.getUserCount();
    }

    @Override
    public List<User> getUserForPage(Page page) {
        int start = page.getCurrentResult();
        int end = start + page.getLimit();
        List<User> userList = userDao.getUserForPage(start, end);
        return userList;
    }

    @Override
    public List<User> getUserForPage(Page page, String search) {
        int start = page.getCurrentResult();
        int end = start + page.getLimit();
//        Map<Object,Object> paramMap = new HashMap<>();
//        paramMap.put("start",start);
//        paramMap.put("end",end);
//        paramMap.put("search",search);
        List<User> userList = userDao.getUserForPageHasSearch(start, end, search);
        return userList;
    }

    @Override
    public boolean deleteUser(String id) {
        String mark = userDao.deleteUser(id);
        if (mark.equals("succ")) {
            return true;
        } else {
            return false;
        }

    }

    @Override
    public boolean bindRole(String userid, String roleid) {
        String s = userDao.bindRole(userid, roleid);
        if (s.equals("succ")) {
            return true;
        } else {
            return false;
        }

    }

    @Override
    public boolean reliveBindRole(String userid, String roleid) {
        String s = userDao.reliveBindRole(userid, roleid);
        if (s.equals("succ")) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean savePermission(Permission permission) {
        return userDao.savePermission(permission).equals("succ") ? true : false;
    }

    @Override
    public boolean saveParentPermission(ParentPermission parentPermission) {
        userDao.saveParentPermission(parentPermission);
        return true;
    }

    @Override
    public Object getPermissionForTree() {
        List<Permission> permissionList = userDao.getPermissionForTree();
        //进行删选组装
        JSONArray jsonArray = new JSONArray();
        while (permissionList.size() > 0) {
            JSONObject json = new JSONObject();
            Permission parent = permissionList.get(0);
            json.put("parent", parent);
            List<Permission> permissions = new ArrayList<>();
            List<Integer> indexs = new ArrayList<>();
            for (int i = 0; i < permissionList.size(); i++) {
                if (permissionList.get(i).getParent().equals(parent.getParent())) {
                    indexs.add(i);
                }
            }
            //剔除
            for (int j = indexs.size() - 1; j >= 0; j--) {
                permissions.add(permissionList.get(indexs.get(j)));
                //这里一定要将Integer转int，否则使用的是remove(object)的方法
                permissionList.remove((int) indexs.get(j));
            }
            //加入json
            json.put("son", permissions);
            //加入json数组
            jsonArray.add(json);
        }
        return jsonArray;
    }

    /**
     * 保存role的方法
     * 注意这是通用方法
     *
     * @param role
     * @param permissionList
     * @return
     */
    @Override
    public Object saveRole(Role role, String[] permissionList) {
        /**
         * 首先我们先只考虑修改role（因为最复杂）
         * 如果说我们在这里进行迭代permissionList来存储数据是否会有不必要的资源开销？
         * 目前想的方案是将数据库里面的role角色数据查出来，来进行比对，如果role为空，那么直接进行插入，如果role不为空，取出permission数据
         * 后期这些还可以做缓存，比较方便
         * 嗯那就先这么些，大不了以后变得牛逼了再来重构
         */

        String mark1 = "";
        String mark2 = "";
        if (role.getId().length() < 32) {
            //是新建的角色，插入数据没得跑了
            //先创建角色
            //?问题1：如果新建的角色和已有角色重名呢？应该怎么办？
            //这里先判断是否重名？
            int i = userDao.checkRole(role.getRole());
            if (i > 0) {
                return "已存在该角色名";
            }
            Role temp = new Role();
            role.setId(temp.getId());
            mark1 = userDao.saveRole(role);
            for (String permission : permissionList) {
                //循环插入
                mark2 = userDao.insertPermissionToRole(role.getId(), permission);
                if (!mark2.equals("succ")) {
                    break;
                }
            }
            if (mark1.equals(mark2)) {
                return mark1.equals("succ") ? mark1 : "error";
            } else {
                return "error";
            }

        } else {
            //是旧角色修改没得跑了
            //那么到底是修改还是删除？
            if (role.getDel_flag() == 0) {
                //修改
                Role targetRole = userDao.getRoleById(role.getId());
                if (targetRole != null) {
                    List<Permission> permissions = userDao.getPerMissionByRoleId(role.getId());
                    List<String> deleteList = new ArrayList<>();
                    List<String> insertList = new ArrayList<>();
                    for (Permission p : permissions) {
                        String pname = p.getId();
                        //首先要找出添加的权限，然后找出被删除的权限
                        if (Arrays.asList(permissionList).contains(pname)) {
                            //包含了，那就怎么办呢？说明原数据已经有了，不是我们要修改的目标数据，那就跳过呗
                        } else {
                            //说明不包含了，那就是要删除的数据  permissionList没有permissions里面的数据
                            deleteList.add(p.getId());
                        }

                    }

                    //那添加的权限怎么找？就是permissionList而permissions没有的，那我们就来遍历permissionList
                    for (String p : permissionList) {
                        boolean mark = true;
                        for (Permission permission : permissions) {
                            if (permission.getId().equals(p)) {
                                mark = false;
                            }
                        }
                        if (mark) {
                            //没有发现permissions中存在的却在permissionList中存在，则要添加
                            insertList.add(p);
                        }
                    }
                    //现在删除，添加的都得到了，那就开始操作
                    //删除操作
                    for (String pid : deleteList) {
                        userDao.deleteRolePermission(role.getId(), pid);
                        mark2 = "succ";
                    }
                    //添加操作
                    for (String pid : insertList) {
                        mark2 = userDao.insertPermissionToRole(role.getId(), pid);
                        if (!mark2.equals("succ")) {
                            break;
                        }
                    }
                    return mark2;

                } else {
                    //code编码设置
                    return "没有找到被修改的源数据";
                }
            } else {
                //删除
                role.setDel_flag(1);
                mark1 = userDao.saveRole(role);
                return mark1;
            }
        }
    }

    @Override
    public String deleteRole(Role role) {
        role.setNewdate(role.getDate());
        return userDao.saveRole(role);
    }

    @Override
    public List<Permission> getPermissionByRoleId(String roleid) {
        return userDao.getPerMissionByRoleId(roleid);
    }


}
