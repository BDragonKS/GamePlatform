package com.longruan.mobile.appframe.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Administrator on 2017/7/27 0027.
 */

public class DateUtil {

    /**
     * 获取当前时间
     *
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String getCurrentDateStr() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        return formatter.format(Calendar.getInstance().getTime());
    }

    /**
     * 获取当日
     *
     * @return yyyy-MM-dd
     */
    public static String getCurrentSimpleDateStr() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        return formatter.format(Calendar.getInstance().getTime());
    }

    /**
     * 获取当前月份
     *
     * @return yyyy-MM
     */
    public static String getCurrentMonthStr() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM", Locale.CHINA);
        return formatter.format(Calendar.getInstance().getTime());
    }

    /**
     * 获取当前年份
     *
     * @return yyyy
     */
    public static String getCurrentYearStr() {
        return String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
    }

    /**
     * 获取当前月份最后一天
     *
     * @return yyyy-MM-dd
     */
    public static String getCurrentMonthLastDayStr() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 0);
        return formatter.format(calendar.getTime());
    }

    /**
     * 获取上一个月最后一天
     *
     * @return yyyy-MM-dd
     */
    public static String getBeforeMonthLastDayStr() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 0);
        return formatter.format(calendar.getTime());
    }

    /**
     * 获取指定月份最后一天
     *
     * @return yyyy-MM-dd
     */
    public static String getMonthLastDayStr(int month) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, 0);
        return formatter.format(calendar.getTime());
    }

    /**
     * 获取指定月份
     *
     * @return yyyy-MM
     */
    public static String getMonthStr(int month) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM", Locale.CHINA);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, 0);
        return formatter.format(calendar.getTime());
    }

    /**
     * 将毫秒转为时分秒
     *
     * @param duration 毫秒数
     * @return 时分秒
     */
    public static String timeParse(long duration) {
        String time = "";
        long hour = duration / 3600000;

        long minute = (duration % 3600000) / 60000;
        if (hour < 10) {
            time += "0";
        }
        time += hour + "时";
        if (minute < 10) {
            time += "0";
        }
        time += minute + "分";
        return time;
    }

    /**
     * 将秒转为时分秒
     *
     * @param second 秒数
     * @return 时分秒
     */
    public static String second2Parse(long second) {
        String time = "";
        long hour = second / 3600;

        long minute = (second % 3600) / 60;
        if (hour < 10) {
            time += "0";
        }
        time += hour + "时";
        if (minute < 10) {
            time += "0";
        }
        time += minute + "分";
        return time;
    }

    /**
     * 将毫秒转为具体日期
     *
     * @param millisecond 时间戳
     * @return yyyy-MM-dd格式
     */
    public static String convert2Date(long millisecond) {
        try {
            Date date = new Date(millisecond);
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
            return formatter.format(date);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将毫秒转为具体日期
     *
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String convert2Date1(long millisecond) {
        try {
            Date date = new Date(millisecond);
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
            return formatter.format(date);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * String转long型
     *
     * @param strDate 字符串
     * @return 时间戳
     */
    public static long convert2Time(String strDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        try {
            Date date = formatter.parse(strDate);
            return date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * 获取当前月的月初
     *
     * @return 字符串
     */
    public static String getCurrentMonthStart() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-01", Locale.CHINA);
        return formatter.format(Calendar.getInstance().getTime());
    }

    /**
     * 获取当前时间一周的日期
     *
     * @return 字符串
     */
    public static String getBeforeCurrent7() {

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -6);
        return calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.DAY_OF_MONTH);
    }
}
