package com.longruan.mobile.appframe.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.longruan.mobile.appframe.R;

/**
 * Created by Administrator on 2017/7/14 0014.
 */

public class DialogUtil {
    ProgressDialog mProgress;
    public ProgressDialog ShowUpadateDialog(final Context mContext){

        ProgressDialog mProgress = new ProgressDialog(mContext);
        mProgress.setTitle("更新进度");
        mProgress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        return mProgress;

    }

    public AlertDialog showDialog(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context,R.style.add_dialog);
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        final View layout = inflater.inflate(R.layout.loading_dialog, null);//获取自定义布局
        builder.setView(layout);
        AlertDialog dlg = builder.create();
        dlg.setCanceledOnTouchOutside(false);
        Window dialogWindow = dlg.getWindow();
/*实例化Window*/
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
/*实例化Window操作者*/
        lp.x = 100; // 新位置X坐标
        lp.y = 100; // 新位置Y坐标
        dialogWindow.setAttributes(lp);
        return dlg;
    }
    public  AlertDialog openConfirmDialog(Context context, String title,
                                                String msg, String okbutton, DialogInterface.OnClickListener ok, String nobutton,
                                                DialogInterface.OnClickListener no) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage("\n" + msg + "\n");
        builder.setNegativeButton(okbutton, ok);
        builder.setNeutralButton(nobutton, no);
        AlertDialog loadWaitDialog = builder.create();
        loadWaitDialog.setCanceledOnTouchOutside(false);
        loadWaitDialog.show();
        return loadWaitDialog;

    }

}
