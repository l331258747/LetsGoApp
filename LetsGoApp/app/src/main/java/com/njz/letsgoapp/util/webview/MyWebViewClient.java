package com.njz.letsgoapp.util.webview;

import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

//图片过大处理
public class MyWebViewClient extends WebViewClient {

    WebView webView;

    public MyWebViewClient(WebView webView) {
        this.webView = webView;
    }

    @Override
    public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
        view.loadUrl("file:///android_asset/error.html");
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        imgReset();//重置webview中img标签的图片大小
        viewMeasure();
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        // 不重写会调用系统浏览器
//        view.loadUrl(url);//跳转
        return true;
    }

    /**
     * 对图片进行重置大小，宽度就是手机屏幕宽度，高度根据宽度比便自动缩放
     **/
    private void imgReset() {
        webView.loadUrl("javascript:(function(){" +
                "var objs = document.getElementsByTagName('img'); " +
                "for(var i=0;i<objs.length;i++)  " +
                "{"
                + "var img = objs[i];   " +
                "    img.style.width = '100%'; img.style.height = 'auto';  " +
                "}" +
                "})()");
    }

    private void viewMeasure(){
        int w = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        //重新测量
        webView.measure(w, h);
    }
}