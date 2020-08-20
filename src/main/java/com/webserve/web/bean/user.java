package com.webserve.web.bean;

import java.io.Serializable;

public class user implements Serializable {
    private Integer userid;
    private String username;
    private String password;
    private String equipId;
    private String salt;


    public String getSalt() {
        return "10000a2";
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
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

    public String getEquipId() {
        return equipId;
    }

    public void setEquipId(String equipId) {
        this.equipId = equipId;
    }
}
