package com.wisdombrain.system.serviceImpl;
import com.wisdombrain.system.service.BaseService;
import net.sf.json.JSONObject;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Service
public class BaseServiceImpl implements BaseService {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Override
    public Object Encrypt(String alogrithem, String password, ByteSource salt) {
        Object result = new SimpleHash(alogrithem, password, salt);
        return result;
    }

    @Override
    public Object ConvertSaltByte(String salt) {
        ByteSource saltByte = ByteSource.Util.bytes(salt);
        return saltByte;
    }

    @Override
    public String getCurrentTime() {
        Date date = new Date();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String udate= sdf.format(date);
        return udate;
    }

    @Override
    public String getUUID() {
        UUID uuid=UUID.randomUUID();
        String str = uuid.toString();
        String uuidStr=str.replace("-", "");
        return uuidStr;
    }


}
