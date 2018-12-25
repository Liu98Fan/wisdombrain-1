package com.wisdombrain.system.serviceImpl;


import com.wisdombrain.system.service.BaseService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.condition.RequestMethodsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: liufan
 * @Date: 2018/10/17 18:53
 * @Description:
 */
@Service
public class BaseSeriveImpl implements BaseService {
    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private Logger logger = LoggerFactory.getLogger(getClass());


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
    public JSONObject getAjaxResult(Boolean mark, int errorCode, String errorMessage) {
        JSONObject json = new JSONObject();
        String success = "success";
        String message = null;
        int code = 200;
        if (!mark) {
            success = "fail";
            code = errorCode;
            message = errorMessage;
        }
        json.put("success", success);
        json.put("code", code);
        json.put("message", message);
        return json;
    }

    @Override
    public JSONObject getAjaxResultHasObject(Boolean mark, int errorCode, String errorMessage, Object object) {
        JSONObject json = new JSONObject();
        String success = "success";
        String message = null;
        Object obj = object;
        int code = 200;
        if (!mark) {
            success = "fail";
            code = errorCode;
            message = errorMessage;
        }
        json.put("success", success);
        json.put("code", code);
        json.put("message", message);
        json.put("object", obj);
        return json;

    }

    /**
     * 获取项目的所有mapping 路径
     *
     * @return
     */
    @Override
    public Object getAllUrlMapping() {
        RequestMappingHandlerMapping mapping = webApplicationContext.getBean(RequestMappingHandlerMapping.class);
        // 获取url与类和方法的对应信息
        Map<RequestMappingInfo, HandlerMethod> map = mapping.getHandlerMethods();
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        for (Map.Entry<RequestMappingInfo, HandlerMethod> m : map.entrySet()) {
            Map<String, String> map1 = new HashMap<String, String>();
            RequestMappingInfo info = m.getKey();
            HandlerMethod method = m.getValue();
            PatternsRequestCondition p = info.getPatternsCondition();
            for (String url : p.getPatterns()) {
                map1.put("url", url);
            }
            // 类名
            map1.put("className", method.getMethod().getDeclaringClass().getName());
            // 方法名
            map1.put("method", method.getMethod().getName());
            RequestMethodsRequestCondition methodsCondition = info.getMethodsCondition();
            for (RequestMethod requestMethod : methodsCondition.getMethods()) {
                map1.put("type", requestMethod.toString());
            }
            list.add(map1);
        }
        JSONArray jsonArray = JSONArray.fromObject(list);
        return jsonArray;
    }
}
