package com.github.hanlyjiang.echartandroiddemo;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.os.EnvironmentCompat;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

import java.io.File;

/**
 * 使用WebView 加载存储在内部存储中的Echart示例html
 * <br/>需要将assets 中的localhtml 文件夹拷贝至内部存储中，或者下载echart的示例
 * <br/>使用的echart 为 2.2.7 <a href="https://github.com/ecomfe/echarts/tree/2.2.7">：github地址</a>
 * <br/>下载地址： <a href="https://github.com/ecomfe/echarts/archive/2.2.7.zip" >github echart2.2.7 zip</a>
 *
 */
public class EChartSDActivity extends AppCompatActivity {

    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_echart);

        mWebView = (WebView) findViewById(R.id.webView);

        String url = getUrl();
        mWebView.loadUrl(url);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);


    }

    private String getUrl() {
        File sdDir = Environment.getExternalStorageDirectory();
        String url = sdDir.getAbsolutePath() + File.separator + "localhtml/doc/example/chord3.html";
        return Uri.fromFile(new File(url)).toString();
    }
}
