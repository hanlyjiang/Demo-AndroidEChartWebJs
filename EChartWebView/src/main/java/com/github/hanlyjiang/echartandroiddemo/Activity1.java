package com.github.hanlyjiang.echartandroiddemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

public class Activity1 extends AppCompatActivity {

    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_echart);

        mWebView = (WebView) findViewById(R.id.webView);

        mWebView.loadUrl("http://www.google.com.hk");
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);


    }
}
