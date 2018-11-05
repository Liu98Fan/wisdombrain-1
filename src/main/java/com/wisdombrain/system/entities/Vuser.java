package com.wisdombrain.system.entities;

import java.io.Serializable;

public class Vuser{
    private int id;
    private String username;
    private String password;
    private String email;
    public String uuid;
    public int emailconfirm;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public int getEmailconfirm() {
        return emailconfirm;
    }

    public void setEmailconfirm(int emailconfirm) {
        this.emailconfirm = emailconfirm;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
}
