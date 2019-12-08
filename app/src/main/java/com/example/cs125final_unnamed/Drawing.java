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

    public Drawing(JSONObject jsonArg) {
        try {
            locatorPoint = (LatLng) jsonArg.get("locator");
            lines = (ArrayList<Line>) jsonArg.get("lines");
            name = jsonArg.getString("name");
        } catch (JSONException e) {
            //input is bad
        }
    }

    public void addLine(Line newL) {
        lines.add(newL);
    }

    public ArrayList<Line> Lines() {
        return lines;
    }

    public JSONObject getAsJson() {
        try {
            JSONObject toReturn = new JSONObject();
            toReturn.put("lines", lines);
            toReturn.put("locator", locatorPoint);
            toReturn.put("name", name);
            return toReturn;
        } catch (Exception e) {
            return null;
        }
    }

    public void setName(String arg) {
        name = arg;
    }
}
