package com.example.handyman.activities.customeractivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.handyman.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class RatingActivity extends AppCompatActivity {
    private String getHandyManPhoto, getHandyName, getPosition;
    private RatingBar mRateHandyMan;
    private DatabaseReference requestDbRef;
    private float rating;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);
        this.setFinishOnTouchOutside(false);


        Intent getRatingIntent = getIntent();
        mRateHandyMan = findViewById(R.id.ratedResults);
        if (getRatingIntent != null) {

            getPosition = getRatingIntent.getStringExtra("position");
            getHandyName = getRatingIntent.getStringExtra("handyManName");
            getHandyManPhoto = getRatingIntent.getStringExtra("handyManPhoto");

            TextView txtRateHandyMan = findViewById(R.id.txtRateUser);
            CircleImageView photo = findViewById(R.id.rateHandyPhoto);
            mRateHandyMan = findViewById(R.id.ratedResults);
            progressBar = findViewById(R.id.progressBarLoading);

            txtRateHandyMan.setText(String.format("Rate %s", getHandyName));
            Glide.with(this).load(getHandyManPhoto).into(photo);
            requestDbRef = FirebaseDatabase.getInstance().getReference("Requests").child(getPosition);

            findViewById(R.id.btnCancelRate).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });


            findViewById(R.id.btnRateHandyMan).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (mRateHandyMan.getRating() == 0.0) {
                        makeToast("Please tap the rating bar");
                    } else {

                        rateHandyMan();
                        //String rating = "Rating is :" + mRateHandyMan.getRating();
                       // makeToast(rating);

                    }


                }

                private void rateHandyMan() {
                    progressBar.setVisibility(View.VISIBLE);

                    rating = mRateHandyMan.getRating();
                    Map<String,Object> rateHandy = new HashMap<>();
                    rateHandy.put("rating",rating);

                    //update the request database
                    requestDbRef.updateChildren(rateHandy).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful()){
                                progressBar.setVisibility(View.GONE);
                                makeToast("Handy man has been rated!!");
                                finish();
                            }else{
                                progressBar.setVisibility(View.GONE);
                                makeToast("Sorry please try again");
                            }
                        }
                    });

                }
            });

        }

    }

    public void makeToast(String text) {
        Toast toast = Toast.makeText(this, text, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
}
