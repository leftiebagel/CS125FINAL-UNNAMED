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
    private final int[] colors = {Color.BLACK, Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW};
    private boolean drawing;
    private GoogleMap map;
    private drawMap drawer;
    private BroadcastReceiver receiver;

    @Override
    protected void onResume() {
        super.onResume();
        if (receiver == null) {
            receiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    //these are the coords, send them somewhere
                    String coords = intent.getStringExtra("coords");
                }
            }
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawmode);

        palleteVisibile = false;
        drawing = false;
        Line currentLine;
        LatLng defaultL = new LatLng(40.013,-88.002);//replace this with the found location
        currentDrawing = new Drawing(defaultL);

        if (!runtime_permissions()) {
            setUpUI();
            setUpMap();
        }
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
            if (!drawing) {
                drawing = true;
                //takes away the option of changing colors
                colorGroup.setVisibility(View.GONE);
                startLine.setVisibility(View.GONE);
                Intent intent = new Intent(this, lineActivity.class);
                intent.putExtra("color", color);
                startActivityForResult(intent, 0);

                setUpUI();
            }
        });
        preview.setOnClickListener(v -> {
            //goes to singleView mode and draws the drawing
            Intent intent = new Intent(this, viewmodeActivity.class);
            intent.putExtra("drawing", currentDrawing.getAsJson().toString());
            startActivity(intent);
        });

        save.setOnClickListener(v -> {
            //sends the drawing to the file handler
            defAlert();
        });
        delete.setOnClickListener(v -> {
            //destroys drawing, are you sure dialog
            defAlert();
        });
        Button backButton = findViewById(R.id.backButtonDraw);
        backButton.setOnClickListener(v -> {
            //alert that drawing may not have been saved
            finish();
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == 0) {
            Line result  = (Line) data.getExtras().get("line");
            currentDrawing.addLine(result);
        }
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
                setUpUI();
                setUpMap();
            } else {
                runtime_permissions();
            }
        }
    }
}
