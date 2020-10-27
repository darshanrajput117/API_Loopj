package com.biz.apidemo;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
public class Userlist extends AppCompatActivity {

    Globals globals;

    @BindView(R.id.toolbar_title)
    Toolbar toolbar;
    @BindView(R.id.layout_recylcerview)
    RecyclerView recyclerView;
    @BindView(R.id.ib_logout)
    AppCompatImageButton ib_logout;
    StringEntity stringEntity;
    UserAdapter useradapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userlist);

        globals = (Globals) getApplicationContext();
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        setLayoutManager();
        setData();
    }

    @OnClick(R.id.ib_logout)
    public void Onclick(View view) {
        setLogout();
    }

    private void setLogout() {
        new AlertDialog.Builder(Userlist.this).setTitle("Logout").setMessage("Are Sur Want To Logout?")
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss()).setPositiveButton("Logout", (dialog, which) -> {
            globals.setLoginData(null);
            Intent i = new Intent(Userlist.this, MainActivity.class);
            startActivity(i);
            finish();
        }).show();
    }

    private void setData() {
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        JSONObject params = new JSONObject();
        try {
            params.put("page_no", 1);
            params.put("page_record", 50);
            stringEntity = new StringEntity(params.toString());
        } catch (JSONException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        asyncHttpClient.post(this, getString(R.string.base_url) + getString(R.string.getUsersist), stringEntity,
                "application/json", new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                        Logger.json(response.toString());
                        User user = new Gson().fromJson(response.toString(), User.class);
                        useradapter = new UserAdapter(user.data.rows);
                        recyclerView.setAdapter(useradapter);
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        super.onFailure(statusCode, headers, throwable, errorResponse);
                        Logger.e("Error!!!", errorResponse.toString());
                    }
                });
    }

    private void setLayoutManager() {
        RecyclerView.LayoutManager LayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(LayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(useradapter);
    }
}