package com.github.hanlyjiang.androidtemplate;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 *
 */
public class BaiduActivity extends AppCompatActivity {

    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_echart);

        mWebView = (WebView) findViewById(R.id.webView);

        String url = getUrl();

        mWebView.getSettings().setDefaultTextEncodingName("gb2312");

        // 页面缩放控制 去除控制条 http://blog.csdn.net/hanhailong726188/article/details/46717621
        mWebView.getSettings().setSupportZoom(true);
        mWebView.getSettings().setBuiltInZoomControls(true);

        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);

        /* 避免出现 Denied starting an intent without a user gesture Webview Android 错误，导致内嵌页面无法显示
         <meta http-equiv="refresh" content="0.1;url=view/index.htm"> 自动刷新并指向新页面
         http://stackoverflow.com/questions/33048945/denied-starting-an-intent-without-a-user-gesture-webview-android/33477955

         原因： 默认情况下如果跳转到一个链接，会由Android 系统浏览器去打开，重写一个WebViewClient 重载shouldOverrideUrlLoading 方法来避免
         https://developer.android.com/guide/webapps/webview.html?hl=zh-cn
         */
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // 如果是百度页面，则在本页面打开，否则使用系统浏览器
                if (Uri.parse(url).getHost().endsWith(".baidu.com")) {
                    // This is my web site, so do not override; let my WebView load the page
                    Log.d("Host", "Host:" + Uri.parse(url).getHost());
                    return false;
                }
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
                return true;
            }
        });

        mWebView.loadUrl(url);


    }

    private String getUrl() {
        return new Uri.Builder().encodedPath("http://www.baidu.com").build().toString();
    }


    // 处理页面内跳转事件
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // Check if the key event was the Back button and if there's history
        if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        }
        // If it wasn't the Back key or there's no web page history, bubble up to the default
        // system behavior (probably exit the activity)
        return super.onKeyDown(keyCode, event);
    }
}

