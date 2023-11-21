package com.example.realtimelanguagetranslator.Registration;

import static android.content.ContentValues.TAG;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.realtimelanguagetranslator.R;
import com.example.realtimelanguagetranslator.firebasedatabase.ReadWriteUserDetails;
import com.example.realtimelanguagetranslator.profile.Profile;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUp extends AppCompatActivity {

    private EditText editTextRegisterFullName, editTextRegisterEmail, editTextRegisterDoB, editTextRegisterPwd, editTextRegisterConfirmPwd, editTextRegisterMobile;
    private ProgressBar progressBar;
    private RadioGroup radioGroupRegisterGender;
    private RadioButton radioButtonRegisterGenderSelected;
    //DatePicker dialog
    private DatePickerDialog picker;
    private FirebaseAuth mAuth;

    //progress dialog
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        //find id all string
        progressBar = findViewById(R.id.progressBar);

        editTextRegisterFullName = findViewById(R.id.editText_register_fullname);
        editTextRegisterEmail = findViewById(R.id.editText_register_email);
        editTextRegisterDoB = findViewById(R.id.editText_register_dob);
        editTextRegisterPwd = findViewById(R.id.editText_register_pasword);
        editTextRegisterConfirmPwd = findViewById(R.id.editText_register_confirmPassword);
        editTextRegisterMobile = findViewById(R.id.editText_register_mobile);

        //RadioButton for textGender
        radioGroupRegisterGender = findViewById(R.id.radio_group_register_gender);
        radioGroupRegisterGender.clearCheck();

        //Setting up DatePicker on EditText
        editTextRegisterDoB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calender = Calendar.getInstance();
                int day = calender.get(Calendar.DAY_OF_MONTH);
                int month = calender.get(Calendar.MONTH);
                int year = calender.get(Calendar.YEAR);

                //Date picker dialog
                picker = new DatePickerDialog(SignUp.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        editTextRegisterDoB.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                }, year, month, day);
                picker.show();
            }
        });


        //progress dialogbar
        progressBar = new ProgressBar(this);

        //extra addd
        Button buttonRegister = findViewById(R.id.registerUser);
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int selectGenderId = radioGroupRegisterGender.getCheckedRadioButtonId();
                radioButtonRegisterGenderSelected = findViewById(selectGenderId);

                //obtain the entered data
                String textFullName = editTextRegisterFullName.getText().toString();
                String textEmail = editTextRegisterEmail.getText().toString();
                String textPwd = editTextRegisterPwd.getText().toString();
                String textConfirmPwd = editTextRegisterConfirmPwd.getText().toString();
                String textDoB = editTextRegisterDoB.getText().toString();
                String textMobile = editTextRegisterMobile.getText().toString();
                String textGender; //Can't obtain the value before verifying if any button was selected or not

                //validate mobile number using matcher and pattern (Regular Expression)
                String mobileRegex = "[6-9][0-9]{9}";  //First no can be(6,8,9) and rest 9 numbers,can be any number
                Matcher mobileMatcher;
                Pattern mobilePattern = Pattern.compile(mobileRegex);
                mobileMatcher = mobilePattern.matcher(textMobile);


                if (TextUtils.isEmpty(textFullName)) {
                    Toast.makeText(SignUp.this, "Please enter your full name.", Toast.LENGTH_SHORT).show();

                    editTextRegisterFullName.setError("Full Name is required");
                    editTextRegisterFullName.requestFocus();
                } else if (TextUtils.isEmpty(textEmail)) {
                    Toast.makeText(SignUp.this, "Please enter Email.", Toast.LENGTH_SHORT).show();

                    editTextRegisterEmail.setError("textEmail is required");
                    editTextRegisterEmail.requestFocus();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(textEmail).matches()) {
                    Toast.makeText(SignUp.this, "Please re-enter Email.", Toast.LENGTH_SHORT).show();

                    editTextRegisterEmail.setError("Valid textEmail is required");
                    editTextRegisterEmail.requestFocus();


                } else if (TextUtils.isEmpty(textEmail)) {
                    Toast.makeText(SignUp.this, "Please enter Email.", Toast.LENGTH_SHORT).show();

                    editTextRegisterEmail.setError("textEmail is required");
                    editTextRegisterEmail.requestFocus();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(textEmail).matches()) {
                    Toast.makeText(SignUp.this, "Please re-enter Email.", Toast.LENGTH_SHORT).show();

                    editTextRegisterEmail.setError("Valid textEmail is required");
                    editTextRegisterEmail.requestFocus();

                } else if (TextUtils.isEmpty(textDoB)) {
                    Toast.makeText(SignUp.this, "Please enter your date of birth.", Toast.LENGTH_SHORT).show();
                    editTextRegisterDoB.setError("Date of Birth is required");
                    editTextRegisterDoB.requestFocus();

                } else if (radioGroupRegisterGender.getCheckedRadioButtonId() == -1) {

                    Toast.makeText(SignUp.this, "Please select your textGender", Toast.LENGTH_SHORT).show();
                    radioButtonRegisterGenderSelected.setError("textGender is required");
                    radioButtonRegisterGenderSelected.requestFocus();

                } else if (TextUtils.isEmpty(textMobile)) {
                    Toast.makeText(SignUp.this, "Please enter mobile no.", Toast.LENGTH_SHORT).show();
                    editTextRegisterMobile.setError("Mobile No. is required");
                    editTextRegisterMobile.requestFocus();

                } else if (textMobile.length() != 10) {
                    Toast.makeText(SignUp.this, "Please re-enter mobile no.", Toast.LENGTH_SHORT).show();
                    editTextRegisterMobile.setError("Mobile No. should be 10 digits");
                    editTextRegisterMobile.requestFocus();

                } else if (!mobileMatcher.find()) {
                    Toast.makeText(SignUp.this, "Please re-enter mobile no.", Toast.LENGTH_SHORT).show();
                    editTextRegisterMobile.setError("Mobile no is not valid");
                    editTextRegisterMobile.requestFocus();


                } else if (TextUtils.isEmpty(textPwd)) {
                    Toast.makeText(SignUp.this, "Please enter password", Toast.LENGTH_SHORT).show();
                    editTextRegisterPwd.setError("textPwd is required");
                    editTextRegisterPwd.requestFocus();
                } else if (TextUtils.isEmpty(textPwd)) {
                    Toast.makeText(SignUp.this, "Please enter password", Toast.LENGTH_SHORT).show();
                    editTextRegisterPwd.setError("textPwd is required");
                    editTextRegisterPwd.requestFocus();

                } else if (textPwd.length() < 6) {
                    Toast.makeText(SignUp.this, "textPwd should be at least 6 digits", Toast.LENGTH_SHORT).show();

                    editTextRegisterPwd.setError("textPwd too weak");
                    editTextRegisterPwd.requestFocus();

                } else if (TextUtils.isEmpty(textConfirmPwd)) {
                    Toast.makeText(SignUp.this, "Please confirm your password", Toast.LENGTH_SHORT).show();
                    editTextRegisterConfirmPwd.setError("textPwd confirmation is required");
                    editTextRegisterConfirmPwd.requestFocus();

                    //clear the entered textPwd
                    editTextRegisterPwd.clearComposingText();
                    editTextRegisterConfirmPwd.clearComposingText();

                    //progress dialogbar
                } else {
                    //textGender
                    textGender = radioButtonRegisterGenderSelected.getText().toString();
                    // progressbar
                    progressBar.setVisibility(View.VISIBLE);

                    registerUser(textFullName, textEmail, textDoB, textGender, textConfirmPwd, textMobile);
                }
            }
        });
    }

    //Register user using  the credentials given
    private void registerUser(String textFullName, String textEmail, String textDoB, String textGender, String textConfirmPwd, String textMobile) {

        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.createUserWithEmailAndPassword(textEmail, textConfirmPwd).addOnCompleteListener(SignUp.this,
                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(SignUp.this, "User Registrated Successfully", Toast.LENGTH_SHORT).show();
                            FirebaseUser firebaseUser = auth.getCurrentUser();

                            ReadWriteUserDetails writeUserDetails = new ReadWriteUserDetails(textFullName, textDoB, textGender, textMobile);

                            //Extracting user refrence from Database for "Register User"(save data in firebase database)
                            DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("Registered Users");
                            referenceProfile.child(firebaseUser.getUid()).setValue(writeUserDetails).addOnCompleteListener(new OnCompleteListener<Void>() {

                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if (task.isSuccessful()) {

                                        //send verification Email
                                        firebaseUser.sendEmailVerification();

                                        Toast.makeText(SignUp.this, "User Registrated Successfully", Toast.LENGTH_SHORT).show();

                                        //Open user profile after succesfull SignUp
                                        Intent intent = new Intent(SignUp.this, Profile.class);
                                        startActivity(intent);
                                        finish(); // to close register activity

                                    } else {

                                        Toast.makeText(SignUp.this, "Registered failed! Please try again!", Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });

                        } else {
                            try {
                                throw task.getException();
                            } catch (FirebaseAuthWeakPasswordException e) {
                                editTextRegisterPwd.setError("Your password too weak. Kindly use a mix of alphabets, numbers and special characters");
                                editTextRegisterPwd.requestFocus();
                            } catch (FirebaseAuthInvalidCredentialsException e) {
                                editTextRegisterEmail.setError("Your Email is invalid or already in use. Kindly re-enter");
                                editTextRegisterEmail.requestFocus();
                            } catch (FirebaseAuthUserCollisionException e) {
                                editTextRegisterEmail.setError("Your User is already register with this Email. Use Another Email");
                                editTextRegisterEmail.requestFocus();
                            } catch (Exception e) {
                                Log.e(TAG, e.getMessage());
                                Toast.makeText(SignUp.this, e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
    }


}




