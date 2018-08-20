package com.njz.letsgoapp.util.photo;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;

import com.njz.letsgoapp.constant.Constant;
import com.njz.letsgoapp.dialog.PicChooseDialog;
import com.njz.letsgoapp.util.FileUtil;

import java.io.File;
import java.io.FileOutputStream;

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
     @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
     super.onActivityResult(requestCode, resultCode, data);

     switch (requestCode) {
     case TackPicturesUtil.CHOOSE_PIC:
     case TackPicturesUtil.TACK_PIC:
     String path = tackPicUtil.getPicture(requestCode, resultCode, data, false);
     if (path == null)
     return;

     path = ImageUtil.getZipImg(path, Constant.IMAGE_CACHE_PATH + System.currentTimeMillis() + ".jpg", 980);

     String base64 = FileHelper.file2Base64(path);

     if (TextUtils.isEmpty(base64)) {
     showToast("获取图片出错");
     return;
     }

     //网络交互上传头像
     JsonObject jsonObj = new JsonObject();
     jsonObj.addProperty("type", 1);
     jsonObj.addProperty("value", base64);
     sendRequest(jsonObj, HttpConstant.URL.MODIFYPERSONALINFO, HttpConstant.ID.MODIFYPERSONALINFO, this);

     Log.e("li", path);

     break;
     default:

     break;
     }
     */

    /**
     * 弹出照片选择框
     */
    public void showDialog(Context context) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(context, Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS) != PackageManager.PERMISSION_GRANTED) {
        }else{
            PicChooseDialog dialog = new PicChooseDialog(activity);
            dialog.show();
        }
    }

    //调用剪裁
//    public void startPhotoZoomCertificate(Activity context, Uri uri) {
//   /*
//    * 至于下面这个Intent的ACTION是怎么知道的，大家可以看下自己路径下的如下网页
//    * yourself_sdk_path/docs/reference/android/content/Intent.html
//    */
//        try {
//            Intent intent = new Intent("com.android.camera.action.CROP");
//            intent.setDataAndType(uri, "image/*");
//            // 下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
//            intent.putExtra("crop", "true");
//            // aspectX aspectY 是宽高的比例
//            intent.putExtra("aspectX", 16);
//            intent.putExtra("aspectY", 9);
//            // outputX outputY 是裁剪图片宽高
//            intent.putExtra("outputX", 800);
//            intent.putExtra("outputY", 450);
//            intent.putExtra("outputFormat",
//                    Bitmap.CompressFormat.JPEG.toString());
//            intent.putExtra("noFaceDetection", true);
//            intent.putExtra(MediaStore.EXTRA_OUTPUT, FileUtil.getFolder(Constant.IMAGE_PATH).getAbsolutePath());
//            intent.putExtra("return-data", false);// 设置为不返回数据
//
//            context.startActivityForResult(intent, 3);
//        } catch (Exception e) {
//        }
//    }

    /**
     * 获取从相册选择或者是拍照的照片的路径
     * 该方法必须在onActivityResult中调用，否则会返回null
     * 获取拍照或相册的照片，然后调用图片剪切
     *
     * @param requestCode code
     * @param resultCode  resultCode
     * @param data        数据
     * @param isCrop      是否需要剪裁
     */
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
                    File outFile = FileUtil
                            .createDownloadFile(IMAGE_CACHE_PATH
                                    + System.currentTimeMillis() + ".jpg");
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
            FileOutputStream out = null;
            try {
                // 把返回的bitmap存到文件系统中
                Bitmap cropBitmap = data.getParcelableExtra("data");
                File outFile = FileUtil
                        .createDownloadFile(IMAGE_CACHE_PATH
                                + System.currentTimeMillis() + ".jpg");
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
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 256);
        intent.putExtra("outputY", 256);
        intent.putExtra("scale", true);
//        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri); //TODO 替换原图保存？？？
        intent.putExtra("return-data", true);
        intent.putExtra("noFaceDetection", true); // no face detection
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
