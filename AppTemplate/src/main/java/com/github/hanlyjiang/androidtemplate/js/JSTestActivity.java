package com.github.hanlyjiang.androidtemplate.js;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

import com.github.hanlyjiang.androidtemplate.R;

public class JSTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_echart);

        WebView mWebView = (WebView) findViewById(R.id.webView);

        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        mWebView.addJavascriptInterface(new WepAppInterface(this), "Android");
        mWebView.loadUrl("file:///android_asset/jsWeb/index.html");


    }
}
