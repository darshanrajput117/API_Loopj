package com.biz.apidemo.utility;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.multidex.MultiDexApplication;

import com.biz.apidemo.Model.LoginModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import java.lang.reflect.Type;

public class Globals extends MultiDexApplication {

    @SuppressLint("StaticFieldLeak")
    static Context context;
    SharedPreferences sp;
    SharedPreferences.Editor editor;

    public static String userDatatoJson(LoginModel userDetails) {
        if (userDetails == null) {
            return null;
        }
        Type mapType = new TypeToken<LoginModel>() {
        }.getType();
        Gson gson = new Gson();
        return gson.toJson(userDetails, mapType);
    }

    public static LoginModel toUserData(String params) {
        if (params == null)
            return null;

        Type mapType = new TypeToken<LoginModel>() {
        }.getType();
        Gson gson = new Gson();
        return gson.fromJson(params, mapType);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        Logger.addLogAdapter(new AndroidLogAdapter());
    }

    public LoginModel getLoginData() {
        return toUserData(getSharedPref().getString(Constant.USER_MAP, null));
    }

    public void setLoginData(LoginModel userData) {
        getEditor().putString(Constant.USER_MAP, userDatatoJson(userData));
        getEditor().commit();
    }

    public SharedPreferences getSharedPref() {
        return sp = (sp == null) ? getSharedPreferences(Constant.secrets, Context.MODE_PRIVATE) : sp;
    }

    public SharedPreferences.Editor getEditor() {
        return editor = (editor == null) ? getSharedPref().edit() : editor;
    }
}
