package com.example.handyman.activities.customeractivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.handyman.R;
import com.example.handyman.models.User;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;
import id.zelory.compressor.Compressor;

public class CustomerRegister extends AppCompatActivity {

    private TextInputLayout UserFullname, UserEmail, UserPassword, UserVPassword, UserNumber;
    private Button createaccountbutton;
    private FirebaseAuth mAuth;
    FirebaseUser firebaseUser;
    private Uri resultUri;
    private String getImageUri = "";
    private StorageReference mStorageReferenceForPhoto;
    private final String urlShortNer = "https://bit.ly/2I6aStr";
    private ProgressDialog loadingbar;
    private DatabaseReference UserRef;
    String CurrentUserid, email, password,confirmpassword,number,occupation, fullname;
    private static final String TAG = "Register";
    private Vibrator vibrator;
    CircleImageView imgProfile;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_register);


        mAuth = FirebaseAuth.getInstance();
//vibration
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);

        UserRef = FirebaseDatabase.getInstance().getReference("Users");
        //creates a storage like data for check in photos to be stored into
        mStorageReferenceForPhoto = FirebaseStorage.getInstance().getReference().child(
                "userPhotos");
       UserFullname=findViewById(R.id.TxtFullName);
        UserNumber = findViewById(R.id.TxtMobileNumberSignup);
        UserEmail = findViewById(R.id.TxtEmailsignup);
        UserPassword = findViewById(R.id.TxtPasswordsignup);
        UserVPassword = findViewById(R.id.TxtConfirmPasswordSignup);
        createaccountbutton = findViewById(R.id.btnregisterSignup);
        loadingbar = new ProgressDialog(this);
        imgProfile = findViewById(R.id.imgProfile);


        createaccountbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (resultUri != null) {

                    createnewaccount();
                } else {

                    new AlertDialog.Builder(CustomerRegister.this)
                            .setTitle("Warning")
                            .setMessage("Must add a photo ")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).create().show();

                }

            }
        });
    }

    private void createnewaccount() {
       fullname = UserFullname.getEditText().getText().toString();
        email = UserEmail.getEditText().getText().toString();
        password = UserPassword.getEditText().getText().toString();
        confirmpassword = UserVPassword.getEditText().getText().toString();
        number = UserNumber.getEditText().getText().toString();

        if (TextUtils.isEmpty(fullname)) {
            Toast.makeText(this, "pls enter First Name", Toast.LENGTH_LONG).show();
        }


        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "pls enter valid Email", Toast.LENGTH_LONG).show();
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "pls enter Password", Toast.LENGTH_LONG).show();
        }

        if (TextUtils.isEmpty(confirmpassword)) {
            Toast.makeText(this, "pls Confirm Password", Toast.LENGTH_LONG).show();
        }

        if (TextUtils.isEmpty(number)) {
            Toast.makeText(this, "pls enter Phone number", Toast.LENGTH_LONG).show();
        }





        if (!TextUtils.isEmpty(fullname) && !TextUtils.isEmpty(email)
                && !TextUtils.isEmpty(number) && !TextUtils.isEmpty(password)
                && !TextUtils.isEmpty(confirmpassword))
        {
            if (password.equals(confirmpassword) && password.length()<8  )
            {
                Toast.makeText(this, "pls enter a password with at least 8 characters ", Toast.LENGTH_LONG).show();
            }


            if (!TextUtils.isEmpty(number) && number.length()<10 ) {
                Toast.makeText(this, "Please ,phone number field only receives 10 digits", Toast.LENGTH_LONG).show();
            }
            if (!TextUtils.isEmpty(number) && number.length()>10 ) {
                Toast.makeText(this, "Please ,phone number field only receives 10 digits", Toast.LENGTH_LONG).show();
            }

            if (password.equals(confirmpassword) && password.length()>=8 && number.length()==10 )
            {

                loadingbar.setMessage("Please wait while we  create your new account");
                loadingbar.show();
                loadingbar.setCancelable(false);

                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {

                            firebaseUser = mAuth.getCurrentUser();
                            CurrentUserid = firebaseUser.getUid();


                            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful())
                                    {

                                        //thumb file
                                        final File thumb_imageFile = new File(resultUri.getPath());

                                        //  compress image file to bitmap surrounding with try catch
                                        byte[] thumbBytes = new byte[0];
                                        try {
                                            Bitmap thumb_imageBitmap =
                                                    new Compressor(CustomerRegister.this)
                                                            .setQuality(100)
                                                            .compressToBitmap(thumb_imageFile);

                                            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                                            thumb_imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                                            thumbBytes = byteArrayOutputStream.toByteArray();

                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }

                                        //                file path for the image
                                        final StorageReference fileReference =
                                                mStorageReferenceForPhoto.child(CurrentUserid + "." + resultUri.getLastPathSegment());
                                        fileReference.putFile(resultUri).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                                            @Override
                                            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                                                if (!task.isSuccessful()) {
                                                    //throw task.getException();
                                                    Log.d(TAG, "then: " + task.getException().getMessage());

                                                }
                                                return fileReference.getDownloadUrl();
                                            }

                                        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Uri> task) {

                                                if (task.isSuccessful()){
                                                    Uri downLoadUri = task.getResult();
                                                    assert downLoadUri != null;
                                                    getImageUri = downLoadUri.toString();

//class created within the files
                                                    final User user = new User(CurrentUserid, email, number, fullname,occupation,getImageUri);
                                                    UserRef.child(CurrentUserid).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {

                                                            if (task.isSuccessful())
                                                            {
                                                                loadingbar.dismiss();
                                                                //vibrates to alert success for android M and above
                                                                if (Build.VERSION.SDK_INT >= 26) {
                                                                    vibrator.vibrate(VibrationEffect.createOneShot
                                                                            (2000, VibrationEffect.DEFAULT_AMPLITUDE));
                                                                } else {
                                                                    //vibrate below android M
                                                                    vibrator.vibrate(2000);

                                                                }
                                                                new AlertDialog.Builder(CustomerRegister.this)
                                                                        .setMessage("Hello" + " " + fullname + " " + "\n" + "an email verification link has been sent to " + email + "\n" +
                                                                                "please verify to continue")
                                                                        .setPositiveButton("OK",
                                                                                new DialogInterface.OnClickListener
                                                                                        () {
                                                                                    @Override
                                                                                    public void onClick(DialogInterface dialog, int which) {
//
                                                                                        dialog.dismiss();

                                                                                        startActivity(new Intent(CustomerRegister.this, CustomerLogin.class));
                                                                                        finish();


                                                                                    }
                                                                                })
                                                                        .create()
                                                                        .show();

                                                            } else {
                                                                loadingbar.dismiss();
                                                                Log.d(TAG, "onComplete: " + task.getException().getMessage());
                                                            }

                                                        }
                                                    });



                                                }








                                            }
                                        });








                                    } else {
                                        Toast toast = Toast.makeText(CustomerRegister.this, " Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT);
                                        toast.setGravity(Gravity.CENTER, 0, 0);
                                        toast.show();

                                    }
                                }
                            });


                        } else
                        {
                            String message = task.getException().getMessage();
                            Toast.makeText(CustomerRegister.this, "error occurred" + message, Toast.LENGTH_SHORT).show();
                            loadingbar.dismiss();
                        }


                    }
                });

            } else {
                Toast toast = Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();}


        }



    }

    public void selectPhoto(View view) {

openGallery();

    }


    private void openGallery() {
        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .start(CustomerRegister.this);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            if (resultCode == RESULT_OK) {
                assert result != null;
                resultUri = result.getUri();
                //  userImage.setImageURI(uri);

                Glide.with(CustomerRegister.this).load(resultUri).into(imgProfile);

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                loadingbar.dismiss();
                assert result != null;
                String error = result.getError().getMessage();
                Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
            }
        }


    }

}
