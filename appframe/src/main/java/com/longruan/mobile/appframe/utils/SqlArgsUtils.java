package com.longruan.mobile.appframe.utils;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenming on 2017/8/10
 */

public class SqlArgsUtils {

    /**
     * 将"520100,520200"转换为"('520100','520200')"格式，作为IN查询的值
     * @param args
     * @return
     */
    public static String convert2InArgs(String args) {
        if (TextUtils.isEmpty(args))
            return null;
        StringBuilder builder = new StringBuilder();
        String[] array = args.split(",");
        int length = array.length;
        if (length == 1) {
            builder.append("('")
                    .append(args)
                    .append("')");
            return builder.toString();
        }
        builder.append("(");
        for (int i=0; i<array.length; i++) {
            builder.append("'")
                    .append(array[i])
                    .append("'")
                    .append(",");
        }
        builder.deleteCharAt(builder.lastIndexOf(","));
        builder.append(")");

        return builder.toString();
    }

    /**
     * 将{'520100','520000'}转换为{'5201%','52%'}格式，作为like查询的值
     * @param args xzqhdms or hylbdms
     * @return
     */
    public static List<String> convert2LikeArgs(String args) {
        if (TextUtils.isEmpty(args))
            return null;

        String[] array = args.split(",");
        List<String> list = new ArrayList<>();
        int size = array.length;
        for (int i=0; i<size; i++) {
            String str = new StringBuffer(new StringBuilder(array[i])
                    .reverse()
                    .toString()
                    .replaceFirst("^0*","")).reverse()
                    .toString();
            str = new StringBuilder("\'" + str +"%\'").toString();
            list.add(str);
        }
        return list;
    }

    /**
     * 将520100转换为5201%格式，作为like查询的值
     * @param args
     * @return
     */
    public static String convert2LikeArg(String args) {
        if (TextUtils.isEmpty(args))
            return null;

        String str = new StringBuffer(new StringBuilder(args)
                .reverse()
                .toString()
                .replaceFirst("^0*","")).reverse()
                .toString();
        str += "%";

        return str;
    }

    public static String makePlaceholders(int len) {
        if (len < 1) {
            throw new RuntimeException("No placeholders");
        } else {
            StringBuilder sb = new StringBuilder(len * 2 - 1);
            sb.append("?");
            for (int i = 1; i < len; i++) {
                sb.append(",?");
            }
            return sb.toString();
        }
    }

    public static boolean appendWhereIfNeed(StringBuilder sql, boolean hasWhere) {
        if (hasWhere == false) {
            sql.append(" WHERE");
        } else {
            sql.append(" AND");
        }
        return true;
    }
}
