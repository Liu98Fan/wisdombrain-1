package com.wisdombrain.system.util;

import net.sf.json.JSONArray;

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
    public static JSONArray msg(String info,String date,Integer flag){
        JSONArray jsonArray = new JSONArray();
        Map<String,Object> map = new HashMap<String,Object>();
        if(flag == 0){
            map.put(Info.SUCCESS,info);
        }else{
            map.put(Info.FAIL,info);
        }
        map.put(Info.DATE,date);
        jsonArray.add(map);
        return jsonArray;
    }
}
