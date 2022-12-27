package com.example.myapplication.core;

import java.util.HashMap;

public class Settings {
    private static final HashMap<String,Object> settings = new HashMap<>();

    public static Object getSettings(String key) {
        return settings.get(key);
    }

    public static Object setSettings(String key,Object obj){
        return settings.put(key,obj);
    }
}
