package com.longruan.mobile.appframe.utils;

import com.longruan.mobile.appframe.FrameApplication;
import com.longruan.mobile.appframe.constant.Constant;

import java.nio.charset.Charset;

/**
 * <dl>  Class Description
 * <dd> 项目名称：mea
 * <dd> 类名称：
 * <dd> 类描述：
 * <dd> 创建时间：2018/2/5
 * <dd> 修改人：无
 * <dd> 修改时间：无
 * <dd> 修改备注：无
 * </dl>
 *
 * @author Jing Lu
 * @version 1.0
 */

public class EncodeUtil {

    /**
     * 加密明文
     *
     * @param s 需要加密的明文
     * @return 加密后的密文
     */
    public static String encode(String s) {
        int appLocation = FrameApplication.getInstance().getAppLocation();
        String encodeStr = "";
        switch (appLocation) {
            case Constant.LOCATION_GZAQY:
                encodeStr = AESEnconder.encode(s, "safecloud".getBytes(Charset.forName("ISO-8859-1")));
                break;
            case Constant.LOCATION_XJBT:

                encodeStr = MD5Encoder.makeMD5(s);
                break;
        }
        return encodeStr;
    }
}
