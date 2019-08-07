package com.njz.letsgoapp.view.other;

import android.content.Intent;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.njz.letsgoapp.R;
import com.njz.letsgoapp.base.BaseActivity;
import com.njz.letsgoapp.constant.Constant;
import com.njz.letsgoapp.util.webview.LWebView;


/**
 * Class Name: WebViewActivity.java
 * <p>
 * Function: 通用的 WebView 界面
 * <p>
 * version 1.0
 * since  2017/02/20 17:49
 */
public class WebViewActivity extends BaseActivity {
	private ProgressBar progressBar;
	private LWebView webView;
	private String url = "";
	private String title = "";
	private boolean isUseWideViewPort;

	public LWebView getWebView() {
		return webView;
	}

	@Override
	public int getLayoutId() {
		return R.layout.webview_layout;
	}

	public void getIntentData() {
		Intent intent = getIntent();
		url = intent.getStringExtra(Constant.EXTRA_URL);
		title = intent.getStringExtra(Constant.EXTRA_TITLE);
		isUseWideViewPort = intent.getBooleanExtra(Constant.IS_USE_WIDE_VIEW_PORT,false);
		if(TextUtils.isEmpty(url)){
			url = "https://jingyan.baidu.com/article/6525d4b179af49ac7d2e94a1.html";
		}
		if(TextUtils.isEmpty(title)) title = "那就走";
	}

	@Override
	public void initView() {
		showLeftAndTitle(title);
		leftIv.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				WebViewActivity.super.onBackPressed();
			}
		});

		progressBar = $(R.id.progressbar);

		webView = $(R.id.webview);
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){//WebView默认不支持同时加载Https和Http混合模式
			webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE);
		}
		if(isUseWideViewPort){
			webView.getSettings().setUseWideViewPort(true);
			webView.getSettings().setLoadWithOverviewMode(true);
		}

		webView.setWebChromeClient(new WebChromeClient() {
			@Override
			public void onReceivedTitle(WebView view, String title) {
				super.onReceivedTitle(view, title);
//				getTitleTv().setText(title);
			}

			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				super.onProgressChanged(view, newProgress);
				progressBar.setProgress(newProgress);

				if (newProgress == 100) {
					progressBar.setVisibility(View.GONE);
				}
			}
		});

		if (!TextUtils.isEmpty(url)) {
			if(url.startsWith("http") || url.startsWith("https"))
				webView.loadUrl(url);
			else{
				url = "http://"+url;
				webView.loadUrl(url);
			}

			Log.e("li", "url:" + url);
		} else {
//			webView.loadUrl("file:///android_asset/error.html");
		}
	}

	@Override
	public void initData() {
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// webview 需要加载空界面来释放资源
		webView.loadUrl("about:blank");
		webView.clearCache(false);
		webView.destroy();
	}

	@Override
	public void onBackPressed() {
		// 搜索可以返回
//		if (webView.canGoBack()) {
//			webView.goBack();
//		} else {
//			super.onBackPressed();
//		}
		super.onBackPressed();
	}
}
