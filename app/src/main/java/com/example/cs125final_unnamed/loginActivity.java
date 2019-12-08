package com.example.cs125final_unnamed;

import android.annotation.SuppressLint;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class loginActivity extends AppCompatActivity {
    /**
     * Some older devices needs a small delay between UI widget updates
     * and a change of the status and navigation bar.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        Button loginButton = findViewById(R.id.loginButton);

        loginButton.setOnClickListener(v -> {
            //there'll be a google login button
            Intent newLogin = new Intent(this, menuActivity.class);
            startActivity(newLogin);
        });
    }
}
