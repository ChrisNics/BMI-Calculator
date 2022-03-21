package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    EditText editUsername,editPassword;
    TextView tvSignup;
    Button btnSignin;
    DBHelper db;
    ProgressBar pb;
    int counter=0;
    String username,password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);
        tvSignup=findViewById(R.id.tvSignup);
        btnSignin=findViewById(R.id.btnSignin);
        editPassword=findViewById(R.id.editPassword);
        editUsername=findViewById(R.id.editUsername);
        pb=findViewById(R.id.pb);
        db=new DBHelper(this);
        getSupportActionBar().hide();
        pb.setVisibility(View.INVISIBLE);



         // pag pinindot yung sign in
       btnSignin.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){

                 username=editUsername.getText().toString();
                 password=editPassword.getText().toString();

                if(username.equals("")|| password.equals("")) {
                    Toast.makeText(MainActivity.this, "Please fill-up all fields.", Toast.LENGTH_SHORT).show();
                }else{

                    boolean checkusernamepassword=db.checkusernamepassword(username,password);
                    if(checkusernamepassword==true){
                        editUsername.setEnabled(false);
                        editPassword.setEnabled(false);
                        pbSignin(username);
                    }else{
                        Toast.makeText(MainActivity.this, "Invalid Credentials.", Toast.LENGTH_SHORT).show();
                    }
                }


            }
        }); // end ng sign in


        // start ng signup
        tvSignup.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this,MainActivity2.class);
                startActivity(intent);

            }
        });  // end ng signup

    } // end ng oncreate

  // progress bar sa sign in
   public void pbSignin(String username){
        pb.setVisibility(View.VISIBLE);
        final Timer t= new Timer();
        TimerTask tt= new TimerTask() {
            @Override
            public void run() {
                counter+=2;
                pb.setProgress(counter);
                if(counter==100)
                    t.cancel();
                Intent intent=new Intent(getApplicationContext(), MainActivity3.class);
                intent.putExtra("user",username);
                startActivity(intent);
            }
        };

        t.schedule(tt,0,100);
   } // end ng progress bar sign in
}