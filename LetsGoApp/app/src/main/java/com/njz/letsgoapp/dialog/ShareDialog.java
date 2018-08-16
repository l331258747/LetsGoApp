package com.njz.letsgoapp.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.njz.letsgoapp.R;
import com.njz.letsgoapp.view.other.ReportActivity;
import com.njz.letsgoapp.wxapi.WXHelp;

/**
 * Created by liguoqiang on 2018/1/5.
 */

public class ShareDialog extends Dialog implements View.OnClickListener {

    Activity mContext;
    RelativeLayout layoutParent;
    RelativeLayout ivFriend, ivFriends;

    int flag;
    String title;
    String content;
    String imageUrl;
    String url;

    public ShareDialog(Activity context, String title, String content, String imageUrl, String url) {
        super(context);
        mContext = context;
        this.title = title;
        this.content = content;
        this.imageUrl = imageUrl;
        this.url = url;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.dialog_share, null);
        this.setContentView(layout);

        getWindow().setBackgroundDrawable(new ColorDrawable(0x00000000));
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);

        layoutParent = layout.findViewById(R.id.layout_parent);
        ivFriend = layout.findViewById(R.id.iv_friend);
        ivFriends = layout.findViewById(R.id.iv_friends);

        layoutParent.setOnClickListener(this);
        ivFriend.setOnClickListener(this);
        ivFriends.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(content.length()>99){
            content = content.substring(0,99);
        }
        if (view.getId() == R.id.iv_friend || view.getId() == R.id.iv_friends) {
            WXHelp.getInstance().wxShare(mContext,
                    view.getId() == R.id.iv_friend ? 0 : 1,
                    title,
                    content,
                    imageUrl,
                    url
            );
        }

        if(view.getId() == R.id.iv_report){
            report();
        }

        dismiss();

    }

    private void report() {
        mContext.startActivity(new Intent(mContext, ReportActivity.class));
    }
}
