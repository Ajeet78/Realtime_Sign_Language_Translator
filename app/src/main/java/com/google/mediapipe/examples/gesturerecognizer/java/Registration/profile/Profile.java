package com.google.mediapipe.examples.gesturerecognizer.java.Registration.profile;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.mediapipe.examples.gesturerecognizer.R;
import com.google.mediapipe.examples.gesturerecognizer.java.Registration.SignIn;
import com.google.mediapipe.examples.gesturerecognizer.java.Registration.firebasedatabase.ReadWriteUserDetails;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class Profile extends AppCompatActivity {

    private TextView textVieWelcome, textViewFullName, textViewEmail, textViewDoB, textViewGender, textViewMobile;

    //Progress bar
    private ProgressBar progressBar;

    private String fullname, email, doB, gender, mobile;
    //profile image
    private CircleImageView imageView;
    //Firebase authentication
    private FirebaseAuth authProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        textVieWelcome = findViewById(R.id.textView_show_welcome);
        textViewFullName = findViewById(R.id.textView_show_full_name);
        textViewEmail = findViewById(R.id.textView_show_email);
        textViewDoB = findViewById(R.id.textView_show_dob);
        textViewGender = findViewById(R.id.textView_show_gender);
        textViewMobile = findViewById(R.id.textView_show_mobile);
        progressBar = findViewById(R.id.progress_bar);


        //Set onClickListener on ImageView to open UploadProfileActivity
        imageView = findViewById(R.id.imageView_profile_dp);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //open upload profile activity
                Intent intent = new Intent(Profile.this, UploadProfilePicActivity.class);
                startActivity(intent);
            }
        });
        authProfile = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = authProfile.getCurrentUser();

        if (firebaseUser == null) {
            Toast.makeText(Profile.this, "Something went wrong! Users details are not available at the moment", Toast.LENGTH_LONG).show();

            //check email verification
        }
//        else {
//            checkIfEmailVerified(firebaseUser);
        progressBar.setVisibility((View.VISIBLE));
        showUserProfile(firebaseUser);
//        }
    }

    //User coming to Profile after successful registration
//    private void checkIfEmailVerified(FirebaseUser firebaseUser) {
//        if (!firebaseUser.isEmailVerified()){
//            showAlertDialog();
//        }
//    }
//    private void showAlertDialog() {
//        //Setup the Alert Builder
//        AlertDialog.Builder builder = new AlertDialog.Builder(Profile.this);
//        builder.setTitle("Email Not Verified!");
//        builder.setMessage("Please verify your email now. You can't login without email verification next time.");
//
//        //Open email app if user click/taps Continue button
//        builder.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                Intent intent  = new Intent(Intent.ACTION_MAIN);
//                intent.addCategory(Intent.CATEGORY_APP_EMAIL);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);   //To email app in new window and not within our app
//                startActivity(intent);
//            }
//        });
//
//        //Create the AlertDialog
//        AlertDialog alertDialog = builder.create();
//
//        //Show the alertdialog
//        alertDialog.show();
//
//    }

    private void showUserProfile(FirebaseUser firebaseUser) {
        String userId = firebaseUser.getUid();

        //Extracting User Reference from Database for “Registered Users”
        DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("Registered Users");
        referenceProfile.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                //Get data from ReadWriteUserDetails class
                ReadWriteUserDetails readUserDetails = snapshot.getValue(ReadWriteUserDetails.class);

                if (readUserDetails != null) {
                    fullname = readUserDetails.fullname;
                    email = firebaseUser.getEmail();
                    doB = readUserDetails.doB;
                    gender = readUserDetails.gender;
                    mobile = readUserDetails.mobile;

                    //set text display on Your dashboard
                    textVieWelcome.setText("Welcome " + fullname + "!");
                    textViewFullName.setText(fullname);
                    textViewEmail.setText(email);
                    textViewDoB.setText(doB);
                    textViewGender.setText(gender);
                    textViewMobile.setText(mobile);


                    //Set User DP(After user has uploaded)
                    Uri uri = firebaseUser.getPhotoUrl();

                    //ImageViewer setImageURI() should not be used regular URIs. So we are picasso
                    Picasso.get().load(uri).into(imageView);
                } else {
                    Toast.makeText(Profile.this, "Something went wrong!", Toast.LENGTH_LONG).show();

                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(Profile.this, "Something went wrong!", Toast.LENGTH_LONG).show();
                //progressbar
                progressBar.setVisibility(View.GONE);
            }
        });

    }

    //Creating ActionBar Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        //Inflate menu items
        getMenuInflater().inflate(R.menu.common_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //When any menu item is selected
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_refresh) {
            //Refresh Activity
            startActivity(getIntent());
            finish();
            overridePendingTransition(0, 0);
        } else if (id == R.id.menu_update_profile) {
            Intent intent = new Intent(Profile.this, UpdateProfileActivity.class);
            startActivity(intent);
            finish();

//        } else if (id == R.id.menu_update_email) {
//            Intent intent = new Intent(Profile.this, UpdateProfileActivity.class);
//            startActivity(intent);
//
//        } else if (id == R.id.menu_setting) {
//            Toast.makeText(Profile.this,"menu settings",Toast.LENGTH_SHORT);

        } else if (id == R.id.menu_change_password) {
            Intent intent = new Intent(Profile.this, ChangePasswordActivity.class);
            startActivity(intent);

//        } else if (id == R.id.menu_delete_profile) {
//            Intent intent = new Intent(Profile.this, DeleteProfileActivity.class);
//            startActivity(intent);
//
        } else if (id == R.id.menu_logout) {
            authProfile.signOut();
            Toast.makeText(Profile.this, "Logged Out", Toast.LENGTH_LONG);
            Intent intent = new Intent(Profile.this, SignIn.class);

            //Clear stack to prevent user coming back to Profile on pressing back button after Logged out
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish(); //Close Profile
        } else {
            Toast.makeText(Profile.this, "Something went wrong!", Toast.LENGTH_LONG);

        }
        return super.onOptionsItemSelected(item);
    }
}
