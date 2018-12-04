package com.loveplusplus.update;

public class Constants {

    public static String CHECK_UPDATE_BASE_URL = "http://192.168.40.13:8816/";

    // json {"url":"http://192.168.205.33:8080/Hello/app_v3.0.1_Other_20150116.apk","versionCode":2,"updateMessage":"版本更新信息"}

    static final String APK_DOWNLOAD_URL = "url";
    static final String APK_UPDATE_CONTENT = "message";
    static final String APK_VERSION_CODE = "versioncode";


    static final int TYPE_NOTIFICATION = 2;

    static final int TYPE_DIALOG = 1;

    static final String TAG = "UpdateChecker";

    static  String UPDATE_URL = "getLatestVersionByType?type=1";
}
