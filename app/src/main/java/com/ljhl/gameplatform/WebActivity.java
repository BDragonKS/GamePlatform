package com.ljhl.gameplatform;

import com.longruan.mobile.appframe.base.AbsBasePresenter;
import com.longruan.mobile.appframe.base.BaseActivity;
import com.longruan.mobile.appframe.utils.NetWorkUtils;

import android.Manifest;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.support.v4.content.FileProvider;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.webkit.CookieManager;
import android.webkit.DownloadListener;
import android.webkit.SslErrorHandler;
import android.webkit.URLUtil;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.File;

import butterknife.BindView;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.RuntimePermissions;

/**
 * 用于web浏览的activity
 */
@RuntimePermissions
public class WebActivity extends BaseActivity {

    public static final String EXTRA_WEB_TITLE = "extra_web_title";
    public static final String EXTRA_WEB_URL = "extra_web_url";
    public static final String EXTRA_CONTENT_TYPE = "extra_content_type";

    private boolean isContinue = false;
    private String mUrl = "";

    @BindView(R.id.web_view)
    WebView webView;
    @BindView(R.id.top_progress)
    WebProgressBar webProgressBar;
    private DownloadCompleteReceiver receiver;
    private String fileName;
    private String cookieStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initNoToolbar(R.layout.activity_web);
        mUrl = Constants.BASE_URL;
        initWebView();

        receiver = new DownloadCompleteReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        registerReceiver(receiver, intentFilter);
    }

    @Override
    public void initInject() {

    }

    @Override
    public AbsBasePresenter getPresenter() {
        return null;
    }

    public static void startBrowse(Context context, String title, String url, int contentType) {
        Intent intent = new Intent(context, WebActivity.class);
        intent.putExtra(EXTRA_WEB_TITLE, title);
        intent.putExtra(EXTRA_WEB_URL, url);
        intent.putExtra(EXTRA_CONTENT_TYPE, contentType);
        context.startActivity(intent);
    }

    private void initWebView() {

        webView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {

//                UpdateDialog.goToDownload(WebActivity.this, url);
                // 用手机默认浏览器打开链接
               /* Uri uri = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);*/
                WebActivityPermissionsDispatcher.downloadBySystemWithPermissionCheck(WebActivity.this, url, contentDisposition, "application/vnd.android.package-archive");
            }
        });
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(false);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);

        //设置是否显示缩放按钮
        settings.setBuiltInZoomControls(true);
        //设置可以缩放
        settings.setSupportZoom(true);
        settings.setDisplayZoomControls(false);
        //扩大比例的缩放
        settings.setUseWideViewPort(true);
//        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setLoadWithOverviewMode(true);
        settings.supportMultipleWindows();
        settings.setSupportMultipleWindows(true);

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);

                if (!NetWorkUtils.isNetworkAvailable(WebActivity.this)) {
                    return;
                }
                if (webProgressBar != null) {

                    //如果进度条隐藏则让它显示
                    if (View.INVISIBLE == webProgressBar.getVisibility()) {
                        webProgressBar.setVisibility(View.VISIBLE);
                    }
                }
                //大于80的进度的时候,放慢速度加载,否则交给自己加载
                if (newProgress >= 80 && webProgressBar != null) {
                    //拦截webView自己的处理方式
                    if (isContinue) {
                        return;
                    }
                    webProgressBar.setCurProgress(100, 1000, new WebProgressBar.OnEndListener() {
                        @Override
                        public void onEnd() {
                            finishOperation(true);
                            isContinue = false;
                        }
                    });

                    isContinue = true;
                } else if (webProgressBar != null) {
                    webProgressBar.setNormalProgress(newProgress);
                }
            }
        });

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();
            }

            @Override
            public void onReceivedError(WebView view, int errorCode,
                                        String description, String failingUrl) {
                errorOperation();
            }

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(request.getUrl().toString());
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                CookieManager cookieManager = CookieManager.getInstance();
                cookieStr = cookieManager.getCookie(url); // 获取到cookie字符串值
            }
        });

        //开始加载
        webView.loadUrl(mUrl);
    }


    /**
     * 错误的时候进行的操作
     */
    private void errorOperation() {
        if (View.INVISIBLE == webProgressBar.getVisibility()) {
            webProgressBar.setVisibility(View.VISIBLE);
        }
        webProgressBar.setCurProgress(80, 1000, new WebProgressBar.OnEndListener() {
            @Override
            public void onEnd() {
                webProgressBar.setCurProgress(100, 1000, new WebProgressBar.OnEndListener() {
                    @Override
                    public void onEnd() {
                        finishOperation(false);
                    }
                });
            }
        });
    }

    /**
     * 结束进行的操作
     */
    private void finishOperation(boolean flag) {
        //最后加载设置100进度
        if (webProgressBar != null) {

            webProgressBar.setNormalProgress(100);
        }
        hideProgressWithAnim();
    }

    /**
     * 隐藏加载进度条
     */
    private void hideProgressWithAnim() {
        AnimationSet animation = getDismissAnim(WebActivity.this);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                webProgressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        if (webProgressBar != null) {

            webProgressBar.startAnimation(animation);
        }
    }

    /**
     * 获取消失的动画
     */
    private AnimationSet getDismissAnim(Context context) {
        AnimationSet dismiss = new AnimationSet(context, null);
        AlphaAnimation alpha = new AlphaAnimation(1.0f, 0.0f);
        alpha.setDuration(1000);
        dismiss.addAnimation(alpha);
        return dismiss;
    }

    @NeedsPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    public void downloadBySystem(String url, String contentDisposition, String mimeType) {
        // 指定下载地址
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        // 允许媒体扫描，根据下载的文件类型被加入相册、音乐等媒体库
        request.allowScanningByMediaScanner();
        // 设置通知的显示类型，下载进行时和完成后显示通知
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        // 设置通知栏的标题，如果不设置，默认使用文件名
//        request.setTitle("This is title");
        // 设置通知栏的描述
//        request.setDescription("This is description");
        // 允许在计费流量下下载
        request.setAllowedOverMetered(false);
        // 允许该记录在下载管理界面可见
        request.setVisibleInDownloadsUi(false);
        // 允许漫游时下载
        request.setAllowedOverRoaming(true);
        // 允许下载的网路类型
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI);
        // 设置下载文件保存的路径和文件名
        fileName = URLUtil.guessFileName(url, contentDisposition, mimeType);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName);
//        另外可选一下方法，自定义下载路径
//        request.setDestinationUri()
//        request.setDestinationInExternalFilesDir()
        final DownloadManager downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        // 添加一个下载任务
        long downloadId = downloadManager.enqueue(request);
    }

    private class DownloadCompleteReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
                if (DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(intent.getAction())) {
                    DownloadManager downloadManager = (DownloadManager) context.getSystemService(DOWNLOAD_SERVICE);
                    Intent handlerIntent = new Intent(Intent.ACTION_VIEW);
                    long downloadId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
//                    String type = downloadManager.getMimeTypeForDownloadedFile(downloadId);
//                    if (TextUtils.isEmpty(type)) {
//                        type = "*/*";
//                    }
                    Uri uri = downloadManager.getUriForDownloadedFile(downloadId);
                    if (uri != null) {

                        handlerIntent.setDataAndType(uri, "application/vnd.android.package-archive");

                        if ((Build.VERSION.SDK_INT >= 24)) {//判断版本是否在7.0以上
                            //添加这一句表示对目标应用临时授权该Uri所代表的文件
                            handlerIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        }
                        handlerIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        if (handlerIntent.resolveActivity(context.getPackageManager()) != null) {
                            context.startActivity(handlerIntent);
                        }
                    }
                } else if (DownloadManager.ACTION_NOTIFICATION_CLICKED.equals(intent.getAction())) {

                }
            }
        }
    }

    private void installAPk() {

        File apkFile = new File(Environment.DIRECTORY_DOWNLOADS, "绿茵战神v.1.0.apk");
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(
                    WebActivity.this
                    , "com.ljhl.gameplatform.fileprovider"
                    , apkFile);
            intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive");
        }
        startActivity(intent);
    }

    //使用Webview的时候，返回键没有重写的时候会直接关闭程序，这时候其实我们要其执行的知识回退到上一步的操作
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //这是一个监听用的按键的方法，keyCode 监听用户的动作，如果是按了返回键，同时Webview要返回的话，WebView执行回退操作，因为mWebView.canGoBack()返回的是一个Boolean类型，所以我们把它返回为true
        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
            webView.goBack();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }
}
