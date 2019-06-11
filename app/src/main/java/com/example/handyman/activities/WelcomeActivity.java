package com.example.handyman.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.handyman.R;
import com.example.handyman.activities.customeractivity.CustomerLogin;
import com.example.handyman.activities.handymanactivity.HandyManLogin;

public class WelcomeActivity extends AppCompatActivity {
    Button btncustomer,btnhandyman;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        btncustomer=findViewById(R.id.btncustomerlogin);
        btnhandyman=findViewById(R.id.btnhandyman);

        btncustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
  startActivity(new Intent(WelcomeActivity.this, CustomerLogin.class));


            }
        });

        btnhandyman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

   startActivity(new Intent(WelcomeActivity.this, HandyManLogin.class));

                ;
            }
        });


    }
}
