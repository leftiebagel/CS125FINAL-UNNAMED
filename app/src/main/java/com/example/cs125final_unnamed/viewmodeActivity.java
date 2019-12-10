package com.example.cs125final_unnamed;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class viewmodeActivity extends AppCompatActivity {

    private GoogleMap map;

    private drawMap drawer;

    private Drawing current;

    private String json;

    private ArrayList<Drawing> drawings;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewmode);
        setUpMap();

        Button backButton = findViewById(R.id.backButtonMap);
        backButton.setOnClickListener(v -> {
            finish();
        });
        Intent context = getIntent();
        if (context.getExtras() != null && context.getExtras().containsKey("drawing")) {
            //preview single drawings
            json = getIntent().getStringExtra("drawing");
            current = new Drawing(json);
            drawer.draw(current);
        } else if (context.getExtras() != null && context.getExtras().containsKey("drawings")) {
            //behavior for drawing all drawings
            drawings = new ArrayList<>();
            String drawingsStr = context.getExtras().getString("drawings");
            String[] drawingsArr = drawingsStr.split("D");

            for(String drawingStr : drawingsArr) {
                drawingsStr = drawingStr.substring(0,drawingsStr.lastIndexOf("D"));
                drawings.add(new Drawing(drawingsStr));
            }
            drawer.draw(drawings);
        }
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
