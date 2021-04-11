package com.e.costingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity {

    Button submit_btn;
    TextInputEditText username, password;
    TextView forget_pwd;
    LinearLayout sign_up;
    MyDatabaseHelper1 DB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        submit_btn = findViewById(R.id.submit);
        username  =findViewById(R.id.username_input);
        password = findViewById(R.id.pwd_input);

        sign_up = findViewById(R.id.sign_up);
        forget_pwd = findViewById(R.id.pwd_input);

        DB = new MyDatabaseHelper1(this);

//      submit button
        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = username.getText().toString().trim();
                String pwd = password.getText().toString();
                if(user.equals("") || pwd.equals("")){
                    Toast.makeText(LoginActivity.this, "Username and Password required!", Toast.LENGTH_SHORT).show();
                }
                else   {
                    Boolean checkUserPass = DB.checkUserPwd(user, pwd);
                    if(checkUserPass==true){
                        Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                        Intent intent =new Intent(LoginActivity.this, Project_Home.class);
                        startActivity(intent);
                        finish();
                    }
                    else{
                        Toast.makeText(LoginActivity.this, "Invalid credentials", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

//        sign up button
        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(LoginActivity.this, SignUp_Activity.class);
                startActivity(intent);
            }
        });

//      forget password
        forget_pwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                code
            }
        });


    }
}