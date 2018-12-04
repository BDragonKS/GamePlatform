package com.longruan.mobile.appframe.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

/**
 * Created by Administrator on 2017/7/11 0011
 */

public class NetWorkUtils {
    public static boolean isNetworkAvailable(Context context) {
        if (context != null) {
            ConnectivityManager connectivity = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivity != null) {
                NetworkInfo info = connectivity.getActiveNetworkInfo();
                if (info != null && info.isConnected())
                {
                    // 当前网络是连接的
                    if (info.getState() == NetworkInfo.State.CONNECTED)
                    {
                        // 当前所连接的网络可用
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * 获取mac地址
     */
    public static String getMacAddress(Context context) {
        String mac = "";
        WifiManager wm = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wm.getConnectionInfo();
        if (info != null) {
            mac = info.getMacAddress();
        }
        return mac;
    }
}
