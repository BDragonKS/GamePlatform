package com.longruan.mobile.appframe.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import okhttp3.Request;
import okhttp3.Response;

/**
 * 文件操作类
 *
 * @author licong
 */
public class FileUtils {
    public static final String SDPATH = Environment.getExternalStorageDirectory() + "/";

    /* 判断SD卡是否存在 */
    public static boolean ExistSDCard() {
        if (android.os.Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED)) {
            return true;
        } else
            return false;
    }

    /**
     * 在SD卡上创建文件
     */
    public static File creatSDFile(String filePath) throws IOException {
        File file = new File(filePath);
        file.createNewFile();
        return file;
    }

    /**
     * 在SD卡上创建目录
     */
    public static File creatSDDir(String dirName) {
        File dir = new File(SDPATH + dirName);
        dir.mkdirs();
        return dir;
    }

    /**
     * 判断SD卡上的文件夹是否存在
     */
    public static boolean isFileExist(String fileName) {
        File file = new File(SDPATH + fileName);
        return file.exists();
    }

    /**
     * 判断文件是否存在
     *
     * @param savepath 文件夹的路径
     * @param fileName 文件的名字及后缀名
     */
    public static boolean isFilesExist(String savepath, String fileName) {
        File file = new File(savepath + fileName);
        return file.exists();
    }

    /**
     * 将一个InputStream里面的数据写入到SD卡中
     */
    public static File write2SDFromInput(String path, String fileName,
                                         InputStream input) {
        File file = null;
        OutputStream output = null;
        /**
         * 方法一
         */
        // try {
        // file = creatSDFile(path + fileName);
        // output = new FileOutputStream(file);
        // byte buf[] = new byte[1024];
        // do {
        // // 循环读取
        // int numread = input.read(buf);
        // if (numread == -1) {
        // break;
        // }
        // output.write(buf, 0, numread);
        // } while (true);
        // /* 把buf缓存清空写到文件中去 */
        // output.flush();
        // } catch (Exception e) {
        // e.printStackTrace();
        // } finally {
        // try {
        // /* 关闭已经打开的输出流 */
        // output.close();
        // /* 关闭已经打开的输入流 */
        // input.close();
        // } catch (Exception e) {
        // e.printStackTrace();
        // }
        // }

        /**
         * 方法二,while循环里面的条件有待研究，如果按底下问题代码写，下载下来的文件乱码，一个朋友说问题代码没有真正读文件，
         * 只是在最开始的时候读取了一个buffer大小的数据，后面while循环没有读
         */
        try {
            file = creatSDFile(path + fileName);
            output = new FileOutputStream(file);
            byte buffer[] = new byte[4 * 1024];
            int numread = -1;
            while ((numread = input.read(buffer)) != -1) {
                output.write(buffer, 0, numread);
            }
            // 问题代码
            // while ((input.read(buffer)) != -1) {
            // output.write(buffer);
            // }
            /* 把buf缓存清空写到文件中去 */
            output.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                /* 关闭已经打开的输出流 */
                output.close();
                /* 关闭已经打开的输入流 */
                input.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return file;
    }

    public static String getFileNameFromPath(String path) {
        if (path == null) {
            return null;
        }
        String[] strs = TextUtils.split(path, File.separator);
        if (strs == null || strs.length <= 0) {
            return null;
        }
        return strs[strs.length - 1];
    }

    /**
     * 获取文件扩展名对于的Intent类型
     *
     * @param extensionName 扩展名
     * @return 类型
     */
    public static String getDataType(String extensionName) {
        String strTemp = "";
        switch (extensionName) {
            case ".au":
                strTemp = "audio/*";
                break;
            case ".snd":
                strTemp = "audio/*";
                break;
            case ".mid":
                strTemp = "audio/*";
                break;
            case ".rmi":
                strTemp = "audio/*";
                break;
            case ".mp3":
                strTemp = "audio/*";
                break;
            case ".aif":
                strTemp = "audio/*";
                break;
            case ".aifc":
                strTemp = "audio/*";
                break;
            case ".aiff":
                strTemp = "audio/*";
                break;
            case ".m3u":
                strTemp = "audio/*";
                break;
            case ".ra":
                strTemp = "audio/*";
                break;
            case ".ram":
                strTemp = "audio/*";
                break;
            case ".wav":
                strTemp = "audio/*";
                break;
            case ".bmp":
                strTemp = "image/*";
                break;
            case ".cod":
                strTemp = "image/*";
                break;
            case ".gif":
                strTemp = "image/*";
                break;
            case ".ief":
                strTemp = "image/*";
                break;
            case ".jpe":
                strTemp = "image/*";
                break;
            case ".jpeg":
                strTemp = "image/*";
                break;
            case ".jpg":
                strTemp = "image/*";
                break;
            case ".jfif":
                strTemp = "image/*";
                break;
            case ".svg":
                strTemp = "image/*";
                break;
            case ".tif":
                strTemp = "image/*";
                break;
            case ".tiff":
                strTemp = "image/*";
                break;
            case ".ras":
                strTemp = "image/*";
                break;
            case ".cmx":
                strTemp = "image/*";
                break;
            case ".ico":
                strTemp = "image/*";
                break;
            case ".pnm":
                strTemp = "image/*";
                break;
            case ".pbm":
                strTemp = "image/*";
                break;
            case ".pgm":
                strTemp = "image/*";
                break;
            case ".ppm":
                strTemp = "image/*";
                break;
            case ".rgb":
                strTemp = "image/*";
                break;
            case ".xbm":
                strTemp = "image/*";
                break;
            case ".xpm":
                strTemp = "image/*";
                break;
            case ".xwd":
                strTemp = "image/*";
                break;
            case ".mp2":
                strTemp = "video/*";
                break;
            case ".mpa":
                strTemp = "video/*";
                break;
            case ".mpe":
                strTemp = "video/*";
                break;
            case ".mpeg":
                strTemp = "video/*";
                break;
            case ".mpg":
                strTemp = "video/*";
                break;
            case ".mpv2":
                strTemp = "video/*";
                break;
            case ".mov":
                strTemp = "video/*";
                break;
            case ".qt":
                strTemp = "video/*";
                break;
            case ".lsf":
                strTemp = "video/*";
                break;
            case ".lsx":
                strTemp = "video/*";
                break;
            case ".asf":
                strTemp = "video/*";
                break;
            case ".asr":
                strTemp = "video/*";
                break;
            case ".asx":
                strTemp = "video/*";
                break;
            case ".avi":
                strTemp = "video/*";
                break;
            case ".movie":
                strTemp = "video/*";
                break;
            case ".mp4":
                strTemp = "video/*";
                break;
            /*case ".doc":
                strTemp = "application/msword";
                break;
            case ".docx":
                strTemp = "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
                break;*/
            /*case ".pdf":
                strTemp = "application/pdf";
                break;*/
            case ".xls":
                strTemp = "application/vnd.ms-excel";
                break;
            case ".xlsx":
                strTemp = "application/vnd.ms-excel";
                break;
            case ".xlm":
                strTemp = "application/vnd.ms-excel";
                break;
            case ".ppt":
                strTemp = "application/vnd.ms-powerpoint";
                break;
            default:
                break;
        }
        return strTemp;
    }

    /**
     * 获取图片绝对路径
     *
     * @param context 上下文对象
     * @param uri     地址
     * @return 绝对地址
     */
    public static String getAbsolutePath(Context context, Uri uri) {
        if (null == uri) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null)
            data = uri.getPath();
        else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            Cursor cursor = context.getContentResolver().query(uri,
                    new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data;
    }

    /**
     * 将流写入本地SD
     */
    public static String writeBytesToDisk(final byte[] bytes, final String fileName) {

        File dir = new File(AppDirUtil.getSDDir() + "/" + "longruan/" + "files");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File file = new File(dir + "/" + fileName);
        if (file.exists()) {
            return dir + "/" + fileName;
        }
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(dir + "/" + fileName);
            fos.write(bytes);
            fos.flush();
/*
            MediaScannerConnection.scanFile(mContext,
                    new String[]{dir + "/" + fileName}, null,
                    new MediaScannerConnection.OnScanCompletedListener() {
                        public void onScanCompleted(String path, Uri uri) {
                        }
                    });*/
            return dir + "/" + fileName;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
