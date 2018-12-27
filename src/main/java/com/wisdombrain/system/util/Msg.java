package com.wisdombrain.system.util;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Msg {
    /**
     * 封装返回的json信息
     * @param info
     * @param date
     * @param flag
     * @return
     */
    public static JSONObject msg(String info, String date, Integer flag){
        JSONObject json = new JSONObject();
        if(flag == 0){
            json.put(Info.SUCCESS,info);
        }else{
            json.put(Info.FAIL,info);
        }
        json.put(Info.DATE,date);
        return json;
    }
    public static JSONObject msg(String info, String date, Integer flag,Object object){
        JSONObject json = new JSONObject();
        if(flag == 0){
            json.put(Info.SUCCESS,info);
        }else{
            json.put(Info.FAIL,info);
        }
        json.put(Info.DATE,date);
        json.put("object",object);
        return json;
    }
}
