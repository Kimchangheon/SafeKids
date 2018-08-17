package com.example.changheonkim.momfirebaseauth;

import java.util.HashMap;
import java.util.Map;

public class Info_User_Parent {
    String Id;
    String Password;
    String name_parent;
    String name_child;
    String imageUrl;
    String phone_number;
    String school_code;
    String absent;

    public int starCount = 0;
    public Map<String, Boolean> stars = new HashMap<>();


    public String getAbsent() {
        return absent;
    }

    public void setAbsent(String absent) {
        this.absent = absent;
    }
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Info_User_Parent(){

    }
    public Info_User_Parent(String name_parent, String name_child){
        this.name_parent = name_parent;
        this.name_child = name_child;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getName_parent() {
        return name_parent;
    }

    public void setName_parent(String name_parent) {
        this.name_parent = name_parent;
    }

    public String getName_child() {
        return name_child;
    }

    public void setName_child(String name_child) {
        this.name_child = name_child;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getSchool_code() {
        return school_code;
    }

    public void setSchool_code(String school_code) {
        this.school_code = school_code;
    }
}
