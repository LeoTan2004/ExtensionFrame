package com.example.myapplication.core.Extension;

import androidx.annotation.NonNull;

import java.io.File;

class Javascript{
    private File file;
    private String id;

    public String getId() {
        return id;
    }

    public Javascript(@NonNull File file) throws Exception {
        this.file = file;
        //这里最后思前想后，还是决定交个Extension来处理，而自身占用一个id做标记，在html文档里面也是使用id做标记
        id = String.valueOf(this.hashCode());
    }

    public File getFile() {
        return file;
    }
}