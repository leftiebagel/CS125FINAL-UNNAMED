package com.example.cs125final_unnamed;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

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
                    color = Color.BLUE;
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
            dialog.show();
            //takes away the option of changing colors
            colorGroup.setVisibility(View.GONE);
        });

        endLine.setOnClickListener(v -> {
            dialog.show();
            //You can choose color again
            colorGroup.setVisibility(View.VISIBLE);
        });

        preview.setOnClickListener(v -> {
            dialog.show();
        });

        save.setOnClickListener(v -> {
            dialog.show();
        });

        delete.setOnClickListener(v -> {
            dialog.show();
        });



        Button backButton = findViewById(R.id.backButtonDraw);
        backButton.setOnClickListener(v -> {
            finish();
        });
    }
}
