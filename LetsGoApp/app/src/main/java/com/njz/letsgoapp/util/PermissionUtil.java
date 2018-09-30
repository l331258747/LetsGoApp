package com.njz.letsgoapp.util;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;

import com.njz.letsgoapp.dialog.DialogUtil;

/**
 * Created by LGQ
 * Time: 2018/9/30
 * Function:
 */

public class PermissionUtil {

    private static PermissionUtil permissionUtil;

    public static PermissionUtil getInstance(){
        if (null == permissionUtil) {
            synchronized (PermissionUtil.class) {
                if (null == permissionUtil) {
                    permissionUtil = new PermissionUtil();
                }
            }
        }
        return permissionUtil;
    }

    private PermissionUtil(){
    }

    public static final int PERMISSION_LOCATION_CODE = 0X0001 + 2;
    public static final String PERMISSION_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;

    /**
     * 检测GPS、位置权限是否开启
     */
    public boolean getPremission(final Context context,String permission,int permissionCode) {
            if (Build.VERSION.SDK_INT >= 23) { //判断是否为android6.0系统版本，如果是，需要动态添加权限
                if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions((Activity) context, new String[]{permission}, permissionCode);
                    return false;
                } else {
                    return true;
                }
            } else {
                return true;
            }
    }


    //判断系统定位服务是否开启
    public boolean isGpsAvailable(final Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context
                .LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            DialogUtil.getInstance().getDefaultDialog(context,
                    "提示",
                    "系统检测到未开启GPS定位服务,请开启",
                    "去开启"
                    , new DialogUtil.DialogCallBack() {
                        @Override
                        public void exectEvent(DialogInterface alterDialog) {
                            Intent intent = new Intent();
                            intent.setAction(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            context.startActivity(intent);
                        }
                    }).show();
            return false;
        }
        return true;
    }


    public void showAccreditDialog(final Context context, String msg) {
        new AlertDialog.Builder(context)
                .setMessage(msg)
                .setPositiveButton("去授权", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //引导用户至设置页手动授权
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", context.getApplicationContext().getPackageName(), null);
                        intent.setData(uri);
                        context.startActivity(intent);
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                //引导用户手动授权，权限请求失败
            }
        }).show();
    }

}
