package com.example.handyman.activities.customeractivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.handyman.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class RatingActivity extends AppCompatActivity {
    private String getHandyManPhoto, getHandyName, getPosition;
    private RatingBar mRateHandyMan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);
        this.setFinishOnTouchOutside(false);


        Intent getRatingIntent = getIntent();
        mRateHandyMan = findViewById(R.id.ratingBar);
        if (getRatingIntent != null) {

            getPosition = getRatingIntent.getStringExtra("position");
            getHandyName = getRatingIntent.getStringExtra("handyManName");
            getHandyManPhoto = getRatingIntent.getStringExtra("handyManPhoto");

            TextView txtRateHandyMan = findViewById(R.id.txtRateUser);
            CircleImageView photo = findViewById(R.id.rateHandyPhoto);
            mRateHandyMan = findViewById(R.id.ratingBar);

            txtRateHandyMan.setText("Rate " + getHandyName);
            Glide.with(this).load(getHandyManPhoto).into(photo);

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
                        String rating = "Rating is :" + mRateHandyMan.getRating();
                        makeToast(rating);

                    }


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
