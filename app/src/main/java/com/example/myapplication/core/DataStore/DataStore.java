package com.example.myapplication.core.DataStore;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.myapplication.core.Boot;

public class DataStore{
    private String name;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public DataStore(Activity activity, String name){
        this.name = name;
        this.sharedPreferences = activity.getSharedPreferences(name, Context.MODE_PRIVATE);
        this.editor = sharedPreferences.edit();
    }

    public SharedPreferences.Editor getEditor() {
        return editor;
    }

    public SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }
}
