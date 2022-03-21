package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import android.os.Bundle;
import android.widget.Toast;
import android.widget.Toolbar;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity3 extends AppCompatActivity {
   TextView tvUsername,tvBMI;
   EditText editHeight,editWeight,editAge;
   Button btnHelp;
   DBHelper db;
   TextView tvDelete;
   AlertDialog dialog;
   ColorDrawable cd;
   Menu menu;
   Button btnMale,btnFemale;
   CardView btnCalculate;
   String username;
   boolean gender=false;

   int counter=0;
   ProgressBar pbCalculate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main3);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        btnMale=findViewById(R.id.btnMale);
        btnFemale=findViewById(R.id.btnFemale);
        getSupportActionBar().setBackgroundDrawable(cd);
        Intent intent= getIntent();
        username=intent.getStringExtra("user");
        tvUsername=findViewById(R.id.tvUsername);
        tvUsername.setText("Hi,  " + username);
        tvBMI=findViewById(R.id.tvBMI);
        tvDelete=findViewById(R.id.tvDelete);
        cd= new ColorDrawable(Color.parseColor("#6182f7"));
        getSupportActionBar().setBackgroundDrawable(cd);
        btnCalculate=findViewById(R.id.btnCalculate);
        pbCalculate=findViewById(R.id.pbCalculate);
        pbCalculate.setVisibility(View.INVISIBLE);
        editAge=findViewById(R.id.editAge);
        editHeight=findViewById(R.id.editHeight);
        editWeight=findViewById(R.id.editWeight);
        editWeight.setText("");
        editHeight.setText("");
        editAge.setText("");

       btnCalculate.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

                   float height = 0, weight = 0;
                   String heightString, weightString, ageString;
                   int age = 0;
                   heightString = editHeight.getText().toString();
                   weightString = editWeight.getText().toString();
                   ageString = editAge.getText().toString();
                   if (heightString.equals("") || weightString.equals("") || ageString.equals("")) {
                       Toast.makeText(MainActivity3.this, "Please fill-up all fields.", Toast.LENGTH_SHORT).show();
                   }else if (gender==false){
                       Toast.makeText(MainActivity3.this, "Are u female or male?", Toast.LENGTH_SHORT).show();
                   }else {
                       height = Float.parseFloat(editHeight.getText().toString());
                       weight = Float.parseFloat(editWeight.getText().toString());
                       age = Integer.parseInt(editAge.getText().toString());
                       pbCalculateBMI(height, weight, age, username);
                   }



           }
       });

       btnFemale.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               gender=true;
               btnFemale.setBackgroundResource(R.drawable.female_click);
               btnMale.setBackgroundResource(R.drawable.male);
           }
       });

        btnMale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gender=true;
                btnFemale.setBackgroundResource(R.drawable.female);
                btnMale.setBackgroundResource(R.drawable.male_click);
            }
        });








    }

    public void pbCalculateBMI(float height,float weight,int age,String username){
        tvBMI.setText("     CALCULATING...");
        tvBMI.setTextColor(Color.parseColor("#97abf3"));
        pbCalculate.setVisibility(View.VISIBLE);
        float tempResult,result;
        tempResult=height/100;
        result=(weight/(tempResult*tempResult));

        String result1=String.format("%.2f",result);

        final Timer t= new Timer();
        TimerTask tt= new TimerTask() {
            @Override
            public void run() {
                counter+=2;
                pbCalculate.setProgress(counter);
                if(counter==100)
                    t.cancel();
              Intent intent=new Intent(getApplicationContext(), MainActivity6.class);
              intent.putExtra("BMI",result1);
              intent.putExtra("user",username);
              startActivity(intent);




            }
        };

        t.schedule(tt,0,100);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();


        if(id==R.id.itemChart){
            Intent intent = new Intent(MainActivity3.this,MainActivity8.class);
            intent.putExtra("user",username);
            startActivity(intent);
        } else if(id==R.id.itemAbout){
            Intent intent = new Intent(MainActivity3.this,MainActivity4.class);
            startActivity(intent);
        }else if(id==R.id.itemRecord){
            Intent intent = new Intent(MainActivity3.this,MainActivity7.class);
            intent.putExtra("user",username);
            startActivity(intent);
        }else{

        }

        return super.onOptionsItemSelected(item);
    }



  // start of delete account
    public void deleteAccount(View v){
        db=new DBHelper(this);
        dialog= new AlertDialog.Builder(this)
                .setMessage("Are you sure to delete this account?")
                .setPositiveButton("Cancel",null)
                .setNegativeButton("Ok",null)
                .show();
        Button positiveButton=dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();



            }
        });
        Button negativeButton=dialog.getButton(AlertDialog.BUTTON_NEGATIVE);
        negativeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= getIntent();
                String username=intent.getStringExtra("user");
                boolean deleteAccount;
                deleteAccount= db.deleteAccount(username);
                if(deleteAccount==true){
                    Toast.makeText(MainActivity3.this, "Deleted Successfully.", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(MainActivity3.this,MainActivity.class);
                    startActivity(i);
                    finish();
                }
            }
        });


    } // end of delete acc

    public void logOut(View v){
        dialog= new AlertDialog.Builder(this)
                .setMessage("Are you sure?")
                .setPositiveButton("Cancel",null)
                .setNegativeButton("Ok",null)
                .show();

        Button positiveButton=dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();



            }
        });

        Button negativeButton=dialog.getButton(AlertDialog.BUTTON_NEGATIVE);
        negativeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(MainActivity3.this, "Logout Succesfully.", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(MainActivity3.this,MainActivity.class);
                startActivity(i);
                finish();



            }
        });
    }


}