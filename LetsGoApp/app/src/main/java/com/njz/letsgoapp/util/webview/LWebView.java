package com.njz.letsgoapp.util.webview;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

/**
 * Class Name: LWebView.java
 * <p>
 * Function:
 * <p>
 * version 1.0
 * since  2017/02/13 13:57
 */

public class LWebView extends WebView {
    private View mErrorView;
    private Context context;

    public LWebView(Context context) {
        this(context, null);
    }

    public LWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public LWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    /**
     *
     */
    private void init() {
        /*
             支持Js
             setting.setJavaScriptEnabled(true);

             设置自适应屏幕，两者合用
             //将图片调整到适合webview的大小 setting.setUseWideViewPort(true);
             // 缩放至屏幕的大小   setting.setLoadWithOverviewMode(true);

             缩放操作
             // 是否支持画面缩放，默认不支持 setting.setBuiltInZoomControls(true); setting.setSupportZoom(true);
             // 是否显示缩放图标，默认显示  setting.setDisplayZoomControls(false);
             // 设置网页内容自适应屏幕大小  setting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);

             设置允许JS弹窗
             setting.setJavaScriptCanOpenWindowsAutomatically(true);
             setting.setDomStorageEnabled(true);

             关闭webview中缓存
             setting.setCacheMode(WebSettings.LOAD_NO_CACHE);

             设置可以访问文件
             setting.setAllowFileAccess(true);
             setting.setAllowFileAccessFromFileURLs(true);
             setting.setAllowUniversalAccessFromFileURLs(true);
         */


        WebSettings settings = getSettings();
        settings.setJavaScriptEnabled(true);//支持js
        settings.setDisplayZoomControls(false); // 关闭自动缩放
        settings.setDefaultZoom(WebSettings.ZoomDensity.FAR); //  自适应屏幕处理，不设置，低分辨率显示异常
        settings.setDefaultTextEncodingName("utf-8");

        settings.setAllowFileAccess(true); // 允许访问文件
        settings.setDomStorageEnabled(true); // h5 本地缓存
        settings.setDatabaseEnabled(true); //启用数据库

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//Android5.0上 WebView中Http和Https混合问题
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        this.setVerticalScrollBarEnabled(false);
        this.setVerticalScrollbarOverlay(false);
        this.setHorizontalScrollBarEnabled(false);
        this.setHorizontalScrollbarOverlay(false);


        setWebViewClient(new MyWebViewClient(this));

//        setWebViewClient(new WebViewClient() {
//            @Override
//            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
//                view.loadUrl("file:///android_asset/error.html");
//            }
//
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                // 不重写会调用系统浏览器
//                view.loadUrl(url);
//                return true;
//            }
//
//            @Override
//            public void onPageFinished(WebView view, String url) {
//                super.onPageFinished(view, url);
//
//            }
//        });
    }
}
