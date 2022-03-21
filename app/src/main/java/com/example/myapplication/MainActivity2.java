package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity2 extends AppCompatActivity {
     TextView tvSignin;
     Button btnSignup;
     EditText editUsername, editPassword,editConfirm;
    DBHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        tvSignin=findViewById(R.id.tvSignin);
        btnSignup=findViewById(R.id.btnSignup);
        editUsername=findViewById(R.id.editUsername);
        editPassword=findViewById(R.id.editPassword);
        editConfirm=findViewById(R.id.editConfirm);
        getSupportActionBar().hide();
        db=new DBHelper(this);
        tvSignin.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(MainActivity2.this,MainActivity.class);
                startActivity(intent);
            }
        });

        btnSignup.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){

                String username= editUsername.getText().toString();
                String password= editPassword.getText().toString();
                String confirm= editConfirm.getText().toString();
                if(username.equals("")|| password.equals("")|| confirm.equals("")){
                    Toast.makeText(MainActivity2.this, "Please fill-up all fields.", Toast.LENGTH_SHORT).show();
                }else if(username.length()<3){
                    Toast.makeText(MainActivity2.this, "Username too short", Toast.LENGTH_SHORT).show();
                }else if(password.length()<6) {
                    Toast.makeText(MainActivity2.this, "Password too short", Toast.LENGTH_SHORT).show();
                }else {
                    if(password.equals(confirm)){
                        boolean checkUser= db.checkUsername(username);
                        if(checkUser==false){
                            boolean insert= db.insertData(username,password);
                            if(insert==true){
                                Toast.makeText(MainActivity2.this, "Registered Successfully.", Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                            }else{
                                  Toast.makeText(MainActivity2.this, "Registration Failed.", Toast.LENGTH_SHORT).show();
                            }
                        } else{
                            Toast.makeText(MainActivity2.this, "Username already exist.", Toast.LENGTH_SHORT).show();
                        }
                    } else{
                            Toast.makeText(MainActivity2.this, "Password does not match.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }
}