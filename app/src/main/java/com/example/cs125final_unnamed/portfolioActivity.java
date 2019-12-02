package com.example.cs125final_unnamed;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class portfolioActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_portfolio);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("this button does something!");
        AlertDialog dialog = builder.create();

        LinearLayout chunksList = findViewById(R.id.drawingsList);
        chunksList.removeAllViews();

        View messageChunk = getLayoutInflater().inflate(R.layout.chunk_portfolioentry, chunksList, false);
        TextView messageText = messageChunk.findViewById(R.id.infoBox);
        messageText.setText("EXAMPLE");

        Button viewButton = messageChunk.findViewById(R.id.viewButton);
        Button deleteButton = messageChunk.findViewById(R.id.deleteButton);

        viewButton.setOnClickListener(v -> {
            dialog.show();
        });
        deleteButton.setOnClickListener(v -> {
            dialog.show();
        });

        chunksList.addView(messageChunk);

        Button backButton = findViewById(R.id.backButtonPortfolio);
        backButton.setOnClickListener(v -> {
            finish();
        });
    }
}
