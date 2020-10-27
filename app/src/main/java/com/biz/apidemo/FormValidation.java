package com.biz.apidemo;

import androidx.appcompat.widget.AppCompatEditText;

public class FormValidation {

    String emailPattern = "[a-zA-Z0-9._-]+@[a-z@$%&#-]+\\.+[a-z]+";
    String password = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{4,12}$";
    String name = "[a-zA-Z]*";
    String no = "[0-9]{10}";

    public boolean checkEmptyET(AppCompatEditText editText) {
        return editText.getText().toString().trim().isEmpty();
    }

    public boolean checkemailid(AppCompatEditText editText) {
        return editText.getText().toString().trim().matches(emailPattern);
    }

    public boolean checkpassword(AppCompatEditText editText) {
        return editText.getText().toString().trim().matches(password);
    }

    public boolean checkname(AppCompatEditText editText) {

        return editText.getText().toString().trim().matches(name);
    }

    public boolean checkconfirmpassword(AppCompatEditText editText, AppCompatEditText editText1) {
        return editText.getText().toString().trim().equals(editText1.getText().toString().trim());
    }

    public boolean checkMobileNumber(AppCompatEditText editText) {
        if (editText.getText().toString().length() == 10) {
            return editText.getText().toString().trim().matches(no);
        }
        return false;
    }
}
