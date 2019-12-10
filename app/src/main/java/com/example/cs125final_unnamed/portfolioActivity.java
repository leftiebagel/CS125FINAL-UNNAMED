package com.example.cs125final_unnamed;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.File;
import java.util.Map;

public class portfolioActivity extends AppCompatActivity {
    private SharedPreferences storage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_portfolio);

        LinearLayout chunksList = findViewById(R.id.drawingsList);
        chunksList.removeAllViews();
        FileHandler handler = new FileHandler();
        storage = getApplicationContext().getSharedPreferences("DRAWRUN_PREF", 0);
        Map<String, ?> mapOfDrawings = handler.allStrings(storage);

        for (String key: mapOfDrawings.keySet()) {
            View messageChunk = getLayoutInflater().inflate(R.layout.chunk_portfolioentry, chunksList, false);
            TextView messageText = messageChunk.findViewById(R.id.infoBox);
            messageText.setText(key);

            Button viewButton = messageChunk.findViewById(R.id.viewButton);
            Button deleteButton = messageChunk.findViewById(R.id.deleteButton);
            Button editButton = messageChunk.findViewById(R.id.editButton);

            viewButton.setOnClickListener(v -> {
                Intent intent = new Intent(this, viewmodeActivity.class);
                intent.putExtra("drawing", (String) mapOfDrawings.get(key));
                startActivity(intent);
            });
            editButton.setOnClickListener(v -> {
                Intent intent = new Intent(this, drawmodeActivity.class);
                intent.putExtra("drawing", (String) mapOfDrawings.get(key));
                startActivity(intent);
            });
            deleteButton.setOnClickListener(v -> {
                handler.delete(storage, key);
                messageText.setText("DELETED");
            });

            chunksList.addView(messageChunk);

            Button backButton = findViewById(R.id.backButtonPortfolio);
            backButton.setOnClickListener(v -> {
                finish();
            });
        }
    }
}
