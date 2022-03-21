package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity6 extends AppCompatActivity {
Button btnRecal,btnSave;
EditText editBMI;
TextView tvTitleResult,tvResult;
Intent intent;
String currentDate;
    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        setContentView(R.layout.activity_main6);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        editBMI=findViewById(R.id.editBMI);
        tvResult=findViewById(R.id.tvResult);
        tvTitleResult=findViewById(R.id.tvTitleResult);
        btnSave=findViewById(R.id.btnSave);
        getSupportActionBar().hide();
        db = new DBHelper(this);
        btnRecal=findViewById(R.id.btnRecal);
        String bmiString= getIntent().getStringExtra("BMI");
        String username= getIntent().getStringExtra("user");
        editBMI.setText(bmiString);
        editBMI.setFocusable(false);
        float bmiFloat=Float.parseFloat(bmiString);

        if(bmiFloat<18.5){
            tvTitleResult.setText("Underweight");
            tvResult.setText(R.string.underweight);
        }else if(bmiFloat>=18.5 && bmiFloat<=24.9){
            tvTitleResult.setText("Normal");
            tvResult.setText(R.string.normal);
        }else if(bmiFloat>=25 && bmiFloat<=29.9){
            tvTitleResult.setText("Overweight");
            tvResult.setText(R.string.overweight);
        }else if(bmiFloat>=30 && bmiFloat<=39.9){
            tvTitleResult.setText("Obese");
            tvResult.setText(R.string.obese);
        }else {
            tvTitleResult.setText("Severe Obese");
            tvResult.setText(R.string.severeobese);
        }
        btnRecal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), MainActivity3.class);
                intent.putExtra("user",username);
                startActivity(intent);
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                   Cursor cursor = db.viewData(username);
                   while (cursor.moveToNext()) {
                       if (cursor.isNull(10)) {
                           boolean insertBMI;
                           insertBMI = db.insertBMI(bmiString, username);
                           if (insertBMI) {
                               Toast.makeText(MainActivity6.this, "Save Successfully", Toast.LENGTH_SHORT).show();
                           }
                       } else {

                           Toast.makeText(MainActivity6.this, "Previous BMI is full", Toast.LENGTH_SHORT).show();
                       }
                   }





                }catch (Exception e){
                    Log.e("ERROR", "ERROR IN CODE: " + e.toString());
                    e.printStackTrace();
                }
             /*   Intent intent=new Intent(getApplicationContext(), MainActivity7.class);
                intent.putExtra("bmi",bmiString);
                intent.putExtra("date",currentDate);
                intent.putExtra("user",username);
                startActivity(intent);*/


            }
        });

    }
}