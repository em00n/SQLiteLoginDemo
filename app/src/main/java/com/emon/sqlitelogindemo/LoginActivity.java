package com.emon.sqlitelogindemo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    Button reg, login;
    EditText mobile, password;
    MyDBHelper myDBHelper;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sharedPreferences = getApplicationContext().getSharedPreferences("login", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        myDBHelper = new MyDBHelper(this);

        login = (Button) findViewById(R.id.login_btn);
        reg = (Button) findViewById(R.id.register_btn);

        mobile = (EditText) findViewById(R.id.mobile_no);
        password = (EditText) findViewById(R.id.password);

        login.setOnClickListener(this);
        reg.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.login_btn:
                login();
                break;
            case R.id.register_btn:
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                finish();
                break;
            default:
                Toast.makeText(LoginActivity.this, "Default", Toast.LENGTH_LONG).show();
                break;
        }
    }

    private void login() {
        String mobileNo = mobile.getText().toString().trim();
        String pass = password.getText().toString().trim();
        if (!mobileNo.isEmpty() && !pass.isEmpty()) {
            Boolean cursor = myDBHelper.login(mobileNo, pass);
            try {
                if (cursor == true) {
                    String login = "1";
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();
                    editor.putString("login", login);
                    editor.commit();
                } else {
                    Toast.makeText(this, "Your not valid user", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

}