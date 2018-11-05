package com.wisdombrain.system.service;

import com.wisdombrain.system.entities.Vuser;
import net.sf.json.JSONObject;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public interface baseService {
    public Vuser getCurrentVuser();
    public String getCurrentTime();
    public String getUUID();
    public JSONObject uploadFile(MultipartFile file, String path, HttpSession session, HttpServletRequest request);
}

