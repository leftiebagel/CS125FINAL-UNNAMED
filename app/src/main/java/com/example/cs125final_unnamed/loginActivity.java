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
import com.firebase.ui.auth.IdpResponse;




public class loginActivity extends AppCompatActivity {
    /**
     * Sign in code.
     */
    private final int rcSignIn = 77;

    /**
     * onCreate starts the login procedure.
     *
     * @param savedInstanceState saved state from the previously terminated instance of this activity
     */
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
                    rcSignIn);
            // start login activity for result - see below discussion
        }

    }

    /**
     * Invoked by the Android system when a request launched by startActivityForResult completes.
     *
     * @param requestCode the request code passed by to startActivityForResult
     * @param resultCode  resultCode a value indicating how the request finished
     * @param data        an Intent containing results
     */

    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == rcSignIn) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if (resultCode == RESULT_OK) {
                // Successfully signed in
                //FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                Intent intent = new Intent(this, menuActivity.class);
                startActivity(intent);
                finish();
            } else {
                Button gologin = findViewById(R.id.loginButton);
                gologin.setOnClickListener(unused -> recreate());
                // Sign in failed. If response is null the user canceled the
                // sign-in flow using the back button. Otherwise check
                // response.getError().getErrorCode() and handle the error.
                // ...
            }
        }
    }
}

