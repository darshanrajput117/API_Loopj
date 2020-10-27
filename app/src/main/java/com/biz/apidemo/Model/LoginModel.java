package com.biz.apidemo.Model;

import java.io.Serializable;

public class LoginModel implements Serializable {

    @com.google.gson.annotations.SerializedName("data")
    public Data data;
    @com.google.gson.annotations.SerializedName("msg")
    public String msg;
    @com.google.gson.annotations.SerializedName("res")
    public String res;

    public static class Data implements Serializable {
        @com.google.gson.annotations.SerializedName("updatedAt")
        public String updatedAt;
        @com.google.gson.annotations.SerializedName("createdAt")
        public String createdAt;
        @com.google.gson.annotations.SerializedName("password")
        public String password;
        @com.google.gson.annotations.SerializedName("address")
        public String address;
        @com.google.gson.annotations.SerializedName("mobile_no")
        public String mobile_no;
        @com.google.gson.annotations.SerializedName("email_id")
        public String email_id;
        @com.google.gson.annotations.SerializedName("last_name")
        public String last_name;
        @com.google.gson.annotations.SerializedName("first_name")
        public String first_name;
        @com.google.gson.annotations.SerializedName("user_id")
        public int user_id;
    }
}
