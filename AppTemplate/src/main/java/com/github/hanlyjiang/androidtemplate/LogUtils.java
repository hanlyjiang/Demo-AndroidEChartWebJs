package com.github.hanlyjiang.androidtemplate;


import com.orhanobut.logger.Logger;

import java.io.File;

/**
 * 通用的Android Log 工具接口
 * <br/> 目前使用 <a href="https://github.com/orhanobut/logger">Logger工具包</a>
 * <br/>Created by hanlyjiang on 2016/7/22.
 */
public class LogUtils {

    public static void d(String msg) {
        Logger.d(msg);
    }

    public static void d(Object obj) {
        Logger.d(obj);
    }

    public static void d(String tag, String msg) {
        Logger.t(tag).d(msg);
    }

    public static void e(String msg) {
        Logger.e(msg);
    }

    public static void e(String tag, String msg) {
        Logger.t(tag).e(msg);
    }

    public static void w(String msg) {
        Logger.w(msg);
    }

    public static void json(String jsonStr) {
        Logger.json(jsonStr);
    }

    public static void xml(String xmlStr) {
        Logger.xml(xmlStr);
    }


    /**
     * 不支持
     *
     * @param file
     * @param msg
     */
    public static void file(File file, String msg) {

    }
}
