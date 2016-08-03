package com.github.hanlyjiang.androidtemplate;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.File;

/**
 *
 */
public class LocalChartSDActivity extends AppCompatActivity {

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
         */
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }
        });

        mWebView.loadUrl(url);


    }

    /**
     * nexus5 上url 格式：file:///storage/emulated/0/localhtml/GeoShowNative/view/index.htm
     *
     * @return
     */
    private String getUrl() {

        File sdDir = Environment.getExternalStorageDirectory();
        String url = sdDir.getAbsolutePath() + File.separator + "localhtml/GeoShowNative/index.htm";
        return Uri.fromFile(new File(url)).toString();
    }
}
