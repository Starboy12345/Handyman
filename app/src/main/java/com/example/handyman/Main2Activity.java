package com.example.handyman;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class Main2Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FirebaseAuth mAuth;
    FirebaseUser firebaseUser;
    private DatabaseReference UserRef;
    private static final String TAG = "MainActivity";
    private TextView FullName,Email;
    private CircleImageView userImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = findViewById(R.id.toolbar);

        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();
        if (mAuth.getCurrentUser() == null)
        {
            return;
        }
        String userId = firebaseUser.getUid();
        UserRef = FirebaseDatabase.getInstance().getReference().child("Users").child(userId);
        UserRef.keepSynced(true);

        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        FullName = navigationView.findViewById(R.id.txtfullname);
        Email = navigationView.findViewById(R.id.txtemail);
        userImage = navigationView.findViewById(R.id.userprofileimage);
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
            startActivity(new Intent(this, Main2Activity.class));
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {
            startActivity(new Intent(this, Handymantypes.class));

        } else if (id == R.id.nav_slideshow) {
            startActivity(new Intent(this, EditProfile.class));

        } else if (id == R.id.nav_tools) {

        } else if (id == R.id.nav_share) {


        } else if (id==R.id.nav_logout)
        {
            AlertDialog.Builder a_builder = new AlertDialog.Builder(Main2Activity.this);
            a_builder.setMessage("Do you really want to Logout")
                    .setCancelable(true)
                    .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                            try{
                                if (firebaseUser!=null)
                                {
                                    mAuth.signOut();
                                    Intent Login = new Intent(Main2Activity.this, WelcomeActivity.class);
                                    Login.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(Login);
                                    finish();

                                }
                            }
                            catch( Exception e){
                                e.printStackTrace();
                            }

                        }
                    }).setNegativeButton("No ", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });

            AlertDialog alert= a_builder.create();
            alert.setTitle("Alert!!!");
            alert.show();
        } else if (id == R.id.nav_send) {

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

        }catch (Exception e){
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
                    }}
                catch(Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                try {
                    Log.d(TAG, "Error : " + databaseError.getMessage());
                }
                catch (Exception e )
                { e.printStackTrace();}
            }
        });

    }

    private void SendUserToLoginActivity() {
        Intent Login = new Intent(Main2Activity.this, WelcomeActivity.class);
        startActivity(Login);
        finish();
    }

}
