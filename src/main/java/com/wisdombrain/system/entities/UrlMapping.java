package com.wisdombrain.system.entities;

/**
 * @author:LIUFAN
 * @date:2018/11/25
 */
public class UrlMapping extends BaseEntity {
    /**
     * url
     */
    private String url;
    /**
     * 类名称
     */
    private String className;
    /**
     * 方法名称
     */
    private String methodName;
    /**
     * url描述
     */
    private String describe;
    /**
     * 是否作为权限分配，默认作为权限分配
     */
    private int isAuthority = 0;
    /**
     * 父路径 比如/center/entrance中的center
     */
    private String parentMapping;
    /**
     * 子路径 比如/center/entrance中的entrance
     */
    private String sonMapping;

    public UrlMapping() {
    }

    public String getUrl() {
        return url;
    }

    public String getParentMapping() {
        return parentMapping;
    }

    public void setParentMapping(String parentMapping) {
        this.parentMapping = parentMapping;
    }

    public String getSonMapping() {
        return sonMapping;
    }

    public void setSonMapping(String sonMapping) {
        this.sonMapping = sonMapping;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public int getIsAuthority() {
        return isAuthority;
    }

    public void setIsAuthority(int isAuthority) {
        this.isAuthority = isAuthority;
    }
}
