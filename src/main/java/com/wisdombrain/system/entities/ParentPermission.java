package com.wisdombrain.system.entities;

import java.util.UUID;

/**
 * 权限父类，用来标识一个模块的权限
 *
 * @author:LIUFAN
 * @date:2018/12/3
 */
public class ParentPermission extends BaseEntity {
    /**
     * 父节点名称
     */
    private String parent;
    /**
     * 父节点描述
     */
    private String parentDescribe;
    /**
     * 父节点id
     */
    private String parentId;

    public ParentPermission() {
        UUID uuid = UUID.randomUUID();
        this.parentId = uuid.toString().replace("-", "");
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getParentDescribe() {
        return parentDescribe;
    }

    public void setParentDescribe(String parentDescribe) {
        this.parentDescribe = parentDescribe;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }
}
