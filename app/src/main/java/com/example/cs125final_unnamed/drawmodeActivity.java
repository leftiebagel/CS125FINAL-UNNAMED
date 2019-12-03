package com.example.cs125final_unnamed;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

public class drawmodeActivity extends AppCompatActivity {
    private int color = Color.BLACK;
    private Boolean palleteVisibile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawmode);
        palleteVisibile = false;

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("this button does something!");
        AlertDialog dialog = builder.create();

        Button palleteToggle = findViewById(R.id.color_toggle);
        palleteToggle.setOnClickListener(v -> {
            LinearLayout buttons = findViewById(R.id.buttons);
            RadioGroup colorGroup = findViewById(R.id.colorPalleteGroup);
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
                    color = Color.BLUE;
                });
            }
        });

        Button startLine = findViewById(R.id.startLine);
        Button endLine = findViewById(R.id.stopLine);
        Button preview = findViewById(R.id.preview);

        startLine.setOnClickListener(v -> {
            dialog.show();
        });

        endLine.setOnClickListener(v -> {
            dialog.show();
        });

        preview.setOnClickListener(v -> {
            dialog.show();
        });


        Button backButton = findViewById(R.id.backButtonDraw);
        backButton.setOnClickListener(v -> {
            finish();
        });
    }
}
