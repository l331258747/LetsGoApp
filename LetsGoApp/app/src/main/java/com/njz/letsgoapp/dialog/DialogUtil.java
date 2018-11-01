package com.njz.letsgoapp.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

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

    public AlertDialog getDefaultDialog(Context context, String content,final DialogUtil.DialogCallBack callBack) {
        return this.getDefaultDialog(context,"提示",content,"确定",callBack);
    }

    public AlertDialog getDefaultDialog(Context context, String content,String positiveName,final DialogUtil.DialogCallBack callBack) {
        return this.getDefaultDialog(context,"提示",content,positiveName,callBack);
    }
    public AlertDialog getEditDialog(Context context ,final DialogUtil.DialogEditCallBack callBack) {

        View dialogView = View.inflate(context, R.layout.dialog_edit, null);
        final EditText et = dialogView.findViewById(R.id.et_input);
        et.setFilters(new InputFilter[]{new InputFilter.LengthFilter(4)});
        et.setMaxLines(1);
        et.setInputType(InputType.TYPE_CLASS_TEXT);
        et.setHint("请输入4个字以内的标签");
        AlertDialog alterDialog = new AlertDialog.Builder(context)
                .setTitle("提示")
                .setView(dialogView)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(callBack == null){
                            dialog.cancel();
                        }else{
                            callBack.exectEvent(dialog,et.getText().toString());
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

    public interface DialogCallBack {
        void exectEvent(DialogInterface alterDialog);
    }

    public interface DialogEditCallBack {
        void exectEvent(DialogInterface alterDialog,String str);
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
