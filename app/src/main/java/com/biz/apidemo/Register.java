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
public class Register extends AppCompatActivity {

    StringEntity entity;
    
    @BindView(R.id.et_fname)
    AppCompatEditText et_fname;
    @BindView(R.id.et_lname)
    AppCompatEditText et_lname;
    @BindView(R.id.et_address)
    AppCompatEditText et_address;
    @BindView(R.id.et_emailid)
    AppCompatEditText et_emailid;
    @BindView(R.id.et_password)
    AppCompatEditText et_password;
    @BindView(R.id.et_cpassword)
    AppCompatEditText et_cpassword;
    @BindView(R.id.et_mobileno)
    AppCompatEditText et_mobileno;
    @BindView(R.id.btn_register)
    AppCompatButton btn_register;
    @BindView(R.id.action_login)
    AppCompatTextView action_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_register, R.id.action_login})
    public void OnclickRegister(View view) {
        switch (view.getId()) {
            case R.id.btn_register:
                if (RegistrationValidation()) {
                    AsyncHttpClient client = new AsyncHttpClient();
                    JSONObject params = new JSONObject();
                    try {
                        params.put("first_name", et_fname.getText().toString().trim());
                        params.put("last_name", et_lname.getText().toString().trim());
                        params.put("email_id", et_emailid.getText().toString().trim());
                        params.put("password", et_password.getText().toString().trim());
                        params.put("address", et_address.getText().toString().trim());
                        params.put("mobile_no", et_mobileno.getText().toString().trim());
                        entity = new StringEntity(params.toString());
                    } catch (JSONException | UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    client.post(this, getString(R.string.base_url) + getString(R.string.register), entity,
                            "application/json", new JsonHttpResponseHandler() {
                                @SuppressLint("SetTextI18n")
                                @Override
                                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                                    super.onSuccess(statusCode, headers, response);
                                    Logger.json(response.toString());
                                    Intent i = new Intent(Register.this, Login.class);
                                    startActivity(i);
                                    LayoutInflater inflater = getLayoutInflater();
                                    View layout = inflater.inflate(R.layout.toast_layout, findViewById(R.id.container));
                                    AppCompatTextView textView = layout.findViewById(R.id.text);
                                    textView.setText("Successfully Registered");
                                    Toast toast = new Toast(getApplicationContext());
                                    toast.setDuration(Toast.LENGTH_LONG);
                                    toast.setGravity(Gravity.BOTTOM, 0, 100);
                                    toast.setView(layout);
                                    toast.show();
                                }

                                @Override
                                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                                    super.onFailure(statusCode, headers, throwable, errorResponse);
                                    Logger.e("Register error", "" + errorResponse.toString());
                                    Logger.e("String Error", "status code " + statusCode);
                                }
                            });
                }
                break;
            case R.id.action_login:
                Intent i = new Intent(this, Login.class);
                startActivity(i);
                break;
        }
    }

    @SuppressLint("SetTextI18n")
    private boolean RegistrationValidation() {
        if (!new FormValidation().checkEmptyET(et_fname)) {
            if (new FormValidation().checkname(et_fname)) {
                if (!new FormValidation().checkEmptyET(et_fname)) {
                    if (new FormValidation().checkname(et_fname)) {
                        if (!new FormValidation().checkEmptyET(et_emailid)) {
                            if (new FormValidation().checkemailid(et_emailid)) {
                                if (!new FormValidation().checkEmptyET(et_password)) {
                                    if (new FormValidation().checkpassword(et_password)) {
                                        if (!new FormValidation().checkEmptyET(et_cpassword)) {
                                            if (new FormValidation().checkconfirmpassword(et_cpassword, et_password)) {
                                                if (!new FormValidation().checkEmptyET(et_mobileno))
                                                    if (new FormValidation().checkMobileNumber(et_mobileno)) {
                                                        //Toast.makeText(getApplicationContext(), "Successfully Registered", Toast.LENGTH_SHORT).show();
                                                        return true;
                                                    } else {
                                                        et_mobileno.requestFocus();
                                                        et_mobileno.setError("Mobile no must be 10 digits");
                                                        return false;
                                                    }
                                                else {
                                                    et_mobileno.requestFocus();
                                                    et_mobileno.setError("Mobile No is empty.");
                                                    return false;
                                                }
                                            } else {
                                                et_cpassword.requestFocus();
                                                et_cpassword.setError("Password and Confirm Password are not matched!!");
                                                return false;
                                            }
                                        } else {
                                            et_cpassword.requestFocus();
                                            et_cpassword.setError("Confirm Password is empty.");
                                            return false;
                                        }
                                    } else {
                                        et_password.requestFocus();
                                        et_password.setError("Password must be 6 characters,1 Uppercase,1 Lowercase,1 Number.");
                                        return false;
                                    }
                                } else {
                                    et_password.requestFocus();
                                    et_password.setError("Password is empty.");
                                    return false;
                                }
                            } else {
                                et_emailid.requestFocus();
                                et_emailid.setError("Emailid must be xyz@abc.xyz");
                                return false;
                            }
                        } else {
                            et_emailid.requestFocus();
                            et_emailid.setError("EmailId is empty.");
                            return false;
                        }
                    } else {
                        et_lname.requestFocus();
                        et_lname.setError("Last Name must be xyz not any inputed digit or speacial characters.");
                        return false;
                    }
                } else {
                    et_lname.requestFocus();
                    et_lname.setError("Last Name is empty.");
                    return false;
                }
            } else {
                et_fname.requestFocus();
                et_fname.setError("First Name must be xyz not any inputed digit or speacial characters.");
                return false;
            }
        } else {
            et_fname.requestFocus();
            et_fname.setError("First Name is empty.");
            return false;
        }
    }
}