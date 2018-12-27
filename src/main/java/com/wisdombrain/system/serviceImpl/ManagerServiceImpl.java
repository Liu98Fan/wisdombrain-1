package com.wisdombrain.system.serviceImpl;

import com.wisdombrain.system.entities.*;
import com.wisdombrain.system.mapper.ManagerDao;
import com.wisdombrain.system.service.BaseService;
import com.wisdombrain.system.service.ManagerService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author:LIUFAN
 * @date:2018/11/22
 */
@Service
public class ManagerServiceImpl implements ManagerService {
    @Autowired
    private ManagerDao managerDao;
    @Autowired
    private BaseService baseService;

    @Override
    public List<User> getAllUser() {
        return managerDao.getAllUser();
    }

    @Override
    public int getUserCount() {
        return managerDao.getUserCount();
    }

    @Override
    public List<User> getUserForPage(Page page) {
        int start = page.getCurrentResult();
        int end = start + page.getLimit();
        List<User> userList = managerDao.getUserForPage(start, end);
        return userList;
    }

    @Override
    public boolean deleteUser(String id) {
        String mark = managerDao.deleteUser(id);
        if (mark.equals("succ")) {
            return true;
        } else {
            return false;
        }

    }


    /**
     * 获取urlMapping的总数
     *
     * @return
     */
    @Override
    public int getAuthorityCount() {
        return managerDao.getAuthorityCount();
    }

    /**
     * 获取urlMapping 分页数据
     *
     * @param page
     * @return
     */
    @Override
    public List<UrlMapping> getAuthorityForTable(Page page) {
        int start = page.getStart();
        int end = page.getLimit();
        List<UrlMapping> urlMapping = managerDao.getAuthorityForTable(start, end);
        return urlMapping;
    }

    @Override
    public List<ParentPermission> getParentAuthorityForTable(Page page) {
        int start = page.getStart();
        int end = page.getLimit();
        List<ParentPermission> parentPermission = managerDao.getParentAuthorityForTable(start, end);
        return parentPermission;
    }

    @Override
    public List<Permission> getSonAuthorityForTable(String parent) {
        return managerDao.getSonAuthorityForTable(parent);
    }

    @Override
    public int getParentAuthorityCount() {
        return managerDao.getParentAuthorityCount();
    }

//    @Override
//    public boolean saveParentUrl(UrlMapping parentUrl) {
//        return managerDao.saveParentUrl(parentUrl).equals("succ") ? true : false;
//    }
}
