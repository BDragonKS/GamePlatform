package com.ljhl.gameplatform;


import com.longruan.mobile.appframe.FrameApplication;

import android.content.Context;
import android.support.multidex.MultiDex;


public class MyApplication extends FrameApplication {




    @Override
    public void onCreate() {
        super.onCreate();


    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
