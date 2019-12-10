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

            String[] pointsStrs = (String[]) lineJson.get("points");

            for (int i = 0; i < pointsStrs.length; i++) {
                points.add(parseLatLng(pointsStrs[i]));
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
            JSONObject asJson = new JSONObject();

            asJson.put("color", color);

            String[] pointsStrings = new String[points.size()];

            for (int i = 0; i < points.size(); i++) {
                LatLng point = points.get(i);
                String latlngString = point.latitude + "," + point.longitude;
                pointsStrings[i] = latlngString;
            }

            asJson.putOpt("points", pointsStrings);

            return asJson;
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
