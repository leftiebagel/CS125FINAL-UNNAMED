package com.example.cs125final_unnamed;

import android.content.SharedPreferences;

import java.util.Map;

public class FileHandler {
    private SharedPreferences storage;
    private int count;

    public static void save(SharedPreferences storage, Drawing current, String name) {
        String toSave = current.toJson().toString();
        String key = "file_" + name;
        storage.edit().putString(key, toSave).apply();
    }
    public void delete(SharedPreferences storage, String key) {
        storage.edit().remove(key).apply();
    }

    public void allStrings(SharedPreferences storage) {
        Map<String, ?> map = storage.getAll();
    }
}
