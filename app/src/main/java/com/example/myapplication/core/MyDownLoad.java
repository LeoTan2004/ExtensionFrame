package com.example.myapplication.core;


import static androidx.core.content.ContextCompat.startActivity;

import android.content.Intent;
import android.net.Uri;
import android.webkit.DownloadListener;

public class MyDownLoad implements DownloadListener {
    @Override
    public void onDownloadStart(String url, String userAgent,
                                String contentDisposition, String mimetype, long contentLength) {
        if (url.endsWith(".apk")) {
            Uri uri = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW,uri);
            startActivity(Boot.getBoot().getActivity(), intent,null);
        }
    }

}

