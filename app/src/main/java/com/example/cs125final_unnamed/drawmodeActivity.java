package com.example.cs125final_unnamed;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONException;
import org.json.JSONObject;

public class drawmodeActivity extends AppCompatActivity {
    private int color = Color.BLACK;
    private Boolean palleteVisibile;
    private Drawing currentDrawing;
    private GoogleMap map;
    private drawMap drawer;
    private SharedPreferences storage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawmode);

        palleteVisibile = false;
        LatLng defaultL = new LatLng(40.013,-88.002);//replace this with the found location
        currentDrawing = new Drawing(defaultL);
        storage = getApplicationContext().getSharedPreferences("PREF_STORE", 0);

        setUpUI();
        setUpMap();
    }

    private void defAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("this button does something!");
        AlertDialog dialog = builder.create();

        dialog.show();
    }

    public void setUpMap() {
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

    private void setUpUI() {
        Button palleteToggle = findViewById(R.id.color_toggle);
        LinearLayout buttons = findViewById(R.id.buttons);
        RadioGroup colorGroup = findViewById(R.id.colorPalleteGroup);
        System.out.println(colorGroup.toString());
        colorGroup.setVisibility(View.VISIBLE);

        palleteToggle.setOnClickListener(v -> {
            if (palleteVisibile) {
                palleteVisibile = false;
                buttons.setVisibility(View.GONE);
            } else {
                palleteVisibile = true;
                buttons.setVisibility(View.VISIBLE);
            }
        });

        Button startLine = findViewById(R.id.startLine);
        startLine.setVisibility(View.VISIBLE);

        Button preview = findViewById(R.id.preview);
        Button save = findViewById(R.id.save);
        Button delete = findViewById(R.id.delete_draw);

        colorGroup.check(R.id.blackButton);
        colorGroup.setOnCheckedChangeListener((unused, checkedId) -> {
            int ID = checkedId;
            switch(ID) {
                case R.id.blackButton:
                    color = Color.BLACK;
                    break;
                case R.id.redButton:
                    color = Color.RED;
                    break;
                case R.id.greenButton:
                    color = Color.GREEN;
                    break;
                case R.id.blueButton:
                    color = Color.BLUE;
                    break;
                case R.id.yellowButton:
                    color = Color.YELLOW;
                    break;
                default:
                    color = Color.BLACK;
            }
        });

        startLine.setOnClickListener(v -> {
                //takes away the option of changing colors
                colorGroup.setVisibility(View.GONE);
                startLine.setVisibility(View.GONE);
                Intent intent = new Intent(this, lineActivity.class);
                intent.putExtra("color", color);
                startActivityForResult(intent, 0);
                colorGroup.setVisibility(View.VISIBLE);
                startLine.setVisibility(View.VISIBLE);
        });
        preview.setOnClickListener(v -> {
            //goes to singleView mode and draws the drawing
            Intent intent = new Intent(this, viewmodeActivity.class);
            intent.putExtra("drawing", currentDrawing.toJson().toString());
            startActivity(intent);
        });

        save.setOnClickListener(v -> {
            //sends the drawing to the file handler
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder = builder.setTitle("Are you sure you want to save " + currentDrawing.getName());
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    storage.edit().putString("jsonString", currentDrawing.toJson().toString()).commit();
                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        });
        delete.setOnClickListener(v -> {
            //destroys drawing, are you sure dialog
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder = builder.setTitle("Are you sure you want to delete " + currentDrawing.getName());
            builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    FileHandler.deleteFile(currentDrawing.getName()+"JSONSAVE.txt");
                }
            });
            builder.setNegativeButton("no", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    //just closes the dialog
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        });
        Button backButton = findViewById(R.id.backButtonDraw);
        backButton.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Your changes might not be saved");
            builder.setPositiveButton("continue", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finish();
                }
            });
            builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    //just closes the dialog
                }
            });
            AlertDialog alert = builder.create();
            alert.show();
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == 0) {
            System.out.println("result received");
            String lineString = data.getExtras().getString("newLine");
            Line result = new Line(lineString);
            currentDrawing.addLine(result);
            drawer.draw(currentDrawing);
            centerMap(result.getPoints().get(0));
        }
    }

    private void centerMap(LatLng center) {
        final float defaultMapZoom = 18f;
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(
                center, defaultMapZoom));
    }
}
