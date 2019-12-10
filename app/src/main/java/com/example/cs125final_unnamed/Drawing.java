package com.example.cs125final_unnamed;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
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
            JSONObject obj = new JSONObject(jsonStrArg);

            name = obj.getString("name");
            locatorPoint = parseLatLng(obj.getString("locator"));

            String[] linesStrs = (String[]) obj.get("lines");

            for (int i = 0; i < linesStrs.length; i++) {
                lines.add(new Line(linesStrs[i]));
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
        try {
            asJson = asJson.put("name", name);
            asJson = asJson.put("locator", locatorPoint.latitude + "," + locatorPoint.longitude);

            String[] linesJsons = new String[lines.size()];
            for (int i = 0; i < lines.size(); i++) {
                linesJsons[i] = lines.get(i).toJson().toString();
            }
            asJson.putOpt("lines", linesJsons);
        } catch (Exception e) {
            System.out.println("fail in the Drawing Json builder " + e.getMessage());
        }
        return asJson;
    }


    public void setName(String arg) {
        name = arg;
    }

    public String getName() {
        return name;
    }

    private LatLng parseLatLng(String arg) {
        String[] split = arg.split(", ");
        split[0].trim();
        split[1].trim();
        double lat = Double.parseDouble(split[0]);
        double lng = Double.parseDouble(split[1]);

        return new LatLng(lat, lng);
    }

    public void clear() {
        lines = new ArrayList<Line>();
    }
}
