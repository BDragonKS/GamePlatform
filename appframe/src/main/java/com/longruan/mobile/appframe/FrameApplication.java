package com.longruan.mobile.appframe;

import com.longruan.mobile.appframe.utils.CrashHandler;
import com.longruan.mobile.appframe.utils.PropertyUtil;


import org.litepal.LitePal;
import org.litepal.LitePalApplication;

public class FrameApplication extends LitePalApplication {

    private static FrameApplication mInstance;
    private int appLocation;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        // 初始化异常处理类，出现异常保存日志到sd卡上
        CrashHandler catchHandler = CrashHandler.getInstance();
        catchHandler.init(getApplicationContext());
//        appLocation = Integer.parseInt(PropertyUtil.getInstance().getValue("appLocation"));

    }

    public static FrameApplication getInstance() {
        return mInstance;
    }

    public int getAppLocation() {
        return appLocation;
    }
}
