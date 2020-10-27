package com.biz.apidemo;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import com.biz.apidemo.utility.Globals;

import butterknife.BindView;
import butterknife.ButterKnife;

@SuppressLint("NonConstantResourceId")
public class Splash extends AppCompatActivity {

    Globals globals;
    Animation animinter;
    @BindView(R.id.iv_logo)
    AppCompatImageView iv_logo;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        globals = (Globals) getApplicationContext();

        animinter= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.logo);
        iv_logo.setVisibility(View.VISIBLE);
        iv_logo.startAnimation(animinter);

        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_layout, findViewById(R.id.container));
        AppCompatTextView textView = layout.findViewById(R.id.text);

        textView.setText("Welcome");
        Toast toast = new Toast(getApplicationContext());
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.BOTTOM, 0, 200);
        toast.setView(layout);
        toast.show();
        new Handler().postDelayed(() -> {
            if (globals.getLoginData() != null) {
                Intent i = new Intent(Splash.this, Userlist.class);
                startActivity(i);
                finish();
            } else {
                Intent i = new Intent(Splash.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        }, 3000);
    }
}