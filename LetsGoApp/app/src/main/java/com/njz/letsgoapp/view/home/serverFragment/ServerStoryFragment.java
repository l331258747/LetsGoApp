package com.njz.letsgoapp.view.home.serverFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;

import com.njz.letsgoapp.R;
import com.njz.letsgoapp.base.BaseFragment;
import com.njz.letsgoapp.util.webview.LWebView;

/**
 * Created by LGQ
 * Time: 2018/12/4
 * Function:
 */

public class ServerStoryFragment extends BaseFragment {

    LWebView webView;
    String story;

    public static Fragment newInstance(String story) {
        ServerStoryFragment fragment = new ServerStoryFragment();
        Bundle bundle = new Bundle();
        bundle.putString("story", story);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            story = bundle.getString("story");
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_service_story;
    }

    @Override
    public void initView() {
        webView = $(R.id.webview);
    }

    @Override
    public void initData() {
        if (!TextUtils.isEmpty(story))
            webView.loadDataWithBaseURL(null, story, "text/html", "utf-8", null);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//         webview 需要加载空界面来释放资源
        webView.loadUrl("about:blank");
        webView.clearCache(false);
        webView.destroy();
    }
}
