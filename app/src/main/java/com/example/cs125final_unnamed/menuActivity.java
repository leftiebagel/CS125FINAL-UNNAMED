package com.example.cs125final_unnamed;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

        viewButton.setOnClickListener(v -> {
            Intent newIntent = new Intent(this, viewmodeActivity.class); //goes to viewMode
            startActivity(newIntent);
        });

        drawButton.setOnClickListener(v -> {
            Intent newIntent = new Intent(this, drawmodeActivity.class); //goes to drawMode
            startActivity(newIntent);
        });

        portfolioButton.setOnClickListener(v -> {
            Intent newIntent = new Intent(this, portfolioActivity.class); //goes to portfolio
            startActivity(newIntent);
        });

        Button logout = findViewById(R.id.logoutButton);
        logout.setOnClickListener(v -> {
            //are you sure? dialogue
            finish();
        });
    }
}
