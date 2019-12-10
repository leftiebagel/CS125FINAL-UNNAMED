package com.example.cs125final_unnamed;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.SupportMapFragment;

import android.os.Bundle;
import android.widget.LinearLayout;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class viewmodeActivity extends AppCompatActivity {

    private GoogleMap map;

    private drawMap drawer;

    private Drawing current;

    private String json;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewmode);
        setUpMap();

        LinearLayout middleLayout = findViewById(R.id.mapAndPallete);

        Button backButton = findViewById(R.id.backButtonMap);
        backButton.setOnClickListener(v -> {
            finish();
        });
        json = getIntent().getStringExtra("drawing");
        current = new Drawing(json);
        drawer.draw(current);
    }

    /**
     * Sets up the Google map.
     */
    @SuppressWarnings("MissingPermission")
    private void setUpMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.gameMap);
        // Interestingly, the UI component itself doesn't have methods to manipulate the map
        // We need to get a GoogleMap instance from it and use that
        mapFragment.getMapAsync(theMap -> {
            // Save the map so it can be manipulated later
            map = theMap;
            drawer = new drawMap(map,new LatLng(40.013,-88.002));
        });
    }


}
