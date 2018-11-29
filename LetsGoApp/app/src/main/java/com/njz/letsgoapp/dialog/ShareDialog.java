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
import android.widget.LinearLayout;
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
    RelativeLayout ivFriend, ivFriends,ivPeport;
    LinearLayout llClose;

    int flag;
    String title;
    String content;
    String imageUrl;
    String url;

    private int reportId;
    private int reportClass;

    public static final int REPORT_DYNAMIC = 1;
    public static final int REPORT_GUIDE = 2;
    public static final int REPORT_SERVICE = 3;
    public static final int REPORT_USER = 4;

    public static final int TYPE_REPORT = 1;//只显示举报
    public static final int TYPE_ALL = 0;//全部限制
    public static final int TYPE_FRIEND = 2;//全部限制

    int type = TYPE_ALL;


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
        ivPeport = layout.findViewById(R.id.iv_report);
        llClose = layout.findViewById(R.id.ll_close);

        layoutParent.setOnClickListener(this);
        ivFriend.setOnClickListener(this);
        ivFriends.setOnClickListener(this);
        ivPeport.setOnClickListener(this);
        llClose.setOnClickListener(this);

        if(type == TYPE_REPORT){
            ivFriend.setVisibility(View.GONE);
            ivFriends.setVisibility(View.GONE);
            ivPeport.setVisibility(View.VISIBLE);
        }else if(type == TYPE_FRIEND){
            ivFriend.setVisibility(View.VISIBLE);
            ivFriends.setVisibility(View.VISIBLE);
            ivPeport.setVisibility(View.GONE);
        }else{
            ivFriend.setVisibility(View.VISIBLE);
            ivFriends.setVisibility(View.VISIBLE);
            ivPeport.setVisibility(View.VISIBLE);
        }
    }

    public void setReportData(int reportId,int reportClass){
        this.reportId = reportId;
        this.reportClass = reportClass;
    }

    public void setType(int type){
        this. type = type;
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
        }else if(view.getId() == R.id.iv_report){
            report();
        }

        dismiss();
    }

    private void report() {
        Intent intent = new Intent(mContext, ReportActivity.class);
        intent.putExtra("reportId",reportId);
        intent.putExtra("reportClass",reportClass);
        mContext.startActivity(intent);
    }
}
