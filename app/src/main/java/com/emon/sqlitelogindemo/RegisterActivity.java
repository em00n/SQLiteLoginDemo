package com.emon.sqlitelogindemo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    Button reg, login;
    EditText mobile, password, uname;
    MyDBHelper myDBHelper;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        myDBHelper = new MyDBHelper(this);

        sharedPreferences = getApplicationContext().getSharedPreferences("login", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        login = (Button) findViewById(R.id.login_btn);
        reg = (Button) findViewById(R.id.register_btn);

        mobile = (EditText) findViewById(R.id.mobile_no);
        password = (EditText) findViewById(R.id.password);
        uname = (EditText) findViewById(R.id.uname);
        if (sharedPreferences.getString("login", "0").equalsIgnoreCase("1")) {
            startActivity(new Intent(RegisterActivity.this, MainActivity.class));
            finish();
        }


        login.setOnClickListener(this);
        reg.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.login_btn:
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                finish();
                break;
            case R.id.register_btn:
                register();
                break;
            default:
                Toast.makeText(RegisterActivity.this, "Default", Toast.LENGTH_LONG).show();
                break;
        }

    }

    private void register() {
        String mobileNo = mobile.getText().toString().trim();
        String pass = password.getText().toString().trim();
        String username = uname.getText().toString().trim();
        if (!mobileNo.isEmpty() && !pass.isEmpty() && !username.isEmpty()) {
            long id = myDBHelper.addData(mobileNo, pass, username);
            if (id > 0) {
                Toast.makeText(RegisterActivity.this, "Register Success!", Toast.LENGTH_LONG).show();
                startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                finish();
            } else {
                Toast.makeText(RegisterActivity.this, "Register Faild!", Toast.LENGTH_LONG).show();
            }
        }
    }
}
