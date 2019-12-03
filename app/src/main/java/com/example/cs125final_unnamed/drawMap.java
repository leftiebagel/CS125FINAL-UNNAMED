package com.example.cs125final_unnamed;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class drawMap {
    GoogleMap map;
    LatLng mapCenter;

    public drawMap (GoogleMap theMap, LatLng center) {
        map = theMap;
        mapCenter = center;
        setupMap();
    }
    //center map

    private void setupMap() {
        final float defaultMapZoom = 18f;
        //LatLng location = new LatLng(40.013,-88.002); near champaign for testing purposes
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(
                mapCenter, defaultMapZoom));
    }

    //start location function
    public void draw(Drawing toDraw) {
        //iterate through list of Lines, Draws them to map
    }

    public void draw(ArrayList<Drawing> toDraw) {
        for (Drawing drawThis : toDraw) {
            draw(drawThis);
        }
    }

    //adding lines
    //end location function
}
