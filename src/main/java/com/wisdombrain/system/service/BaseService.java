package com.wisdombrain.system.service;

import net.sf.json.JSONObject;
import org.apache.shiro.util.ByteSource;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


public interface BaseService {
    public Object Encrypt(String alogrithem, String password, ByteSource salt);

    public Object ConvertSaltByte(String salt);


    public String getCurrentTime();
    public String getUUID();

}
