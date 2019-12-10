package com.example.cs125final_unnamed;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONException;
import org.json.JSONObject;

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
    //string name
    private String name;

    public Drawing(LatLng loc) {
        locatorPoint = loc;
        lines = new ArrayList<>();
        name = "unnamed";
    }

    public Drawing(String jsonStrArg) {
        try {
            JSONObject drawingJson = new JSONObject(jsonStrArg);
            name = drawingJson.getString("name");
            locatorPoint = parseLatLng(drawingJson.getString("locator"));

            lines = new ArrayList<>();
            String linesStr = drawingJson.getString("lines");
            String[] linesStrArray = linesStr.split("L");
            for (int i = 0; i < linesStrArray.length; i++) {
                Line newL = new Line(linesStrArray[i]);
            }
        } catch (Exception e) {
            System.out.println("Drawing reconstruction fail " + e.getMessage());
        }
    }


    public void addLine(Line newL) {
        lines.add(newL);
    }

    public ArrayList<Line> Lines() {
        return lines;
    }

    public JSONObject toJson() {
        JSONObject asJson = new JSONObject();
        String linesStr = "";
        for (int i = 0; i < lines.size(); i++) {
            String lineAsString = lines.get(i).toJson().toString();
            linesStr += lineAsString + "L";
        }
        try {
            asJson = asJson.put("lines", linesStr);
            asJson = asJson.put("name", name);
            asJson = asJson.put("locator", locatorPoint.latitude + "," + locatorPoint.longitude);
            return asJson;
        } catch (Exception e) {
            System.out.println("drawing toJSon fail " + e.getMessage());
        }
        return asJson;
    }

    public void setName(String arg) {
        name = arg;
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
