package com.njz.letsgoapp.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;

import com.njz.letsgoapp.R;
import com.njz.letsgoapp.util.AppUtils;

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

}
