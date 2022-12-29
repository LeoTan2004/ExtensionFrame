package com.example.myapplication.core.FileMGR;

import android.content.Context;

import java.util.HashMap;

public class FileMGRStore {
    private final HashMap<String,IFILE> fileMGRs = new HashMap<>();

    public IFILE getPriFileMGR() {
        return fileMGRs.get("Private");
    }

    public FileMGR getPubFileMGR() {
        return fileMGRs.get("Public");
    }

    public FileMGRStore(Context context){
        fileMGRs.put("Public",new PubFileMGR());
        fileMGRs.put("Private",new PriFileMGR(context));
    }
    public FileMGR customizeFileMGR(String name, Context context,String target){
        return fileMGRs.put(name,new CustomFileMGR(context, target));
    }

    public FileMGR getFileMGR(String name) {
        return fileMGRs.get(name);
    }
}
