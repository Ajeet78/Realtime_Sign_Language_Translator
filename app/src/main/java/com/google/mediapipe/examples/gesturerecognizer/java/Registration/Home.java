package com.google.mediapipe.examples.gesturerecognizer.java.Registration;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.mediapipe.examples.gesturerecognizer.java.Registration.profile.Profile;
import com.google.mediapipe.examples.gesturerecognizer.kotlin.MainActivity;
import com.google.mediapipe.examples.gesturerecognizer.R;


public class Home extends AppCompatActivity implements View.OnClickListener {

    CardView profile;
    CardView sign_language;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        profile = (CardView) findViewById(R.id.profile);
        profile.setOnClickListener(this);

        sign_language = (CardView) findViewById(R.id.view_sign_language);
        sign_language.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.profile) {
            Intent intent = new Intent(Home.this, Profile.class);
            startActivity(intent);
        }
        if (view.getId() == R.id.view_sign_language) {
            startActivity(new Intent(Home.this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
        }
    }
}
