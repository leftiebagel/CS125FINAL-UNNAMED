package com.example.cs125final_unnamed;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

public class viewmodeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewmode);


        Button backButton = findViewById(R.id.backButtonMap);
        backButton.setOnClickListener(v -> {
            finish();
        });
    }
}
