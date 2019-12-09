package com.example.cs125final_unnamed;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
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
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

public class lineActivity extends AppCompatActivity{
    private BroadcastReceiver receiver;
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

        //setup location listener
        if (!runtime_permissions()) {
            setUpUi();
            setUpMap();
            Intent i = new Intent(this, GPS_SERVICE.class);
            startService(i);
        }
    }

    private void addPointToLine(LatLng location) {
        map.clear();
        System.out.println("point reached");
        if (last == null) {
            last = location;
            return;
        }
        length++;
        System.out.println("point drawing reached");
        currentLine.addPoint(location);
        drawer.drawLine(currentLine);
    }


    public void setUpMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.gameMap);
        // Interestingly, the UI component itself doesn't have methods to manipulate the map
        // We need to get a GoogleMap instance from it and use that
        mapFragment.getMapAsync(theMap -> {
            // Save the map so it can be manipulated later
            map = theMap;
            if (last == null) {
                drawer = new drawMap(map, new LatLng(40.013,-88.002));
            } else {
                drawer = new drawMap(map, last);
            }
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
                resultIntent.putExtra("newLine", currentLine.toJson().toString());
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

    private boolean runtime_permissions() {
        if (Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(new String[] {Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION}, 100);

            return true;
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults){
        super.onRequestPermissionsResult(requestCode,permissions,grantResults);
        if (requestCode == 100) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                setUpUi();
                setUpMap();
                onResume();
            } else {
                runtime_permissions();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (receiver == null) {
            receiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    //these are the coords, send them somewhere to parse it into a LATLNG
                    System.out.println("RECEIVED LOC UPDATE");
                    String coords = (String) intent.getExtras().get("coords");
                    LatLng parsed = parseLatLng(coords);
                    addPointToLine(parsed);
                    last = parsed;
                    TextView coordsDebug = findViewById(R.id.coordDisplay);
                    coordsDebug.setText(coords);
                    centerMap(parsed);
                }
            };
        }
        registerReceiver(receiver, new IntentFilter("location_update"));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (receiver != null) {
            unregisterReceiver(receiver);
        }
    }

    //location_update comes in format Latitude, Longitude
    private LatLng parseLatLng(String arg) {
        String[] split = arg.split(", ");
        split[0].trim();
        split[1].trim();
        double lat = Double.parseDouble(split[0]);
        double lng = Double.parseDouble(split[1]);

        return new LatLng(lat, lng);
    }

    private void centerMap(LatLng center) {
        final float defaultMapZoom = 18f;
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(
                center, defaultMapZoom));
    }
}