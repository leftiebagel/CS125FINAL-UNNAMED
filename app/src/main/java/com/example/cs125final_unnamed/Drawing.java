package com.example.cs125final_unnamed;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class Drawing {
    /*
    * The point that the map will zoom too if in single view mode, the map fragment will have
    * a marker at this point if in all drawings view mode
    * */
    public LatLng locatorPoint;
    /*
    * Stores Line objects, which store the color
    * */
    private ArrayList<Line> lines;

    public Drawing(LatLng loc) {
        locatorPoint = loc;
        lines = new ArrayList<>();
    }

    public void addLine(Line newL) {
        lines.add(newL);
    }

    public ArrayList<Line> Lines() {
        return lines;
    }
}
