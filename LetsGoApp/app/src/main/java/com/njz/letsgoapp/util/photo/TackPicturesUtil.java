package com.njz.letsgoapp.util.photo;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;

import com.njz.letsgoapp.constant.Constant;
import com.njz.letsgoapp.dialog.PicChooseDialog;
import com.njz.letsgoapp.util.FileUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 获取照片工具类
 */
public class TackPicturesUtil {
    private Activity activity;

    public static final int CROP_PIC = 3;

    public static final int TACK_PIC = 1;

    public static final int CHOOSE_PIC = 2;

    /**
     * IMAGE_CACHE_PATH 图片缓存目录
     */
    public static final String IMAGE_CACHE_PATH = FileUtil.getFolder(Constant.IMAGE_PATH).getAbsolutePath();


    public TackPicturesUtil(Activity activity) {
        this.activity = activity;
    }

    /**
     * 弹出照片选择框
     */
    public void showDialog(Context context) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(context, Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS) != PackageManager.PERMISSION_GRANTED) {
        } else {
            PicChooseDialog dialog = new PicChooseDialog(activity);
            dialog.show();
        }
    }


    //兼容7.0照相获取uri
    public String getPicture(int requestCode, int resultCode, Intent data,
                             boolean isCrop) {
        if (resultCode != Activity.RESULT_OK || data == null)
            return null;

        // 获取原图
        if (requestCode == TACK_PIC || requestCode == CHOOSE_PIC) {
            Uri uri = data.getData();

            // 部分手机可能为null
            if (uri == null) {
                FileOutputStream out = null;
                try {
                    // 把返回的bitmap存到文件系统中
                    Bitmap cropBitmap = data.getParcelableExtra("data");
                    File outFile = FileUtil.createDownloadFile(IMAGE_CACHE_PATH + System.currentTimeMillis() + ".jpg");
                    out = new FileOutputStream(outFile);
                    cropBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
                    if (!cropBitmap.isRecycled())
                        cropBitmap.recycle();

                    //拍照留下的图片
                    File camerafile = new File(outFile.getAbsolutePath());
                    if (Build.VERSION.SDK_INT >= 24) {
                        String authority = activity.getApplicationInfo().packageName + ".provider";
                        uri = FileProvider.getUriForFile(activity, authority, camerafile);
                    } else {
                        uri = Uri.fromFile(camerafile);
                    }

                    if (uri == null) {
                        return outFile.getAbsolutePath();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (out != null) {
                            out.flush();
                            out.close();
                        }
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                }
            }
            if (uri == null)
                return null;

            // 是否需要调用系统剪裁界面
            if (isCrop)
                cropImageUri(uri, CROP_PIC);
            else {
                // 不需要剪裁就直接返回原图路径
                Cursor cursor = activity.getContentResolver().query(uri, new String[]{MediaStore.Images.Media.DATA}, null, null, null);
                if (cursor == null) {
                    return null;
                }
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                if (cursor.moveToFirst())
                    return cursor.getString(column_index);
            }
        } else if (requestCode == CROP_PIC && isCrop) {// 剪裁返回

            //"return-data" true 取bitmap的方法。
            FileOutputStream out = null;
            try {
                // 把返回的bitmap存到文件系统中
                Bitmap cropBitmap = data.getParcelableExtra("data");
                File outFile = FileUtil.createDownloadFile(IMAGE_CACHE_PATH + System.currentTimeMillis() + ".jpg");
                out = new FileOutputStream(outFile);
                cropBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
                if (!cropBitmap.isRecycled())
                    cropBitmap.recycle();

                return outFile.getAbsolutePath();

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (out != null) {
                        out.flush();
                        out.close();
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }

        //"return-data" false 取原图的方法。

        return null;
    }


    /**
     * 调用系统剪裁
     *
     * @param uri         uri
     * @param requestCode void
     */
    private void cropImageUri(Uri uri, int requestCode) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        if (Build.VERSION.SDK_INT >= 24) {
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }
        intent.setDataAndType(uri, "image/*");
        // crop为true是设置在开启的intent中设置显示的view可以剪裁
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX,outputY 是剪裁图片的宽高
        intent.putExtra("outputX", 320);
        intent.putExtra("outputY", 320);
        //是否保留比例
        intent.putExtra("scale", true);
        // 取消人脸识别
        intent.putExtra("noFaceDetection", true); // no face detection


        //return-data 设置了true的话直接返回bitmap，可能会很占内存
        //设置为true的话会模糊，因为取得是bitmap在内存中的缩略图
        intent.putExtra("return-data", true);

//        intent.putExtra("return-data", false);
//        //设置输出的地址return-data 设置为false "output"关联一个Uri
        intent.putExtra("output", uri); //替换原图保存
//        //outputFormat 设置输出的格式
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());


        activity.startActivityForResult(intent, requestCode);
    }

    /**
     * 验证拍照，存储图片权限
     * android.permission.CAMERA 拍照
     * android.permission.WRITE_EXTERNAL_STORAGE 往sdcard中写入数据的权限
     * android.permission.MOUNT_UNMOUNT_FILESYSTEMS 在sdcard中创建/删除文件的权限
     */
    public static void checkPermission(Context context) {//1是回调处理，这里就不处理了，如果用户取不到位置，就默认为0
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(context, Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions((Activity) context, new String[]{
                    Manifest.permission.CAMERA,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS}, 2);
        }
    }
}
