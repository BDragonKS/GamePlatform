package com.longruan.mobile.appframe.utils;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;

import static android.content.Context.TELEPHONY_SERVICE;

/**
 * <dl>  Class Description
 * <dd> 项目名称：LRMEA
 * <dd> 类名称：
 * <dd> 类描述：
 * <dd> 创建时间：2017/10/13
 * <dd> 修改人：无
 * <dd> 修改时间：无
 * <dd> 修改备注：无
 * </dl>
 *
 * @author Jing Lu
 * @version 1.0
 */

public class AppUtil {

    public static String getDeviceID(Context context) {
        TelephonyManager TelephonyMgr = (TelephonyManager) context.getSystemService(TELEPHONY_SERVICE);
        return TelephonyMgr.getDeviceId();
    }

    /**
     * 获取登录设备mac地址
     *
     * @return Mac地址
     */
    public static String getMacAddress(Context context) {
        WifiManager wm = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo connectionInfo = wm.getConnectionInfo();
        String mac = connectionInfo.getMacAddress();
        return mac == null ? "" : mac;
    }
}
