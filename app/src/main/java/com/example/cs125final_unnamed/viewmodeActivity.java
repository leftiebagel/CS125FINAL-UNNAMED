package com.example.cs125final_unnamed;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

import com.google.android.gms.maps.MapView;

public class viewmodeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewmode);
        MapView map = findViewById(R.id.mapView2);



        Button backButton = findViewById(R.id.backButtonMap);
        backButton.setOnClickListener(v -> {
            finish();
        });
    }
}
