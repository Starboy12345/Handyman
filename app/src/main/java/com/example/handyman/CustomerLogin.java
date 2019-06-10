package com.example.handyman;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class CustomerLogin extends AppCompatActivity {

    private TextInputLayout UserEmail, UserPassword;
    private Button LoginButton;
    private TextView Needaccount, forgotpassword;
    private FirebaseAuth mAuth;
    FirebaseUser firebaseUser;
    private ProgressDialog loadingbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();

        UserEmail = findViewById(R.id.TxtLoginEmail);
        UserPassword = findViewById(R.id.TxtLoginPassword);
        LoginButton = findViewById(R.id.btnlogin);
        Needaccount = findViewById(R.id.Txtregisterlogin);
       // forgotpassword = findViewById(R.id.Txtforgotpassword);
        loadingbar = new ProgressDialog(this);

        Needaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendUsertoRegisterActivity();


            }
        });

        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AllowUsertoLogin();
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences preferences = getSharedPreferences("loginEmail", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("email", UserEmail.getEditText().getText().toString());
        editor.apply();
    }

    @Override
    protected void onResume() {
        super.onResume();
        super.onResume();
        SharedPreferences sharedPreferences = getSharedPreferences("loginEmail", MODE_PRIVATE);
        String email = sharedPreferences.getString("email", "");
        UserEmail.getEditText().setText(email);
    }

    private void AllowUsertoLogin() {
        final String email = UserEmail.getEditText().getText().toString();
        String password = UserPassword.getEditText().getText().toString();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please enter your email", Toast.LENGTH_SHORT).show();
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please enter your password", Toast.LENGTH_SHORT).show();
        }
        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
            loadingbar.setTitle("");
            loadingbar.setMessage("Please wait");
            loadingbar.show();
            loadingbar.setCancelable(false);

            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task)
                        {
                            if (task.isSuccessful())
                            {
                                if (mAuth.getCurrentUser().isEmailVerified()){
                                    startActivity(new Intent(CustomerLogin.this, MainActivityCustomers.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK));
                                    finish();
                                    loadingbar.dismiss();
                                }else{
                                    new AlertDialog.Builder(CustomerLogin.this)
                                            .setMessage("Hello" + " " + email + " "
                                                    + " " + "\n" + "please check your email  " + "\n" +
                                                    "to continue")
                                            .setPositiveButton("OK",
                                                    new DialogInterface.OnClickListener
                                                            () {
                                                        @Override
                                                        public void onClick(DialogInterface dialog, int which)
                                                        { dialog.dismiss(); }
                                                    })
                                            .create()
                                            .show();
                                }


                            } else
                            {
                                String message = task.getException().getMessage();
                                Toast.makeText(CustomerLogin.this, "Error:" + "" + message, Toast.LENGTH_SHORT).show();
                                loadingbar.dismiss();
                            }
                        }
                    });

        }
    }


    private void SendUsertoRegisterActivity()
    {
        // send user to register activity
        Intent registeruser = new Intent(CustomerLogin.this, CustomerRegister.class);
        startActivity(registeruser);
    }
}
