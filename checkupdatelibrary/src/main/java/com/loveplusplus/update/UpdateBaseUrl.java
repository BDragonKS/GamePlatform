package com.loveplusplus.update;

/**
 * <dl>  Class Description
 * <dd> 项目名称：QJYInspect
 * <dd> 类名称：
 * <dd> 类描述：
 * <dd> 创建时间：2018/6/25
 * <dd> 修改人：无
 * <dd> 修改时间：无
 * <dd> 修改备注：无
 * </dl>
 *
 * @author Jing Lu
 * @version 1.0
 */
public class UpdateBaseUrl {

    public static void updateBaseUrl(String baseUrl) {
        Constants.CHECK_UPDATE_BASE_URL = baseUrl;
        Constants.UPDATE_URL = baseUrl + "update.json";
    }
}
