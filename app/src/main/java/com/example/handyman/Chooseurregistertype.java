package com.example.handyman;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.handyman.activities.customeractivity.CustomerRegister;
import com.example.handyman.activities.handymanactivity.HandymanRegister;

public class Chooseurregistertype extends AppCompatActivity {
    Button btncustomer,btnhandyman;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chooseurregistertype);

        btncustomer=findViewById(R.id.btncustomerlogin1);
        btnhandyman=findViewById(R.id.btnhandyman1);

        btncustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Chooseurregistertype.this, CustomerRegister.class));
            }
        });

        btnhandyman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Chooseurregistertype.this, HandymanRegister.class));
            }
        });

    }
}
