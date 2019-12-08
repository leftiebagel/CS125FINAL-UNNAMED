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

        for (Line line: linesToDraw) {
            drawLine(line);
        }
    }

    public void drawLine(Line line) {
        int color = line.getColor();
        ArrayList<LatLng> points = line.getPoints();

        //add Slider for line width at start of line?
        if (points.size() <= 1) {
            return;
        }

        for (int i  = 0; i < points.size() - 1; i++) {
            Polyline linePoly = map.addPolyline(new PolylineOptions()
                    .add(points.get(i), points.get(i + 1))
                    .width(5)
                    .color(color));
        }
    }

    public void draw(ArrayList<Drawing> toDraw) {
        for (Drawing drawThis : toDraw) {
            draw(drawThis);
        }
    }
}
