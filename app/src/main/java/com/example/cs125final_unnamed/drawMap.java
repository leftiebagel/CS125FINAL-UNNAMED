package com.example.cs125final_unnamed;

import android.graphics.Color;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;

public class drawMap {
    GoogleMap map;
    LatLng mapCenter;

    public drawMap (GoogleMap theMap, LatLng center) {
        map = theMap;
        mapCenter = center;
        setupMap();
    }

    //centers map
    private void setupMap() {
        final float defaultMapZoom = 18f;
        //LatLng location = new LatLng(40.013,-88.002); near champaign for testing purposes
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(
                mapCenter, defaultMapZoom));
    }

    //start location function
    public void draw(Drawing toDraw) {
        ArrayList<Line> linesToDraw = toDraw.Lines();
        System.out.println("draw reached");
        for (Line line: linesToDraw) {
            drawLine(line);
        }
    }

    public void drawLine(Line line) {
        int color = line.getColor();
        ArrayList<LatLng> points = line.getPoints();
        System.out.println("drawLine reached");
        //add Slider for line width at start of line?
        if (points.size() <= 1) {
            return;
        }

        PolylineOptions newLine = new PolylineOptions();
        for (int i = 0; i < points.size(); i++) {
            newLine = newLine.add(points.get(i));
        }
        newLine = newLine.color(color);
        newLine = newLine.width(100);
        Polyline newLineDrawn = map.addPolyline(newLine);
    }

    public void draw(ArrayList<Drawing> toDraw) {
        for (Drawing drawThis : toDraw) {
            draw(drawThis);
        }
    }
}
