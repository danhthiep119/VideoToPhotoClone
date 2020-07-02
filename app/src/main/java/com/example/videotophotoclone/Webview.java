package com.example.videotophotoclone;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Webview extends AppCompatActivity {
    String url;
    WebView webView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_view);
        getDataFromIntent();
        AddControls();
        AddEvents();
    }

    private void getDataFromIntent() {
        Intent intent=getIntent();
        url = intent.getStringExtra("PUSHDATA");
    }


    private void AddEvents() {
        webView.setWebChromeClient(new WebChromeClient());
        webView.loadUrl(url);
    }

    private void AddControls() {
        webView=findViewById(R.id.webView);
    }
}
