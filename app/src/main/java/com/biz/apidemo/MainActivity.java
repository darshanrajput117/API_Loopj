package com.biz.apidemo;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.orhanobut.logger.Logger;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

@SuppressLint("NonConstantResourceId")
public class MainActivity extends AppCompatActivity {

    @BindView(R.id.btn_login)
    AppCompatButton btn_Login;
    @BindView(R.id.btn_register)
    AppCompatButton btn_registration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        Logger.d("Main Activity....");
    }

    @OnClick({R.id.btn_login, R.id.btn_register})
    public void OnclickAction(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                Intent i = new Intent(this, Login.class);
                startActivity(i);
                break;
            case R.id.btn_register:
                Intent i1 = new Intent(this, Register.class);
                startActivity(i1);
                break;
        }
    }
}