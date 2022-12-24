package com.example.myapplication.core;

import android.content.Context;

public class PriFileMGR extends FileMGR{
    private Context context;
    @Override
    public String convertPath(String path) {
        return context.getExternalFilesDir(path).getPath();
    }

    public PriFileMGR(Context context){
        this.context = context;
    }
}

class PubFileMGR extends FileMGR{

    @Override
    public String convertPath(String path) {
        return path;
    }
}
