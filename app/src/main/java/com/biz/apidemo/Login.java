package com.biz.apidemo;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;

import com.biz.apidemo.Model.LoginModel;
import com.biz.apidemo.utility.Globals;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.orhanobut.logger.Logger;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

@SuppressLint("NonConstantResourceId")
public class Login extends AppCompatActivity {

    Globals globals;
    LoginModel loginModel;
    StringEntity entity = null;

    @BindView(R.id.et_uname)
    AppCompatEditText et_uname;
    @BindView(R.id.et_password)
    AppCompatEditText et_password;
    @BindView(R.id.btn_login)
    AppCompatButton login;
    @BindView(R.id.action_register)
    AppCompatTextView register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);
        globals = (Globals) getApplicationContext();
    }

    @OnClick({R.id.btn_login, R.id.action_register})
    public void OnClickEvent(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                if (LoginValidation()) {
                    AsyncHttpClient client = new AsyncHttpClient();
                    JSONObject params = new JSONObject();
                    try {
                        params.put("username", et_uname.getText().toString().trim());
                        params.put("password", et_password.getText().toString().trim());
                        entity = new StringEntity(params.toString());
                    } catch (JSONException | UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }

                    client.post(this, getString(R.string.base_url) + getString(R.string.validateLogin), entity,
                            "application/json", new JsonHttpResponseHandler() {
                                @SuppressLint("SetTextI18n")
                                @Override
                                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                                    super.onSuccess(statusCode, headers, response);
                                    Logger.json(response.toString());
                                    loginModel = new Gson().fromJson(response.toString(), LoginModel.class);

                                    if(loginModel.data!=null){
                                        globals.setLoginData(loginModel);
                                        LayoutInflater inflater = getLayoutInflater();
                                        View layout = inflater.inflate(R.layout.toast_layout, findViewById(R.id.container));
                                        AppCompatTextView textView = layout.findViewById(R.id.text);
                                        textView.setText("Successfully Login...");
                                        Toast toast = new Toast(getApplicationContext());
                                        toast.setDuration(Toast.LENGTH_LONG);
                                        toast.setGravity(Gravity.BOTTOM, 0, 100);
                                        toast.setView(layout);
                                        toast.show();
                                        Intent i = new Intent(Login.this, Userlist.class);
                                        startActivity(i);
                                        finishAffinity();
                                    }else {
                                        Toast.makeText(Login.this, loginModel.msg, Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                                    super.onFailure(statusCode, headers, throwable, errorResponse);
                                    Logger.e("Login error", "" + errorResponse.toString());
                                    Logger.e("String Error", "status code " + statusCode);
                                }
                            });
                }
                break;
            case R.id.action_register:
                Intent i = new Intent(Login.this, Register.class);
                startActivity(i);
                break;
        }
    }

    @SuppressLint("SetTextI18n")
    private boolean LoginValidation() {
        if (!new FormValidation().checkEmptyET(et_uname)) {
            if (!new FormValidation().checkEmptyET(et_password)) {
                if (new FormValidation().checkpassword(et_password)) {

                    //Toast.makeText(getApplicationContext(), "Successfully Login...", Toast.LENGTH_SHORT).show();
                    return true;
                } else {
                    //Toast.makeText(getApplicationContext(), "Password must be 6 characters,1 Uppercase,1 Lowercase,1 Number.", Toast.LENGTH_SHORT).show();
                    et_password.requestFocus();
                    et_password.setError("Password must be 6 characters,1 Uppercase,1 Lowercase,1 Number.");
                    return false;
                }
            } else {
                //Toast.makeText(getApplicationContext(), "Password is empty!!", Toast.LENGTH_SHORT).show();
                et_password.requestFocus();
                et_password.setError("Password is empty!!");
                return false;
            }
        } else {
            //Toast.makeText(getApplicationContext(), "EmailId is emplty!!!", Toast.LENGTH_SHORT).show();
            et_uname.requestFocus();
            et_uname.setError("Username is empty!!!");
            return false;
        }
    }
}