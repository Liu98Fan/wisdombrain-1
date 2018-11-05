package com.wisdombrain.system.serviceImpl;
import com.wisdombrain.system.entities.Vuser;
import com.wisdombrain.system.service.baseService;
import net.sf.json.JSONObject;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class baseServiceImpl implements baseService {


    @Override
    public Vuser getCurrentVuser() {
        Subject subject = SecurityUtils.getSubject();
        Vuser principal = (Vuser)subject.getPrincipal();
        return principal;
    }

    @Override
    @CachePut(cacheNames = "userTime")
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

    @Override
    public JSONObject uploadFile(MultipartFile file, String path, HttpSession session, HttpServletRequest request) {
        // 生成一个文件名16位数字+字母
        String fileRandomNameId = getUUID();
        String fileRandomName = getUUID();
        String realName = file.getOriginalFilename();
        String suffixName = realName.substring(realName.lastIndexOf("."));
        fileRandomName += suffixName;
        path = "/opt/dinggc/";
        File tempFile = new File(path, fileRandomName);
        if (!tempFile.getParentFile().exists()) {
            tempFile.getParentFile().mkdirs();
        }
        if (tempFile.exists()) {
            tempFile.delete();
        }
        try{
            tempFile.createNewFile();
            file.transferTo(tempFile);
        }catch (IOException e){
            e.printStackTrace();
        }
        session = request.getSession();
        session.setAttribute("picname", tempFile);
        session.setAttribute("multpart", file);
        session.setAttribute("path", path);
        session.setAttribute("fileName", fileRandomName);
        // 封装文件的信息
        JSONObject fileInfor = new JSONObject();
        fileInfor.put("fileId", fileRandomNameId);
        fileInfor.put("fileName", fileRandomName);
        fileInfor.put("fileType", suffixName);
        fileInfor.put("filePath", tempFile.getAbsolutePath());
        return fileInfor;
    }

}
