package com.example.cs125final_unnamed;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

public class lineActivity extends AppCompatActivity{
    private int color;
    private int length;
    private Line currentLine;
    private LatLng last;
    private GoogleMap map;
    private drawMap drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        color = getIntent().getIntExtra("color", Color.BLACK);
        setContentView(R.layout.activity_drawmode);
        currentLine = new Line(color);
        length = 0;
        last = new LatLng(0.0,0.0);
        //setup location listener
        if (!runtime_permissions()) {
            setUpUi();
            setUpMap();
        }


    }

    private void addPointToLine(Location location) {
        LatLng newpoint = new LatLng(location.getLatitude(), location.getLongitude());
        length++;
        currentLine.addPoint(newpoint);
    }

    private boolean farEnough(Location location) {
        LatLng locationLatLng = new LatLng(location.getLatitude(), location.getLongitude());
        if (distance(last, locationLatLng) > 4.0) {
            drawer.drawLine(currentLine);
            return true;
        }
        return false;
    }

    //taken from LatLngUtils in the MP
    public static double distance(final LatLng start, final LatLng end) {
        final double latScale = 110574;
        final double lngScale = 111320;
        final double degRad = Math.PI / 180;

        double latRads = degRad * start.latitude;

        double latDistance = latScale * (start.latitude - end.latitude);
        double lngDistance = lngScale * (start.longitude - end.longitude) * Math.cos(latRads);
        return Math.sqrt(latDistance * latDistance + lngDistance * lngDistance);
    }

    public void setUpMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.gameMap);
        // Interestingly, the UI component itself doesn't have methods to manipulate the map
        // We need to get a GoogleMap instance from it and use that
        mapFragment.getMapAsync(theMap -> {
            // Save the map so it can be manipulated later
            map = theMap;
            drawer = new drawMap(map, new LatLng(40.013,-88.002));
        });
    }

    public void setUpUi() {
        LinearLayout bottoms = findViewById(R.id.bottomButtons);
        bottoms.setVisibility(View.GONE);
        RadioGroup pallete = findViewById(R.id.colorPalleteGroup);
        pallete.setVisibility(View.GONE);
        Button palleteButton = findViewById(R.id.color_toggle);
        Button startLine = findViewById(R.id.startLine);
        startLine.setVisibility(View.GONE);

        palleteButton.setOnClickListener(v -> {
            LinearLayout menu = findViewById(R.id.buttons);
            if (menu.getVisibility() == View.GONE) {
                menu.setVisibility(View.VISIBLE);
            } else {
                menu.setVisibility(View.GONE);
            }
        });

        Button endLine = findViewById(R.id.stopLine);
        endLine.setVisibility(View.VISIBLE);
        endLine.setOnClickListener(V -> {
            lineAlert(currentLine);
        });
        Button backButton = findViewById(R.id.backButtonDraw);
        backButton.setOnClickListener(v -> {
            //cancels line activity
            finish();
        });
    }

    private void lineAlert(Line line) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("would you like to save your new line?");

        builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                int resultCode = 0;
                Intent resultIntent = new Intent();
                resultIntent.putExtra("newLine", currentLine.toString());
                setResult(resultCode, resultIntent);
                finish();
            }
        });
        builder.setNegativeButton("no", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                setResult(1);
                finish();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}