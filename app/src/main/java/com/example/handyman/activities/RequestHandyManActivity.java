package com.example.handyman.activities;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.handyman.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class RequestHandyManActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView txtName, txtOccupation, txtDate, edtAbt;
    private static final String TAG = "RequestHandyManActivity";
    private CircleImageView mPhoto;
    private EditText edtReason;
    private Button btnRequest, btnStartDate;
    private String uid, getLocation, getName, getAbt, getOccupation, getPhoto, adapterPosition;
    private Intent intent;
    ProgressDialog loading;
    private String dayOfTheWeek, startDateSelected, ownName, ownerPhoto;
    DatabaseReference request;
    private DatePickerDialog datePicker;
    private final Calendar calendar = Calendar.getInstance();
    private int year = calendar.get(Calendar.YEAR);
    private int month = calendar.get(Calendar.MONTH);
    private int day = calendar.get(Calendar.DAY_OF_MONTH);
    private String getStartDate;
    private Date date;
    SimpleDateFormat sfd;
    private DatabaseReference UserRef, requestDbRef;
    private String notApproved = "Not yet Approved";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_handy_man);

        date = calendar.getTime();
        sfd = new SimpleDateFormat("EEE dd-MMMM-yyyy ",
                Locale.US);




        initViews();
        initListener();
    }



    private void initListener() {
        btnRequest.setOnClickListener(this);
        btnStartDate.setOnClickListener(this);
    }

    private void initViews() {
        intent = getIntent();
        txtName = findViewById(R.id.handyManName);
        txtOccupation = findViewById(R.id.handyManOccupation);
        mPhoto = findViewById(R.id.handyManPhoto);
        loading = new ProgressDialog(this);
        edtAbt = findViewById(R.id.edtAbt);
        txtDate = findViewById(R.id.edtDate);
        edtReason = findViewById(R.id.edtReason);
        btnRequest = findViewById(R.id.btnRequest);
        btnStartDate = findViewById(R.id.btnStartDate);

        if (intent != null) {
            adapterPosition = intent.getStringExtra("position");
            getName = intent.getStringExtra("name");
            getAbt = intent.getStringExtra("details");
            getOccupation = intent.getStringExtra("occupation");
            getPhoto = intent.getStringExtra("image");
            getLocation = intent.getStringExtra("location");

            txtName.setText(getName);
            txtOccupation.setText(getOccupation);
            edtAbt.setText(getAbt);
            Glide.with(this).load(getPhoto).into(mPhoto);

            dayOfTheWeek = new SimpleDateFormat("EEE", Locale.ENGLISH).format(System.currentTimeMillis());
            //request
            request = FirebaseDatabase.getInstance().getReference("Request").child(dayOfTheWeek).child(adapterPosition);
        }


        //firebase
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser mFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (mAuth.getCurrentUser() == null) {
            return;
        }
        assert mFirebaseUser != null;
        uid = mFirebaseUser.getUid();
        requestDbRef = FirebaseDatabase.getInstance().getReference().child("Users").child(adapterPosition);
        UserRef = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);

        UserRef.keepSynced(true);
        requestDbRef.keepSynced(true);


        UserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {
                    if (dataSnapshot.exists()) {
                        //retrieve the details and set the on the users profile
                        ownName = (String) dataSnapshot.child("fullName").getValue();
                        ownerPhoto = (String) dataSnapshot.child("image").getValue();


                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                try {
                    Log.d(TAG, "Error : " + databaseError.getMessage());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnStartDate:
                callStartDate();
                break;

            case R.id.btnRequest:

                if (!btnRequest.isEnabled()) {
                    makeToast("Cannot request please select another date");
                } else {
                    sendRequestToHandyMan();
                }

                break;
        }

    }


    //Method to check the start date selected by the user and display the valid date into the view
    private void callStartDate() {
        datePicker = new DatePickerDialog(RequestHandyManActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                try {
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(Calendar.YEAR, year);
                    calendar.set(Calendar.MONTH, month);
                    calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                    startDateSelected = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());


                    if (date.before(new Date(startDateSelected))) {
                        checkSuccessSelectStartDate();


                    } else if (date.after(new Date(startDateSelected))) {
                        displayErrorOnStartDateSelected();

                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

        }, year, month, day);

        datePicker.show();
    }


    //checks corresponding date from the user and allows them to proceed
    void checkSuccessSelectStartDate() {
        txtDate.setText(sfd.format(new Date(startDateSelected)));
        txtDate.setTextColor(getResources().getColor(R.color.colorGreen));
        btnRequest.setEnabled(true);
    }

    //if date selected is before the current date ... display error
    void displayErrorOnStartDateSelected() {
        txtDate.setText("Please select a day after today");
        txtDate.setTextColor(getResources().getColor(R.color.colorRed));
        btnRequest.setEnabled(false);

    }

    public void makeToast(String text) {
        Toast toast = Toast.makeText(this, text, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }


    private void sendRequestToHandyMan() {
        final String getReason = edtReason.getText().toString();

        if (!edtReason.toString().isEmpty()) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {


                    Map<String, Object> leave = new HashMap<>();
                    leave.put("userId", uid);
                    leave.put("ownerName", ownName);
                    leave.put("ownerImage", ownerPhoto);
                    leave.put("date", ServerValue.TIMESTAMP);
                    leave.put("reason", getReason);
                    leave.put("response", notApproved);


                    String requestId = requestDbRef.push().getKey();
                    assert requestId != null;

                    requestDbRef.child(requestId).setValue(leave).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                makeToast("Request has been sent");
                            } else {
                                makeToast("Request Failed try again");
                            }

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            e.printStackTrace();
                            makeToast("Request Failed try again");

                        }
                    });
                }
            });


        } else if (edtReason.getText().toString().isEmpty()) {
            edtReason.setError("Please state your reason");
            makeToast("Please state your reason");
        }


    }

}
