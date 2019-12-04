package com.example.cs125final_unnamed;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

/*
* Helper Class to make Drawing a little easier, these will be generated during DrawModeActivity
* when the player has hit "End Line"
* */

public class Line {
    private int color;
    private ArrayList<LatLng> points;

    public Line(ArrayList<LatLng> pointsInput, int colorInput) {
        color = colorInput;
        points = pointsInput;
    }

    public int getColor() {
        return color;
    }
    public ArrayList<LatLng> getPoints() {
        return points;
    }
}
