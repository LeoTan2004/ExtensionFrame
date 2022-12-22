package com.example.myapplication.core;

import androidx.annotation.NonNull;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.StringJoiner;

class Javascript{
    private File file;
    private String path;

    public String getPath() {
        return path;
    }

    public Javascript(@NonNull File file) throws IOException {
        this.file = file;

        //todo create a relative path for webview,!!check!!

        String s =file.getPath();
        String[] f = Boot.getBoot().getWebView().getContext().getAssets().getLocales();
        String result = f[0];
        for(int i = 1; i < f.length; i++) {
            result = result + "/" + f[i];
        }
        this.path = s.replace(result,Boot.getBoot().getLocalhost());
    }

    public File getFile() {
        return file;
    }
}