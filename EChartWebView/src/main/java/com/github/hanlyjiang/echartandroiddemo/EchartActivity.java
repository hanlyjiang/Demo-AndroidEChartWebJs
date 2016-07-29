package com.github.hanlyjiang.echartandroiddemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

public class EchartActivity extends AppCompatActivity {

    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_echart);

        mWebView = (WebView) findViewById(R.id.webView);

        mWebView.loadUrl("file:///android_asset/localhtml/doc/example/bar0.html");
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);


    }
}
