package com.example.cs125final_unnamed;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONException;
import org.json.JSONObject;

public class drawmodeActivity extends AppCompatActivity {
    private int color = Color.BLACK;
    private Boolean palleteVisibile;
    private Drawing currentDrawing;
    private final int[] colors = {Color.BLACK, Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW};
    private boolean drawing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawmode);
        setUpUI();
        palleteVisibile = false;
        drawing = false;
        Line currentLine;
        LatLng defaultL = new LatLng(40.1, -80.1);//replace this with the found location
        currentDrawing = new Drawing(defaultL);
        //if the intent has a drawing extra, create a new drawing from that
        if (getIntent().hasExtra("drawing")) {
            //currentDrawing = new Drawing(new JSONObject(getIntent().getStringExtra("drawing")));
        }
    }

    private void defAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("this button does something!");
        AlertDialog dialog = builder.create();

        dialog.show();
    }

    private void setUpUI() {
        Button palleteToggle = findViewById(R.id.color_toggle);
        LinearLayout buttons = findViewById(R.id.buttons);
        RadioGroup colorGroup = findViewById(R.id.colorPalleteGroup);
        colorGroup.setVisibility(View.VISIBLE);

        palleteToggle.setOnClickListener(v -> {
            if (palleteVisibile) {
                palleteVisibile = false;
                buttons.setVisibility(View.GONE);
                colorGroup.setOnCheckedChangeListener((unused, checkedId) -> {
                    //unused
                });
            } else {
                palleteVisibile = true;
                buttons.setVisibility(View.VISIBLE);
                colorGroup.setOnCheckedChangeListener((unused, checkedId) -> {
                    color = colors[checkedId];
                });
            }
        });

        Button startLine = findViewById(R.id.startLine);
        startLine.setVisibility(View.VISIBLE);

        Button preview = findViewById(R.id.preview);
        Button save = findViewById(R.id.save);
        Button delete = findViewById(R.id.delete_draw);

        colorGroup.check(R.id.blackButton);
        colorGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.redButton:
                        colorGroup.check(R.id.redButton);
                        color = Color.RED;
                        break;
                    default:
                        colorGroup.check(R.id.blueButton);
                        break;
                }
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
                //get the result or something
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
}
