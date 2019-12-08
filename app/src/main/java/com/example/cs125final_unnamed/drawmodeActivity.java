package com.example.cs125final_unnamed;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import com.google.android.gms.maps.model.LatLng;

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
        palleteVisibile = false;
        drawing = false;
        Line currentLine;

        Intent thisIntent = getIntent();
        if (thisIntent.hasExtra("drawing")) {
            currentDrawing = new Drawing(new JSONObject(thisIntent.getStringExtra("drawing")));
        } else {
            LatLng defaultL = new LatLng(40.1,-80.1);//replace this with the found location
            currentDrawing = new Drawing(defaultL);
        }

        Button palleteToggle = findViewById(R.id.color_toggle);
        LinearLayout buttons = findViewById(R.id.buttons);
        RadioGroup colorGroup = findViewById(R.id.colorPalleteGroup);

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

        Button collapser = findViewById(R.id.collapse);

        collapser.setOnClickListener(v -> {
            LinearLayout mapNOthers = findViewById(R.id.mapAndPallete);
            if (mapNOthers.getVisibility() == View.GONE) {
                mapNOthers.setVisibility(View.VISIBLE);
            } else {
                mapNOthers.setVisibility(View.GONE);
            }
        });

        Button startLine = findViewById(R.id.startLine);
        Button endLine = findViewById(R.id.stopLine);
        Button preview = findViewById(R.id.preview);

        Button save = findViewById(R.id.save);
        Button delete = findViewById(R.id.delete_draw);

        startLine.setOnClickListener(v -> {
            if (!drawing) {
                drawing = true;
                //takes away the option of changing colors
                colorGroup.setVisibility(View.GONE);
                endLine.setVisibility(View.VISIBLE);
                startLine.setVisibility(View.GONE);
            }
        });

        endLine.setOnClickListener(v -> {
            if (drawing) {
                //show a dialog to ask if you want to add the line to the drawing
                //defAlert();
                drawing = false;
                //You can choose color again
                colorGroup.setVisibility(View.VISIBLE);
                startLine.setVisibility(View.VISIBLE);
                endLine.setVisibility(View.GONE);
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

    public void onLocationUpdate() {

    }

    private void lineAlert(Line line) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("would you like to save your new line?");

        builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                //add line to the drawing
                currentDrawing.addLine(line);
            }
        });
        builder.setNegativeButton("yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                //dont add line, continue on with drawmode
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void defAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("this button does something!");
        AlertDialog dialog = builder.create();

        dialog.show();
    }
}
