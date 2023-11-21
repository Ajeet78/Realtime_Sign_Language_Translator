package com.example.realtimelanguagetranslator.Registration;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.realtimelanguagetranslator.Home;
import com.example.realtimelanguagetranslator.R;
import com.example.realtimelanguagetranslator.profile.Profile;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignIn extends AppCompatActivity {

    private EditText email, password;
    private TextView openForgetPass, SignUpActivity;

    //firebase
    private FirebaseAuth firebaseAuth;
    private Button login;

    //progress dialogbar
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        login = findViewById(R.id.loginbtn);

        //Forget password
        openForgetPass = findViewById(R.id.openForgetPass);

        //Sign Up Activity
        SignUpActivity  = findViewById(R.id.registerUser);

        firebaseAuth = FirebaseAuth.getInstance();

        //progress dialog
        pd = new ProgressDialog(this);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (email.getText().toString().isEmpty()) {
                    email.setError("Please enter email ID");
                    return;
                } else {
                    email.setError(null);
                }
                if (email.getText().toString().isEmpty()) {
                    email.setError("Please enter email ID");
                    return;
                } else {
                    email.setError(null);
                }

                if (password.getText().toString().isEmpty()) {
                    password.setError("Please enter password");
                    return;
                } else {
                    password.setError(null);
                }

                firebaseLogin();
            }
        });

        //open Forget password activity
        openForgetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignIn.this, ForgetPasswordActivity.class));
            }
        });

        //open create account activity
        SignUpActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignIn.this, SignUp.class));
            }
        });
    }

    private void firebaseLogin() {

        //progress dialogbar
        pd.setMessage("Please wait....");
        pd.show();

        firebaseAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            //progress dialogbar
                            pd.dismiss();

                            // Sign in success, update UI with the signed-in user's information
                            Intent intent = new Intent(SignIn.this, Profile.class);
                            startActivity(intent);
                            finish();
                        } else {

                            //progress dialogbar
                            pd.dismiss();

                            // If sign in fails, display a message to the user.
                            Toast.makeText(SignIn.this, "Invalid Email ID or Password!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    //start line
    //check if user already logged in then redirect home activity
    @Override
    protected void onStart() {

        super.onStart();
        if (firebaseAuth.getCurrentUser() != null) {

            startActivity(new Intent(SignIn.this, Home.class));
            finish();
        }
    }
    //end line
}





