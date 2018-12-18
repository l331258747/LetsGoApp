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
import com.njz.letsgoapp.bean.MySelfInfo;
import com.njz.letsgoapp.view.login.LoginActivity;
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

    private int reportId;//被举报用户id-
    private int reportClass;//举报内容类型（0：导游详情、1：服务详情、2：游客个人主页、3：动态详情）
    private int coverReportUserType;//被举报用户类型(0：用户，1：导游)
    private int reportContentId;//举报内容id

    public static final int REPORT_USER_TYPE_USER = 0;
    public static final int REPORT_USER_TYPE_GUIDE = 1;

    public static final int REPORT_GUIDE = 0;
    public static final int REPORT_SERVICE = 1;
    public static final int REPORT_USER = 2;
    public static final int REPORT_DYNAMIC = 3;

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

    public void setReportData(int reportId,int reportClass,int reportContentId){
        this.reportId = reportId;
        this.reportClass = reportClass;
        this.reportContentId = reportContentId;

        if(reportClass == REPORT_SERVICE || reportClass == REPORT_GUIDE){
            this.coverReportUserType = REPORT_USER_TYPE_GUIDE;
        }else{
            this.coverReportUserType = REPORT_USER_TYPE_USER;
        }
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
        if(!MySelfInfo.getInstance().isLogin()){
            mContext.startActivity(new Intent(mContext, LoginActivity.class));
            return;
        }
        Intent intent = new Intent(mContext, ReportActivity.class);
        intent.putExtra("reportId",reportId);
        intent.putExtra("reportClass",reportClass);
        intent.putExtra("coverReportUserType",coverReportUserType);
        intent.putExtra("reportContentId",reportContentId);
        mContext.startActivity(intent);
    }
}
