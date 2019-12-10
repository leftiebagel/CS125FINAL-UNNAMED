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
import android.widget.TextView;
import android.widget.LinearLayout;
import java.util.Arrays;
import java.util.List;

import com.google.firebase.auth.FirebaseAuth;
import com.firebase.ui.auth.AuthUI;




public class loginActivity extends AppCompatActivity {

    private final int RCSign = 77;

    @Override
    protected final void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if (!(FirebaseAuth.getInstance().getCurrentUser() == null)) { // see below discussion
            Intent intent = new Intent(this, menuActivity.class);
            startActivity(intent);
            finish();
        } else {
            List<AuthUI.IdpConfig> providers = Arrays.asList(
                    new AuthUI.IdpConfig.EmailBuilder().build());
            startActivityForResult(
                    AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setAvailableProviders(providers)
                            .build(),
                    RCSign);
        }

    }


    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RCSign) {

            if (resultCode == RESULT_OK) {

                Intent intent = new Intent(this, menuActivity.class);
                startActivity(intent);
                finish();
            } else {
                Button gologin = findViewById(R.id.loginButton);
                gologin.setOnClickListener(unused -> recreate());

            }
        }
    }
}

