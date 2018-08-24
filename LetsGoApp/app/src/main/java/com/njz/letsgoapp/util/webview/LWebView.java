package com.njz.letsgoapp.util.webview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

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
        WebSettings settings = getSettings();
        settings.setJavaScriptEnabled(true);//支持js
        settings.setDisplayZoomControls(false); // 关闭自动缩放
        settings.setDefaultZoom(WebSettings.ZoomDensity.FAR); //  自适应屏幕处理，不设置，低分辨率显示异常
        settings.setDefaultTextEncodingName("utf-8");
//        settings.setCacheMode(WebSettings.LOAD_NO_CACHE); //不使用缓存

        settings.setAllowFileAccess(true); // 允许访问文件
        settings.setDomStorageEnabled(true); // h5 本地缓存
        settings.setDatabaseEnabled(true); //启用数据库

        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
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
