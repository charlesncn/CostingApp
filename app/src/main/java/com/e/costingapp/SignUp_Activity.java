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

public class SignUp_Activity extends AppCompatActivity {

    Button submit_btn;
    TextInputEditText username, password, cmf_password, department;
    TextView sign_in;
    MyDatabaseHelper1 DB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_);


        submit_btn = findViewById(R.id.submit);
        username  =findViewById(R.id.username_sign);
        password = findViewById(R.id.pwd_sign);
        cmf_password = findViewById(R.id.cmf_pwd);
        department = findViewById(R.id.department_sign);

        sign_in = findViewById(R.id.sign_in);

        DB = new MyDatabaseHelper1(this);
        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent =new Intent(SignUp_Activity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
//submit
        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = username.getText().toString().trim();
                String pwd = password.getText().toString();
                String cmf_pwd = password.getText().toString();
                String dptmt = department.getText().toString();
                if(user.equals("") || pwd.equals("") || cmf_pwd.equals("") || dptmt.equals(""))
                    Toast.makeText(SignUp_Activity.this, "Username and Password required!", Toast.LENGTH_SHORT).show();

                else{
                    if(pwd.equals(cmf_pwd)){
                        Boolean checkUser = DB.checkUser(user);
                        if(checkUser==false){
                            Boolean insert = DB.insertData(user, pwd, dptmt);
                            if(insert==true){
                                Toast.makeText(SignUp_Activity.this, "Registered", Toast.LENGTH_SHORT).show();
                                Intent intent =new Intent(SignUp_Activity.this, LoginActivity.class);
                                startActivity(intent);
                                finish();
                            }
                            else{
                                Toast.makeText(SignUp_Activity.this, "Failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else{
                            Toast.makeText(SignUp_Activity.this, "User exist, please login", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        Toast.makeText(SignUp_Activity.this, "Password didn't match", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}