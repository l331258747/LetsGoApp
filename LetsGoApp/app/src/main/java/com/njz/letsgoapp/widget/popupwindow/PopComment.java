package com.njz.letsgoapp.widget.popupwindow;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.njz.letsgoapp.R;
import com.njz.letsgoapp.bean.MySelfInfo;
import com.njz.letsgoapp.util.AppUtils;
import com.njz.letsgoapp.view.login.LoginActivity;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by LGQ
 * Time: 2018/9/6
 * Function:
 */

public class PopComment extends PopupWindow {
    EditText popup_live_comment_edit;
    TextView popup_live_comment_send;

    private View commentView;
    Context mContext;

    public PopComment(final Context context, View parentView) {
        super(parentView, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);

        mContext = context;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        commentView = inflater.inflate(R.layout.popup_comment, null);
        popup_live_comment_edit = commentView.findViewById(R.id.et_content);
        popup_live_comment_send = commentView.findViewById(R.id.tv_send);

        this.mContext = context;

        this.setContentView(commentView);
        setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);

        // 设置动画 commentPopup.setAnimationStyle(R.style.popWindow_animation_in2out);
        // 使其聚集 ，要想监听菜单里控件的事件就必须要调用此方法
        setFocusable(true);
        // 设置允许在外点击消失
        setOutsideTouchable(true);
        // 设置背景，这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景
        setBackgroundDrawable(new BitmapDrawable());
        //必须加这两行，不然不会显示在键盘上方
        setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED);
        setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        popup_live_comment_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String result = popup_live_comment_edit.getText().toString().trim();
                if (result.length() != 0) {
                    if(sendClickLisener != null)
                        sendClickLisener.send(result);
                    dismiss();
                }
            }
        });

        //设置popup关闭时要做的操作
        setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                AppUtils.HideKeyboard(popup_live_comment_edit);
                popup_live_comment_edit.setText("");
            }
        });
    }

    OnSendClickLisener  sendClickLisener;
    public interface OnSendClickLisener{
        void send(String content);
    }
    public void setSendClickLisener(OnSendClickLisener  sendClickLisener){
        this.sendClickLisener = sendClickLisener;
    }

    public void setEtHint(String name){
        popup_live_comment_edit.setHint(name);
    }

    public void showPop(View btnComment) {

        if (!MySelfInfo.getInstance().isLogin()) {
            mContext.startActivity(new Intent(mContext,LoginActivity.class));
            return;
        }

        showAtLocation(btnComment, Gravity.BOTTOM, 0, 0);

        //显示软键盘
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                AppUtils.ShowKeyboard(popup_live_comment_edit);
            }
        }, 200);
    }


}
