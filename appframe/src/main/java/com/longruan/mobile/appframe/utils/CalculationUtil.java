package com.longruan.mobile.appframe.utils;

import android.content.Context;

import java.lang.ref.WeakReference;

/**
 * <dl>  Class Description
 * <dd> 项目名称：LRMEA
 * <dd> 类名称：CalculationUtil
 * <dd> 类描述：计算工具
 * <dd> 创建时间：2017/7/15
 * <dd> 修改人：无
 * <dd> 修改时间：无
 * <dd> 修改备注：无
 * </dl>
 *
 * @author ljhl
 * @version 1.0
 */

public class CalculationUtil {

    private CalculationUtil() {

    }

    private static class CalculationUtilHolder {
        static final CalculationUtil INSTANCE = new CalculationUtil();
    }

    public static CalculationUtil getInstance() {
        return CalculationUtilHolder.INSTANCE;
    }

    /**
     * dp转px
     * @param context 上下文对象
     * @param dpValue dp值
     * @return px值
     */
    public int dip2px(Context context, float dpValue) {

        //弱引用
        WeakReference<Context> weakReference = new WeakReference<>(context);
        Context wContext = weakReference.get();
        final float scale = wContext.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * px转dp
     * @param context 上下文对象
     * @param pxValue px值
     * @return dp值
     */
    public int px2dip(Context context, float pxValue) {
        //弱引用
        WeakReference<Context> weakReference = new WeakReference<>(context);
        Context wContext = weakReference.get();
        final float scale = wContext.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
}
