package com.google.mediapipe.examples.gesturerecognizer.java.Registration;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.mediapipe.examples.gesturerecognizer.R;
import com.google.mediapipe.examples.gesturerecognizer.java.Registration.profile.Profile;

public class ForgetPasswordActivity extends AppCompatActivity {

    private Button forgetBtn;
    private EditText txtEmail;
    private String email;
    private FirebaseAuth auth;

    private final static String TAG = "ForgetPasswordActivity";

    //progress dialogbar
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);


        auth = FirebaseAuth.getInstance();

        txtEmail = findViewById(R.id.email);
        forgetBtn = findViewById(R.id.resetpasswordbtn);

        //progress dialog
        pd = new ProgressDialog(this);


        forgetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateData();
            }
        });
    }

    private void validateData() {
        email = txtEmail.getText().toString();
        if (email.isEmpty()) {
            txtEmail.setError("Required");

        } else {
            forgetPass();
        }
    }

    private void forgetPass() {

        //progress dialogbar
        pd.setMessage("Please wait....");
        pd.show();

        auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                //Success
                if (task.isSuccessful()) {

                    //progress dialogbar
                    pd.dismiss();

                    Toast.makeText(ForgetPasswordActivity.this, "Please Check  your inbox for password reset link", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(ForgetPasswordActivity.this, Profile.class);
                    //Clear stack to prevent user coming back to ForgetPasswordActivity
                    intent.setFlags((Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
                    startActivity(intent);
                    finish(); //Close ForgetPasswordActivity
                    // startActivity(new Intent(ForgetPasswordActivity.this, LoginActivity.class));

                    //worng email
                } else {
                    try {
                        throw task.getException();
                    } catch (FirebaseAuthInvalidUserException e) {
                        txtEmail.setError("User does not exists or is no longer valid, please registration again!");
                        txtEmail.requestFocus();
                    } catch (FirebaseAuthInvalidCredentialsException e) {
                        txtEmail.setError("Invalid credentials. Kindly, check and re-enter.");
                        txtEmail.requestFocus();
                    } catch (Exception e) {
                        Log.e(TAG, e.getMessage());
                        Toast.makeText(ForgetPasswordActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    //progress dialogbar
                    pd.dismiss();

                    Toast.makeText(ForgetPasswordActivity.this, "Error:" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}