package com.wisdombrain.system.service;

import net.sf.json.JSONObject;
import org.apache.shiro.util.ByteSource;


/**
 * @Author: liufan
 * @Date: 2018/10/17 18:51
 * @Description:
 */
public interface BaseService {
    public Object Encrypt(String alogrithem, String password, ByteSource salt);

    public Object ConvertSaltByte(String salt);

    public JSONObject getAjaxResult(Boolean mark, int errorCode, String errorMessage);

    public JSONObject getAjaxResultHasObject(Boolean mark, int errorCode, String errorMessage, Object object);

    public Object getAllUrlMapping();
}
