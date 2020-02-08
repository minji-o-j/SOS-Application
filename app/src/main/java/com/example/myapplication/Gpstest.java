package com.example.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;


public class Gpstest extends AppCompatActivity {
    private WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gpstest);
        webView=(WebView)findViewById(R.id.webView);
        WebSettings webSettings= webView.getSettings();
        webView.getSettings().setJavaScriptEnabled(true);
        webSettings.setAppCacheEnabled(false);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url){
                view.loadUrl(url);
                return true;
            }
        });
        webView.loadUrl("  https://maps.google.com/maps?f=q&q=(37.598288141681266,126.96199236220738)");
    }
}
