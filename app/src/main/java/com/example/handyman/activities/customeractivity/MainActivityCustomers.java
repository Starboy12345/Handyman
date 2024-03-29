package com.example.handyman.activities.customeractivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.handyman.EditProfile;
import com.example.handyman.R;
import com.example.handyman.activities.handymanactivity.CarpenterActivity;
import com.example.handyman.activities.handymanactivity.GardenerListActivity;
import com.example.handyman.activities.handymanactivity.PainterActivity;
import com.example.handyman.activities.handymanactivity.PestControlListActivity;
import com.example.handyman.activities.handymanactivity.PlumberListActivity;
import com.example.handyman.activities.handymanactivity.RollerListActivity;
import com.example.handyman.activities.handymanactivity.TilerListActivity;
import com.example.handyman.activities.handymanactivity.TvInstallerActivity;
import com.example.handyman.models.GridBaseAdapter;
import com.example.handyman.models.ImageModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivityCustomers extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FirebaseAuth mAuth;
    FirebaseUser firebaseUser;
    private DatabaseReference UserRef;
    private static final String TAG = "MainActivity";
    private TextView FullName, Email;
    private CircleImageView userImage;


    private GridView gvGallery;
    private GridBaseAdapter gridBaseAdapter;
    private ArrayList<ImageModel> imageModelArrayList;

    private int[] myImageList = new int[]{R.drawable.mechanic, R.drawable.gardener,
            R.drawable.plumber, R.drawable.roller, R.drawable.pest, R.drawable.tiler
            , R.drawable.tv, R.drawable.carpenter,
            R.drawable.paint,};
    private String[] myImageNameList = new String[]{"Mechanic", "Gardener",
            "Plumber", "Roller", "Pest Control", "Tiler"
            , "TV Installation", "Carpenter",
            "Painter",};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_main_page);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        float[] results = new float[1];
        Location.distanceBetween(5.5, -0.2, 3.56, -0.58, results);
        float distanceInMeters = results[0];
        boolean isWithinRange = distanceInMeters < 145;

        if (!isWithinRange) {

            Log.i(TAG, "cannot check in from this location: ");
            //makeToast("cannot check in from this location ");
        } else {
            // makeToast("Can check in");
            Log.i(TAG, " can check in ");
        }

        Log.i(TAG, "onLocationChanged --- distance in meters: " + distanceInMeters);

        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();
        if (mAuth.getCurrentUser() == null) {
            return;
        }
        String userId = firebaseUser.getUid();
        UserRef = FirebaseDatabase.getInstance().getReference().child("Users").child(userId);
        UserRef.keepSynced(true);


        gvGallery = findViewById(R.id.gv);

        imageModelArrayList = populateList();

        gridBaseAdapter = new GridBaseAdapter(getApplicationContext(), imageModelArrayList);
        gvGallery.setAdapter(gridBaseAdapter);

        gvGallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Toast.makeText(MainActivityCustomers.this, myImageNameList[position] + " Clicked", Toast.LENGTH_SHORT).show();

                if (position == 0) {
                    startActivity(new Intent(MainActivityCustomers.this, HandyManMapsActivity.class));
                    //  finish();
                }
                if (position == 1) {
                    startActivity(new Intent(MainActivityCustomers.this, GardenerListActivity.class));
                    //   finish();
                }
                if (position == 2) {
                    startActivity(new Intent(MainActivityCustomers.this, PlumberListActivity.class));
                    // finish();
                }
                if (position == 3) {
                    startActivity(new Intent(MainActivityCustomers.this, RollerListActivity.class));
                    //  finish();
                }
                if (position == 4) {
                    startActivity(new Intent(MainActivityCustomers.this, PestControlListActivity.class));
                //  finish();
            }
                if (position == 5) {
                    startActivity(new Intent(MainActivityCustomers.this, TilerListActivity.class));
                    //  finish();
                }
                if (position == 6) {
                    startActivity(new Intent(MainActivityCustomers.this, TvInstallerActivity.class));
                    //  finish();
                }

                if (position == 7) {
                    startActivity(new Intent(MainActivityCustomers.this, CarpenterActivity.class));
                    //  finish();
                }
                if (position == 8) {
                    startActivity(new Intent(MainActivityCustomers.this, PainterActivity.class));
                    //  finish();
                }


            }
        });


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        View navigationHeader = navigationView.getHeaderView(0);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        FullName = navigationHeader.findViewById(R.id.txtfullname);
        Email = navigationHeader.findViewById(R.id.txtemail);
        userImage = navigationHeader.findViewById(R.id.userprofileimage);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            startActivity(new Intent(this, MainActivityCustomers.class));
            // Handle the camera action
        } else if (id == R.id.nav_profile) {
            startActivity(new Intent(this, EditProfile.class));

        }  else if (id == R.id.nav_logout) {
            AlertDialog.Builder a_builder = new AlertDialog.Builder(MainActivityCustomers.this);
            a_builder.setMessage("Do you really want to Logout")
                    .setCancelable(true)
                    .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                            try {
                                if (firebaseUser != null) {
                                    mAuth.signOut();
                                    Intent Login = new Intent(MainActivityCustomers.this, CustomerLogin.class);
                                    Login.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(Login);
                                    finish();

                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                    }).setNegativeButton("No ", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });

            AlertDialog alert = a_builder.create();
            alert.setTitle("Alert!!!");
            alert.show();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    protected void onStart() {
        super.onStart();
        try {
            assert firebaseUser != null;

            if (mAuth.getCurrentUser() == null || !firebaseUser.isEmailVerified()) {
                SendUserToLoginActivity();
            } else {
                Log.d(TAG, "onStart: successful");
                retrieveUserDetails();

            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void retrieveUserDetails() {

        UserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {
                    if (dataSnapshot.exists()) {
                        Log.d(TAG, "onDataChange: user profile exists");
                        //retrieve the details and set the on the users profile
                        String Fullname = (String) dataSnapshot.child("fullName").getValue();
                        String email = (String) dataSnapshot.child("email").getValue();
                        String showImage = (String) dataSnapshot.child("thumbImage").getValue();
                        FullName.setText(Fullname);
                        Email.setText(email);
                        Glide.with(getApplicationContext()).load(showImage).into(userImage);


                    } else {
                        Log.d(TAG, "No details: a default photo has be replaced");
                        Glide.with(getApplicationContext()).load(R.drawable.defaultavatar).into(userImage);
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

    private void SendUserToLoginActivity() {
        Intent Login = new Intent(MainActivityCustomers.this, SplashScreenActivity.class);
        startActivity(Login);
        finish();
    }

    private ArrayList<ImageModel> populateList() {

        ArrayList<ImageModel> list = new ArrayList<>();

        for (int i = 0; i < 9; i++) {
            ImageModel imageModel = new ImageModel();
            imageModel.setName(myImageNameList[i]);
            imageModel.setImage_drawable(myImageList[i]);
            list.add(imageModel);
        }

        return list;
    }

}
