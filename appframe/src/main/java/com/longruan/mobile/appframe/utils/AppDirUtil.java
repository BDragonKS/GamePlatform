package com.longruan.mobile.appframe.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;

import java.io.File;
import java.io.IOException;

/**
 * <dl>  Class Description
 * <dd> 项目名称：AppFrame
 * <dd> 类名称：AppDirUtil
 * <dd> 类描述：程序文件目录工具
 * <dd> 创建时间：2017/6/7
 * <dd> 修改人：无
 * <dd> 修改时间：无
 * <dd> 修改备注：无
 * </dl>
 *
 * @author ljhl
 * @version 1.0
 */

public class AppDirUtil {

    /**
     * <b>方法描述： 删除numDays时间点前的指定路径下的缓存数据</b>
     * <dd>方法作用：
     * <dd>适用条件：
     * <dd>执行流程：
     * <dd>使用方法：
     * <dd>注意事项：
     * 2016-6-16下午2:09:35
     *
     * @param numDays 删除某个毫秒值前的数据（ System.currentTimeMillis()删除当前时间前的所有数据）
     * @since Met 1.0
     */
    public static int clearCacheFolder(File dir, long numDays) {
        int deletedFiles = 0;
        if (dir != null && dir.isDirectory()) {
            try {
                for (File child : dir.listFiles()) {
                    if (child.isDirectory()) {
                        deletedFiles += clearCacheFolder(child, numDays);
                    } else if (child.lastModified() < numDays) {
                        if (child.delete()) {
                            deletedFiles++;
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return deletedFiles;
    }

    /***
     *得到系统缓存文件夹的路径
     * @param context 上下文对象
     * @return 文件路径
     */
    @SuppressLint("NewApi")
    public static String getCacheDirPath(Context context) {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            return context.getApplicationContext().getExternalCacheDir()
                    .getPath();
        } else {
            return context.getApplicationContext().getCacheDir().getPath();
        }
    }

    /***
     * 创建存放在缓存目录下的/image/imageFileFolderName/imageName.jpg的file对象
     * @param context
     * @param imageFileFolderName 文件夹名字
     * @param imageName 文件名字
     * @return
     */
    public static File creatImageFile(Context context, String imageFileFolderName, String imageName) {
        String path = getCacheDirPath(context);
        File imagePath = new File(path + "/image/" + imageFileFolderName + "/");
        if (!imagePath.exists()) {
            imagePath.mkdirs();
        }
        File imageFile = new File(imagePath, imageName + ".jpg");
        if (imageFile.exists()) {
            imageFile.delete();
        }
        try {
            imageFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imageFile;
    }


    /***
     * 删除指定路径的文件
     * @param _path
     */
    public static void deleteFile(String _path) {
        if (TextUtils.isEmpty(_path)) {
            return;
        }

        File file = new File(_path);
        if (file.exists()) {
            file.delete();
        }
    }

    /**
     * 获取SD卡的地址
     *
     * @return 若未取到则返回空字符串
     */
    @SuppressLint("NewApi")
    public static String getSDDir() {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            return Environment.getExternalStorageDirectory().getPath();
//                return System.getenv("SECONDARY_STORAGE");
        } else {
            return "";
        }
//        return FrameApplication.getInstance().getApplicationContext().getExternalCacheDir().getPath();
    }

    /**
     * <b>方法描述： 返回下载文件存放的路径，如果没有创建download，则创建</b>
     * <dd>方法作用：
     * <dd>适用条件：
     * <dd>执行流程：
     * <dd>使用方法：
     * <dd>注意事项：
     *
     * @see
     * @since Met 1.0
     */
    public static String getDownloadPath() {
        File file = new File(AppDirUtil.getSDDir() + File.separator + PropertyUtil.getInstance().getValue("projectName") + "/download/");
        if (!file.exists()) {
            file.mkdirs();
        }
        return file.getAbsolutePath();
    }

    /**
     * <b>方法描述： 返回崩溃日志存储目录</b>
     * <dd>方法作用：
     * <dd>适用条件：
     * <dd>执行流程：
     * <dd>使用方法：
     * <dd>注意事项：
     * 2016-4-27下午3:30:03
     *
     * @see
     * @since Met 1.0
     */
    public static String getCrashLogPath() {
        File file = new File(AppDirUtil.getSDDir() + "/" + "longruan/hnny/" + "crash");
        if (!file.exists()) {
            file.mkdirs();
        }
        return file.getAbsolutePath();
    }

    /***
     * <b>方法描述：检查指定文件是否存在 </b>
     * <dd>方法作用：
     * <dd>适用条件：
     * <dd>执行流程：
     * <dd>使用方法：
     * <dd>注意事项：
     * @param fileName 要检查的文件名
     * @param parentPath 文件所在目录
     * @return true:存在该文件；false：不存在此文件
     * @since Met 1.0
     * @see
     */
    public static boolean isFileExist(String fileName, String parentPath) {
        File[] files = null;//声明一个File数组
        File parentFile = new File(parentPath);
        if (!parentFile.exists()) {
            return false;
        } else {
            files = parentFile.listFiles();
            for (File file : files) {
                if (fileName.equals(file.getName())) {
                    return true;
                }
            }
        }
        return false;
    }

//    /**
//     * 获取SD卡的地址
//     *
//     * @return 若未取到则返回空字符串
//     */
//    @SuppressLint("NewApi")
//    public static String getDicmDir() {
//        return FrameApplication.getInstance().getApplicationContext().getExternalFilesDir().getPath();
//    }
}
