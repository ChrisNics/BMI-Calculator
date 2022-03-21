package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity7 extends AppCompatActivity {
    TextView tvBMI1,tvBMI2,tvBMI3,tvBMI4,tvBMI5;
    TextView tvDate1,tvDate2,tvDate3,tvDate4,tvDate5;
    Button btnDelete;
    String bmi,date;
    DBHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {

            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main7);
            setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            btnDelete = findViewById(R.id.btnDelete);
            bmi = getIntent().getStringExtra("bmi");
            date = getIntent().getStringExtra("date");
            String username = getIntent().getStringExtra("user");
            db = new DBHelper(this);
            getSupportActionBar().hide();
            Cursor cursor = db.viewData(username);
            tvBMI1 = findViewById(R.id.tvBMI1);
            tvBMI2 = findViewById(R.id.tvBMI2);
            tvBMI3 = findViewById(R.id.tvBMI3);
            tvBMI4 = findViewById(R.id.tvBMI4);
            tvBMI5 = findViewById(R.id.tvBMI5);
            tvDate1 = findViewById(R.id.tvDate1);
            tvDate2 = findViewById(R.id.tvDate2);
            tvDate3 = findViewById(R.id.tvDate3);
            tvDate4 = findViewById(R.id.tvDate4);
            tvDate5 = findViewById(R.id.tvDate5);


              if (cursor.getCount() == 0) {
                  Toast.makeText(this, "View Data Failed;", Toast.LENGTH_SHORT).show();
                  return;
              }

            StringBuffer bufferBMI1 =new StringBuffer();
            StringBuffer bufferDate1 =new StringBuffer();
            StringBuffer bufferBMI2 =new StringBuffer();
            StringBuffer bufferDate2 =new StringBuffer();
            StringBuffer bufferBMI3 =new StringBuffer();
            StringBuffer bufferDate3 =new StringBuffer();
            StringBuffer bufferBMI4 =new StringBuffer();
            StringBuffer bufferDate4 =new StringBuffer();
            StringBuffer bufferBMI5 =new StringBuffer();
            StringBuffer bufferDate5 =new StringBuffer();


              while (cursor.moveToNext()) {

                  tvBMI1.setText(bufferBMI1.append(cursor.getString(2)));
                  tvDate1.setText(bufferDate1.append(cursor.getString(3)));
                  tvBMI2.setText(bufferBMI2.append(cursor.getString(4)));
                  tvDate2.setText(bufferDate2.append(cursor.getString(5)));
                  tvBMI3.setText(bufferBMI3.append(cursor.getString(6)));
                  tvDate3.setText(bufferDate3.append(cursor.getString(7)));
                  tvBMI4.setText(bufferBMI4.append(cursor.getString(8)));
                  tvDate4.setText(bufferDate4.append(cursor.getString(9)));
                  tvBMI5.setText(bufferBMI5.append(cursor.getString(10)));
                  tvDate5.setText(bufferDate5.append(cursor.getString(11)));

              }
            cursor.close();
            if(tvBMI1.getText().equals("null")){
                tvDate1.setText("");
                tvBMI1.setText("");
            }
            if(tvBMI2.getText().equals("null")){
                tvDate2.setText("");
                tvBMI2.setText("");
            }
            if(tvBMI3.getText().equals("null")){
                tvDate3.setText("");
                tvBMI3.setText("");
            }
            if(tvBMI4.getText().equals("null")){
                tvDate4.setText("");
                tvBMI4.setText("");
            }
            if(tvBMI5.getText().equals("null")){
                tvDate5.setText("");
                tvBMI5.setText("");
            }




















            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    boolean clearData=db.clearData(username);
                    if (clearData){
                        Toast.makeText(MainActivity7.this, "Record Deleted!", Toast.LENGTH_SHORT).show();
                        finish();
                        overridePendingTransition(0, 0);
                        startActivity(getIntent());
                        overridePendingTransition(0, 0);
                    }else{
                        Toast.makeText(MainActivity7.this, "Delete Failed", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }catch(Exception e){
            Log.e("ERROR", "ERROR IN CODE: " + e.toString());
            e.printStackTrace();
        }


    }
}