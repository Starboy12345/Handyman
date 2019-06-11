package com.example.handyman.activities.handymanactivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.handyman.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import de.hdodenhof.circleimageview.CircleImageView;

public class AcceptOrRejectActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnAcpt, btnRejct;
    private String uid, getHandyManId, getLocation, getName, getDate, getReason, getPhoto, adapterPosition;
    private Intent intent;
    TextView name, dtate, reason;
    ProgressDialog loading;
    CircleImageView mPhto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.requestdesign);


        initViews();
        initListerners();
    }

    private void initListerners() {
        btnRejct.setOnClickListener(this);
        btnAcpt.setOnClickListener(this);
    }

    private void initViews() {

        intent = getIntent();
        name = findViewById(R.id.txtFromUser);
        dtate = findViewById(R.id.txtToBeDonAt);
        reason = findViewById(R.id.txtReasonForRequest);
        loading = new ProgressDialog(this);
        mPhto = findViewById(R.id.mPhto);
        btnAcpt = findViewById(R.id.btnAccept);
        btnRejct = findViewById(R.id.btnReject);

        if (intent != null) {
            adapterPosition = intent.getStringExtra("position");
            getName = intent.getStringExtra("name");
            getDate = intent.getStringExtra("date");
            getReason = intent.getStringExtra("reason");
            getPhoto = intent.getStringExtra("image");
           // getLocation = intent.getStringExtra("location");

            name.setText(getName);
            dtate.setText(getReason);
            Glide.with(this).load(getPhoto).into(mPhto);

        }


        //firebase
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser mFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (mAuth.getCurrentUser() == null) {
            return;
        }
        assert mFirebaseUser != null;
        uid = mFirebaseUser.getUid();

    }

    @Override
    public void onClick(View v) {

    }
}
