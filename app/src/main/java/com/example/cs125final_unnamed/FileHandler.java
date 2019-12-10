package com.example.cs125final_unnamed;

import android.content.SharedPreferences;

import java.util.Map;

public class FileHandler {
    private SharedPreferences storage;
    private int count;

    public static void save(SharedPreferences storage, Drawing current, String name) {
        String toSave = current.toJson().toString();
        storage.edit().putString(name, toSave).apply();
    }
    public void delete(SharedPreferences storage, String key) {
        storage.edit().remove(key).apply();
    }

    public Map<String, ?> allStrings(SharedPreferences storage) {
        return storage.getAll();
    }
}
