package com.github.hanlyjiang.androidtemplate.js;

import android.content.Context;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

/**
 * Created by jianghanghang on 2016/8/4.
 */
public class WepAppInterface {

    private Context mContext;

    public WepAppInterface(Context context) {
        this.mContext = context;
    }

    /**
     * Show a toast from the web page
     */
    @JavascriptInterface
    public void showToast(String toast) {
        Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();
    }

    /**
     * 读取文件
     *
     * @param
     * @return
     */
    @JavascriptInterface
    public String readFile() {

        return "这是返回值";
    }

    @JavascriptInterface
    public String onPageLoad() {
        Toast.makeText(mContext, "页面加载中...", Toast.LENGTH_SHORT).show();
        return "onPageLoad";
    }
}
