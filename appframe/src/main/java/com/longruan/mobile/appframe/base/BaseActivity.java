package com.longruan.mobile.appframe.base;

import com.longruan.mobile.appframe.R;
import com.longruan.mobile.appframe.constant.Constant;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import butterknife.ButterKnife;
import butterknife.Unbinder;


/***
 * 本项目所有activity的直接父类
 * <dl>
 * Class Description
 * <dd>项目名称：AppFrame
 * <dd>类名称：BaseActivity
 * <dd>类描述： 本项目所有activity的直接父类，此类抽取了所有activity的共性，并进行了一些初始化操作。
 *              注意：1.如果子类需要展现菜单栏，需要子类Activity去实现onCreateOptionsMenu方法；
 *                    2.如果菜单栏只有一个操作，需要显示文字，则在Menu文件中<item/>不设置android:icon属性，设置此属性则显示该操作的Icon。
 * <dd>创建时间：2017年6月6日10:29:58
 * <dd>修改人：无
 * <dd>修改时间：无
 * <dd>修改备注：无
 * </dl>
 *
 * @author lujing
 * @version 1.0
 */
public abstract class BaseActivity extends AppCompatActivity {

    //    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    //    @BindView(R.id.tv_title)
    TextView tv_title;
    //    @BindView(R.id.f_content)
    FrameLayout f_content;
    private Unbinder mUnbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContent(R.layout.activity_demo, true, "test");
        // 在当前的activity中注册广播
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constant.EXITAPP);
        this.registerReceiver(this.broadcastReceiver, filter);
    }

    protected BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            finish();
        }
    };

    /**
     * 子类Activity继承BaseActivity都需调用此方法，设置布局以及标题栏属性,默认显示返回按钮
     *
     * @param resId 布局文件ID
     * @param title 标题栏的标题
     */
    public void initToolbar(int resId, String title) {
        setContentView(R.layout.activity_base);
        f_content = (FrameLayout) findViewById(R.id.f_content);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        tv_title = (TextView) findViewById(R.id.tv_title);

        //Fragment添加布局
        View contentView = View.inflate(this, resId, null);
        f_content.addView(contentView);
        //添加注解框架
        mUnbinder = ButterKnife.bind(this);
        //toolbar的相关设置
        setSupportActionBar(mToolbar);
        ActionBar bar = getSupportActionBar();
        if (bar != null) {
            bar.setDisplayShowTitleEnabled(false);
            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        }

        //设置标题
        tv_title.setText(title);
    }

    /**
     * 子类Activity继承BaseActivity都需调用此方法，设置布局以及标题栏属性
     *
     * @param resId    布局文件ID
     * @param showBack 是否显示返回按钮，true：显示；false：不显示
     * @param title    标题栏的标题
     */
    public void initToolbar(int resId, boolean showBack, String title) {
        setContentView(R.layout.activity_base);
        f_content = (FrameLayout) findViewById(R.id.f_content);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        tv_title = (TextView) findViewById(R.id.tv_title);

        //Fragment添加布局
        View contentView = View.inflate(this, resId, null);
        f_content.addView(contentView);

        //添加注解框架
        mUnbinder = ButterKnife.bind(this);
        //toolbar的相关设置
        setSupportActionBar(mToolbar);
        ActionBar bar = getSupportActionBar();
        if (bar != null) {
            bar.setDisplayShowTitleEnabled(false);
            if (!showBack) {
                bar.setDisplayHomeAsUpEnabled(false);
            } else {
                mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onBackPressed();
                    }
                });
            }
        }

        //设置标题
        tv_title.setText(title);
    }

    /**
     * 设置布局，隐藏toolbar
     *
     * @param resId 布局文件ID
     */
    public void initNoToolbar(int resId) {
        setContentView(R.layout.activity_base);
        f_content = (FrameLayout) findViewById(R.id.f_content);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        tv_title = (TextView) findViewById(R.id.tv_title);
        mToolbar.setVisibility(View.GONE);
        tv_title.setVisibility(View.GONE);

        //Fragment添加布局
        View contentView = View.inflate(this, resId, null);
        f_content.addView(contentView);

        //添加注解框架
        mUnbinder = ButterKnife.bind(this);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
        this.unregisterReceiver(this.broadcastReceiver);
    }

    // 点击键盘外区域自动隐藏键盘
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        return getWindow().superDispatchTouchEvent(ev) || onTouchEvent(ev);

    }


    /**
     * 是否保留点击EditText的事件
     *
     * @param v     组件
     * @param event 点击事件
     * @return boolean false：保留，true：不保留。
     */
    public boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            // 获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            return !(event.getX() > left && event.getX() < right && event.getY() > top && event.getY() < bottom);
        }
        return false;
    }

    /***
     * 判断app是否处在前台
     *
     * @return boolean true:在前台；false：不在前台。
     */
    public boolean isAppOnForeground() {
        // Returns a list of application processes that are running on the
        // device

        ActivityManager activityManager = (ActivityManager) getApplicationContext()
                .getSystemService(Context.ACTIVITY_SERVICE);
        String packageName = getApplicationContext().getPackageName();

        List<RunningAppProcessInfo> appProcesses = activityManager
                .getRunningAppProcesses();
        if (appProcesses == null) {
            return false;
        }
        for (RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.processName.equals(packageName)
                    && appProcess.importance == RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return true;
            }
        }

        return false;
    }


    /**
     * 默认当前时间的时间选择器
     *
     * @param context         当前activity上下文对象
     * @param dateSetListener 选择回调监听
     */
    public void showDatePicker(Context context,
                               OnDateSetListener dateSetListener) {
        DatePickerDialog datePickerDialog;

        Calendar calendar = Calendar.getInstance(Locale.CHINA);

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        datePickerDialog = new DatePickerDialog(context, dateSetListener,
                year, month, day);
        datePickerDialog.show();
    }

    /**
     * <b>方法描述： 显示选择日期的对话框</b>
     * <dd>方法作用：
     * <dd>适用条件：
     * <dd>执行流程：
     * <dd>使用方法：
     * <dd>注意事项：
     * 2016-4-14下午2:03:47
     *
     * @param date 字符串的日期格式（yyyy-MM-dd） null默认显示当前日期
     * @since Met 1.0
     */
    public void showDatePicker(Context context,
                               OnDateSetListener dateSetListener, String date, String minDate) {
        DatePickerDialog datePickerDialog;
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        if (!TextUtils.isEmpty(date)) {
            String[] dates = date.split("-");
            if (dates.length > 2) {
                year = Integer.valueOf(dates[0]);
                month = Integer.valueOf(dates[1]) - 1;
                day = Integer.valueOf(dates[2]);
                calendar.set(year, month, day);
            }
        }

        datePickerDialog = new DatePickerDialog(context, dateSetListener,
                year, month, day);
        if (!TextUtils.isEmpty(minDate)) {

            Calendar calendarMin = Calendar.getInstance(Locale.CHINA);
            String[] dates = date.split("-");
            if (dates.length > 2) {
                calendarMin.set(Integer.valueOf(dates[0]), Integer.valueOf(dates[1]) - 1, Integer.valueOf(dates[2]));
            }
            datePickerDialog.getDatePicker().setMinDate(calendarMin.getTimeInMillis());
        }
        datePickerDialog.show();
    }

    /**
     * <b>方法描述： 显示选择月份的对话框</b>
     * <dd>方法作用：
     * <dd>适用条件：
     * <dd>执行流程：
     * <dd>使用方法：
     * <dd>注意事项：
     * 2016-4-14下午2:03:47
     *
     * @param date 字符串的日期格式（yyyy-MM） null默认显示当前日期
     * @since Met 1.0
     */
    public void showDatePickerDissmissDay(Context context,
                                          OnDateSetListener dateSetListener, String date, String minDate) {
        DatePickerDialog datePickerDialog;
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        if (!TextUtils.isEmpty(date)) {
            String[] dates = date.split("-");
            if (dates.length > 1) {
                year = Integer.valueOf(dates[0]);
                month = Integer.valueOf(dates[1]) - 1;
//                day = Integer.valueOf(dates[2]);
                calendar.set(year, month, day);
            }
        }

        datePickerDialog = new DatePickerDialog(context, dateSetListener,
                year, month, day);
        if (!TextUtils.isEmpty(minDate)) {

            Calendar calendarMin = Calendar.getInstance(Locale.CHINA);
            String[] dates = date.split("-");
            if (dates.length > 2) {
                calendarMin.set(Integer.valueOf(dates[0]), Integer.valueOf(dates[1]) - 1, Integer.valueOf(dates[2]));
            }
            datePickerDialog.getDatePicker().setMinDate(calendarMin.getTimeInMillis());
        }
        datePickerDialog.show();
        ((ViewGroup) ((ViewGroup) (datePickerDialog.getDatePicker().getChildAt(0))).getChildAt(0)).getChildAt(1)
                .setVisibility(View.GONE);

        /*//只显示年月，隐藏掉日
        DatePicker dp = findDatePicker((ViewGroup) datePickerDialog.getWindow().getDecorView());
        if (dp != null) {
            ((ViewGroup)((ViewGroup)dp.getChildAt(0)).getChildAt(0))
                    .getChildAt(2).setVisibility(View.GONE);
            //如果想隐藏掉年，将getChildAt(2)改为getChildAt(0)
        }*/
    }

    public void setBaseTitle(String title) {
        if (tv_title != null) {
            tv_title.setText(title);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        BaseActivity.this.overridePendingTransition(
                R.anim.push_right_in, R.anim.push_right_out);
    }

    public abstract void initInject();

    public abstract AbsBasePresenter getPresenter();

    private DatePicker findDatePicker(ViewGroup group) {
        if (group != null) {
            for (int i = 0, j = group.getChildCount(); i < j; i++) {
                View child = group.getChildAt(i);
                if (child instanceof DatePicker) {
                    return (DatePicker) child;
                } else if (child instanceof ViewGroup) {
                    DatePicker result = findDatePicker((ViewGroup) child);
                    if (result != null)
                        return result;
                }
            }
        }
        return null;
    }

}
