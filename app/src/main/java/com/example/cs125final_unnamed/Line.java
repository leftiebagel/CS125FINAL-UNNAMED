package com.example.cs125final_unnamed;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/*
* Helper Class to make Drawing a little easier, these will be generated during DrawModeActivity
* when the player has hit "End Line"
* */

public class Line {
    private int color;
    private ArrayList<LatLng> points;

    public Line(int colorInput) {
        color = colorInput;
        points = new ArrayList<>();
    }

    public Line(String jsonString) {
        points = new ArrayList<>();
        try {
            JSONObject lineJson = new JSONObject(jsonString);
            color = lineJson.getInt("color");

            String pointsAsString = (String) lineJson.get("points");
            String[] pointsAsStrings = pointsAsString.split(" ");

            for (int i = 0; i < pointsAsStrings.length; i++) {
                pointsAsStrings[i] = pointsAsStrings[i].trim();
                points.add(parseLatLng(pointsAsStrings[i]));
            }
        } catch (Exception e) {
            System.out.println("line Json constructor fail " + points.size());
        }
    }

    public int getColor() {
        return color;
    }
    public ArrayList<LatLng> getPoints() {
        return points;
    }
    public void addPoint(LatLng newPoint) { points.add(newPoint); }

    public JSONObject toJson() {
        try {
            JSONObject toJson = new JSONObject();
            toJson = toJson.put("color", color);

            String pointStrings = "";

            for (int i = 0; i < points.size(); i++) {
                LatLng pointTemp = points.get(i);
                String newPoint = pointTemp.latitude + "," + pointTemp.longitude + " ";
                pointStrings += newPoint;
                System.out.println(newPoint);
            }

            toJson.putOpt("points", pointStrings);
            return toJson;
        } catch (Exception e) {
            System.out.println("toJson fail");
            return null;
        }
    }

    private LatLng parseLatLng(String arg) {
        String[] split = arg.split(",");
        split[0].trim();
        split[1].trim();
        double lat = Double.parseDouble(split[0]);
        double lng = Double.parseDouble(split[1]);

        return new LatLng(lat, lng);
    }
}
