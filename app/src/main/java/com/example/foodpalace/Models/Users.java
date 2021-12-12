package com.example.foodpalace.Models;

import android.text.Editable;

public class Users {
String username,mail,password,profilepic,userid;

    public Users(String username, String mail, String password, String profilepic, String userid) {
        this.username = username;
        this.mail = mail;
        this.password = password;
        this.profilepic = profilepic;
        this.userid = userid;
    }
    public Users(){}
    public Users(String username, String mail, String password) {
        this.username = username;
        this.mail = mail;
        this.password = password;
    }

    public Users(String username, String mail) {
        this.username = username;
        this.mail = mail;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getMail() {
        return mail;
    }

    public String getProfilepic() {
        return profilepic;
    }

    public String getUserid() {
        return userid;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setProfilepic(String profilepic) {
        this.profilepic = profilepic;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }



}
