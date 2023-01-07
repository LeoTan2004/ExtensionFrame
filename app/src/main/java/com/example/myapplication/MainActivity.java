package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

import com.example.myapplication.core.Boot;
import com.example.myapplication.core.MyDownLoad;
import com.example.myapplication.core.MyWebChromeClient;
import com.example.myapplication.core.MyWebViewClient;
import com.example.myapplication.core.Settings;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {
    public Boot getBoot() {
        return boot;
    }

    private Boot boot = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        WebView webView = (WebView) findViewById(R.id.webView);
        webViewInit(webView);
        buttonInit();
        Boot.startup(this,webView);
        webView.loadUrl(getResources().getString(R.string.startPage));
    }

    private void webViewInit(@NonNull WebView webView) {
        WebSettings webSettings= webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setSupportZoom(false);
        webSettings.setAllowFileAccess(true);
        MyWebViewClient myWebViewClient = new MyWebViewClient();
        String s = this.getResources().getString(R.string.localhost);
        webView.setWebViewClient(myWebViewClient);
        webView.setWebChromeClient(new MyWebChromeClient());
        webSettings.setDomStorageEnabled(true);
        webView.setDownloadListener(new MyDownLoad());
        webSettings.setAppCacheEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.supportMultipleWindows();
        webSettings.setAllowContentAccess(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setLoadsImagesAutomatically(true);

        webView.setWebChromeClient(new WebChromeClient());//这行最好不要丢掉
        webSettings.setUserAgentString("Mozilla/5.0 (Linux; " +
                "Android 13; sdk_gphone64_x86_64) AppleWebKit/537.36 (KHTML, like Gecko) " +
                "Chrome/101.0.0.0 Safari/537.36");
    }

    private void buttonInit() {
        Button goBack = findViewById(R.id.goBack);
        {
            goBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Boot.getBoot().goBack();
                }
            });
        }
        Button goHome = (Button) findViewById(R.id.goHome);
        {
            goHome.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Boot.getBoot().goHome();
                }
            });
        }
        Button setting = (Button) findViewById(R.id.setting);
        {
            setting.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Boot.getBoot().goSetting();
                    // TODO: 2023/1/7 测试按钮暂停使用
//                    Boot.getBoot().test();
                }
            });
        }
    }
}