package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;

import com.example.myapplication.core.Boot;
import com.example.myapplication.core.MyWebChromeClient;
import com.example.myapplication.core.MyWebViewClient;

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
        {
            WebSettings settings= webView.getSettings();
            settings.setJavaScriptEnabled(true);
            settings.setSupportZoom(false);
            settings.setAllowFileAccess(true);
            MyWebViewClient myWebViewClient = new MyWebViewClient();
            String s = this.getString(R.string.localhost);
            myWebViewClient.setLocalhost(s);
            webView.setWebViewClient(myWebViewClient);
            webView.setWebChromeClient(new MyWebChromeClient());
            settings.setDomStorageEnabled(true);
        }
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
                    Boot.getBoot().setting();
                }
            });
        }
        webView.loadUrl("https://cs03-xtu.rth1.one/");
        Boot.startup(webView);

    }
}