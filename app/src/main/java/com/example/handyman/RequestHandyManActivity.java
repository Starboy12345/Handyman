package com.example.handyman;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class RequestHandyManActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView txtName,txtOccupation,txtDate;
    private CircleImageView mPhoto;
    private EditText edtAbt, edtReason;
    private Button btnRequest,btnStartDate;
    private String uid, getName, getAbt, getOccupation,  getPhoto, adapterPosition;
    private Intent intent;
    ProgressDialog loading;
    private String dayOfTheWeek;
    DatabaseReference request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_handy_man);

        initViews();
        initListener();
    }


    private void initListener() {
        btnRequest.setOnClickListener(this);
        btnStartDate.setOnClickListener(this);
    }

    private void initViews() {
        //firebase
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser mFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (mAuth.getCurrentUser() == null) {
            return;
        }
        assert mFirebaseUser != null;
        uid = mFirebaseUser.getUid();


        intent = getIntent();
        txtName = findViewById(R.id.handyManName);
        txtOccupation = findViewById(R.id.handyManOccupation);
        mPhoto = findViewById(R.id.handyManPhoto);
        loading = new ProgressDialog(this);
        edtAbt = findViewById(R.id.edtAbt);
        txtDate = findViewById(R.id.edtDate);
        edtReason = findViewById(R.id.edtReason);

        if (intent != null) {
            adapterPosition = intent.getStringExtra("position");
            getName = intent.getStringExtra("name");
            getAbt = intent.getStringExtra("about");
            getOccupation = intent.getStringExtra("occupation");
            getPhoto = intent.getStringExtra("photo");

            txtName.setText(getName);
            txtOccupation.setText(getOccupation);
            edtAbt.setText(getAbt);
            Glide.with(this).load(getPhoto).into(mPhoto);

            dayOfTheWeek = new SimpleDateFormat("EEE", Locale.ENGLISH).format(System.currentTimeMillis());
            //request
            request = FirebaseDatabase.getInstance().getReference("Request").child(dayOfTheWeek).child(adapterPosition);
        }

    }


    @Override
    public void onClick(View v) {

    }
}
