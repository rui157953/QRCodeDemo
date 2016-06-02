package com.example.ryan.qrcodedemo;

import android.app.Application;

import com.example.ryan.qrcodedemo.utils.CrashHandler;

/**
 * Created by Ahead on 2016/5/23.
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        if (Debug.ApplicationExceptionEnabled) {
            CrashHandler crashHandler = CrashHandler.getInstance();
            crashHandler.init(getApplicationContext());
        }
    }

    /**
     * 调试常量
     *
     */
    public interface Debug {
        public boolean Enabled = true; // 调试全局开关
        public boolean ApplicationExceptionEnabled = true; // 文件异常日志开关
    }
}
