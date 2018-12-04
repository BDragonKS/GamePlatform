/**
 * System_name：gsmobile
 *
 * 文件名：DebugUtil.java
 *
 * 描述：
 *
 * 日期：2016-4-7
 *
 * 本系统是商用软件，未经授权擅自复制或传播本程序的部分或全部将是非法的
 *
 * Copyright(C) 足下科技有限公司YC48_第一组 Corporation 2005~2012
 *
 * @version V1.0
 */
package com.longruan.mobile.appframe.utils;

import android.util.Log;

/**
 * <dl>  Class Description
 *  <dd> 项目名称：AppFrame
 *  <dd> 类名称：DebugUtil
 *  <dd> 类描述：调试时打印日志的类
 *  <dd> 创建时间：2016-4-7上午10:49:33 2016
 *  <dd> 修改人：无
 *  <dd> 修改时间：无
 *  <dd> 修改备注：无
 * </dl>
 * @author lujing
 * @version 1.0
 */
class DebugUtil {
    private boolean DEBUG = true;
    private final String TAG = "LongRuan";

    private static class DebugUtilHolder{
        static final DebugUtil INSTANCE = new DebugUtil();
    }
    private DebugUtil(){

    }
    public static DebugUtil getInstance(){
        return DebugUtilHolder.INSTANCE;
    }

    /**
     * 打印参数字符串
     * @param str 字符串
     */
    public void log(String str) {
        if (DEBUG) {
            Log.d(TAG, str);
        }
    }

    /**
     * 打印指定Tag的字符串
     * @param tag 指定tag
     * @param str 字符串
     */
    public void log(String tag, String str) {
        if (DEBUG) {
            Log.d(tag, str);
        }
    }

    /**
     *打印错误字符串
     * @param str 字符串
     */
    public void err(String str) {
        if (DEBUG) {
            Log.e(TAG, str);
        }
    }
    /**
     *打印指定tag的错误字符串
     * @param str 字符串
     */
    public void err(String tag,String str) {
        if (DEBUG) {
            Log.e(tag, str);
        }
    }
}
