package com.example.cs125final_unnamed;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

public class menuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_mainmenu);

        Button viewButton = findViewById(R.id.viewModeButton);
        Button drawButton = findViewById(R.id.drawModeButton);
        Button portfolioButton = findViewById(R.id.portfolioButton);

        //set the click listeners.

        Button logout = findViewById(R.id.logoutButton);
        //are you sure?
        logout.setOnClickListener(v -> {
            finish();
        });
    }
}
