package com.njz.letsgoapp.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;

import com.njz.letsgoapp.R;
import com.njz.letsgoapp.bean.MySelfInfo;
import com.njz.letsgoapp.util.AppUtils;
import com.njz.letsgoapp.util.ToastUtil;

/**
 * Created by LGQ
 * Time: 2018/9/26
 * Function:
 */

public class DialogUtil {

    private static DialogUtil instance = null;

    public static DialogUtil getInstance() {
        if (instance == null) {
            instance = new DialogUtil();
        }
        return instance;
    }

    private DialogUtil() {
    }


    public AlertDialog getDefaultDialog(Context context, String title, String content, String positiveName, final DialogUtil.DialogCallBack callBack) {
        AlertDialog alterDialog = new AlertDialog.Builder(context)
                .setMessage(content)
                .setTitle(title)
                .setPositiveButton(positiveName, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(callBack == null){
                            dialog.cancel();
                        }else{
                            callBack.exectEvent(dialog);
                        }
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }).create();

        return alterDialog;
    }

    public AlertDialog getDefaultDialog(Context context, String content) {
        return this.getDefaultDialog(context,"提示",content,"确定",null);
    }

    public interface DialogCallBack {
        void exectEvent(DialogInterface alterDialog);
    }


    //联系客服对话框
    public void showCustomerMobileDialog(final Context context){
        showMobileDialog(context,MySelfInfo.getInstance().getCustomerMobile(),"暂无客服联系方式");

    }

    public void showGuideMobileDialog(final Context context,final String mobil){
        showMobileDialog(context,mobil,"暂无导游联系方式");
    }

    public void showMobileDialog(final Context context,final String mobile,String error){
        if(TextUtils.isEmpty(mobile)){
            ToastUtil.showShortToast(context,error);
            return;
        }
        DialogUtil.getInstance().getDefaultDialog(context, "提示", mobile, "呼叫", new DialogUtil.DialogCallBack() {
            @Override
            public void exectEvent(DialogInterface alterDialog) {
                Intent dialIntent =  new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + mobile));
                context.startActivity(dialIntent);
                alterDialog.dismiss();
            }
        }).show();
    }

}
