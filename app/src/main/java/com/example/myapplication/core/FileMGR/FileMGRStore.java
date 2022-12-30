package com.example.myapplication.core.FileMGR;

import android.content.Context;

import java.util.HashMap;

public class FileMGRStore {
    private final HashMap<String,IFILE> fileMGRs = new HashMap<>();

    public IFILE getPriFileMGR() {
        return fileMGRs.get("Private");
    }

    public IFILE getPubFileMGR() {
        return fileMGRs.get("Public");
    }

    public FileMGRStore(Context context){
        fileMGRs.put("Public",new PubFileMGR());
        fileMGRs.put("Private",new PriFileMGR(context));
    }
    public IFILE customizeFileMGR(String name, String target){
        return  fileMGRs.put(name,new CustomFileMGR(target));
    }

    public IFILE getFileMGR(String name) {
        return fileMGRs.get(name);
    }
}
