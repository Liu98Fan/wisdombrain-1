package com.wisdombrain.system.entities;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;


public class BaseEntity {
    private String id;
    private int del_flag = 0;
    private String date;

    public BaseEntity(String id) {
        this.id = id;
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.date = formatter.format(currentTime);
    }

    public BaseEntity() {
        UUID uuid = UUID.randomUUID();
        this.id = uuid.toString().replace("-", "");
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.date = formatter.format(currentTime);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getDel_flag() {
        return del_flag;
    }

    public void setDel_flag(int del_flag) {
        this.del_flag = del_flag;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
