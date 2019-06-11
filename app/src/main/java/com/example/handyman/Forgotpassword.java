package com.example.handyman;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.handyman.activities.WelcomeActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class Forgotpassword extends AppCompatActivity {
    private Button Resetpasswordbutton;
    private TextInputLayout ResetEmail;
    private FirebaseAuth mAuth;
    private ProgressDialog loadingbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpassword);
        mAuth= FirebaseAuth.getInstance();
        loadingbar=new ProgressDialog(this);



        Resetpasswordbutton= findViewById(R.id.btnRecoverPassword);
        ResetEmail=findViewById(R.id.TxtResetPasswordEmail);

        Resetpasswordbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String Useremail= ResetEmail.getEditText().getText().toString();

                if ((TextUtils.isEmpty(Useremail)))
                {
                    Toast.makeText(Forgotpassword.this, "Please Enter Email .", Toast.LENGTH_SHORT).show();
                }else
                {
                    mAuth.sendPasswordResetEmail(Useremail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task)
                        {
                            if (task.isSuccessful())
                            {
                                Toast.makeText(Forgotpassword.this, "Please Check Your Email address", Toast.LENGTH_SHORT).show();

      startActivity(new Intent(Forgotpassword.this, WelcomeActivity.class));



                            }
                            else
                            {
                                String message=task.getException().getMessage();
                                Toast.makeText(Forgotpassword.this,"Error:"+""+message,Toast.LENGTH_SHORT).show();
                                loadingbar.dismiss();
                            }
                        }
                    });

                }

            }
        });

    }
}
