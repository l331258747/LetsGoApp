package com.njz.letsgoapp.util.accessory;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;

import com.njz.letsgoapp.util.AppUtils;
import com.njz.letsgoapp.util.FileUtil;
import com.njz.letsgoapp.util.log.LogUtil;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by llt on 2017/12/17.
 */

public class ImageUtils {


    public static void getImage(String srcPath, String savepath) {
        if (FileUtil.isFileExist(new File(savepath))) {
            return;
        }

        int degree = getPicRotate(srcPath);

        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        // 开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);// 此时返回bm为空

        newOpts.inJustDecodeBounds = false;
//        int w = newOpts.outWidth;
//        int h = newOpts.outHeight;
//        float hh = Float.valueOf(MyApplication.displayHeight);
//        float ww = Float.valueOf(MyApplication.displayWidth);
//        // 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
//        int be = 1;// be=1表示不缩放
//        if (w >= h && w > ww) {// 如果宽度大的话根据宽度固定大小缩放
//            be = (int) (newOpts.outWidth / ww);
//        } else if (w <= h && h > hh) {// 如果高度高的话根据宽度固定大小缩放
//            be = (int) (newOpts.outHeight / hh);
//        }
//        if (be <= 0)
//            be = 1;
        int inSampleSize = calculateInSampleSize(newOpts, AppUtils.getDisplayWidth(),AppUtils.getDisplayHeight());

        newOpts.inSampleSize = inSampleSize;// 设置缩放比例
        newOpts.inPreferredConfig = Bitmap.Config.ARGB_8888;//该模式是默认的,可不设
        newOpts.inPurgeable = true;// 同时设置才会有效
        newOpts.inInputShareable = true;//。当系统内存不够时候图片自动被回收
        // 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
        if(degree!=0){
            Matrix m = new Matrix();
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            m.setRotate(degree); // 旋转angle度
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height,m, true);// 从新生成图片
        }

        Bitmap newBitmap = compressImage(bitmap, 100);// 压缩好比例大小后再进行质量压缩
        saveBitmapToSD(savepath, newBitmap);
    }

    //计算图片的缩放值
    public static int calculateInSampleSize(BitmapFactory.Options options,int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height/ (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? widthRatio : heightRatio;
        }
        return inSampleSize;
    }


    public static Bitmap compressImage(Bitmap image, int imageSize) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 90;
        int bpSize = baos.toByteArray().length / 1024;
        // 判断图片大小
        int tmpV = imageSize;
        if (bpSize > tmpV) {
            tmpV = imageSize;
        } else if (bpSize > tmpV / 2) {
            tmpV = tmpV / 2;
        } else {
            tmpV = tmpV / 4;
        }
        while (baos.toByteArray().length / 1024 > tmpV) { // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
            if (options == 10) {
                break;
            }
            baos.reset();// 重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
            options -= 10;// 每次都减少10
            if (options == 0)
                break;
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);// 把ByteArrayInputStream数据生成图片
        return bitmap;
    }



//    /**
//     * 保存Bitmap至SD卡
//     */
//    public static void saveBitmapToSD(String path, Bitmap mBitmap) {
//        if (checkSDCardAvailable()) {
//            File dir = new File(path);
//            if (!dir.exists()) {
//                dir.mkdirs();
//            }
//            File photoFile = new File(path);
//            BufferedOutputStream bos = null;
//            try {
//                photoFile.createNewFile();
//                bos = new BufferedOutputStream(new FileOutputStream(photoFile));
//                if (mBitmap != null) {
//                    if (mBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bos)) {
//                        bos.flush();
//                        // fileOutputStream.close();
//                    }
//                }
//            } catch (FileNotFoundException e) {
//                photoFile.delete();
//                e.printStackTrace();
//            } catch (IOException e) {
//                photoFile.delete();
//                e.printStackTrace();
//            } finally {
//                try {
//                    if (bos != null) {
//                        bos.close();
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }

    /**
     * 保存Bitmap至SD卡
     */
    public static void saveBitmapToSD(String path, Bitmap mBitmap) {

        if (mBitmap != null) {
            File f = new File(path);
            try {

                if(!f.getParentFile().exists()){
                    LogUtil.e("父文件夹不存在，创建之");
                    f.getParentFile().mkdirs();
                }
                if(f.exists()){
                    LogUtil.e("文件已存在");
                }

                f.createNewFile();
                FileOutputStream fOut = null;
                fOut = new FileOutputStream(f);

                mBitmap.compress(Bitmap.CompressFormat.JPEG, 90, fOut);
                fOut.flush();
                fOut.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public static boolean checkSDCardAvailable() {
        return android.os.Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED);
    }


    /**
     * 获取图片文件的信息，是否旋转了90度，如果是则反转
     * @param bitmap 需要旋转的图片
     * @param path   图片的路径
     */
    public static Bitmap reviewPicRotate(Bitmap bitmap,String path){
        int degree = getPicRotate(path);
        if(degree!=0){
            Matrix m = new Matrix();
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            m.setRotate(degree); // 旋转angle度
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height,m, true);// 从新生成图片
        }
        return bitmap;
    }

    /**
     * 读取图片文件旋转的角度
     * @param path 图片绝对路径
     * @return 图片旋转的角度
     */
    public static  int getPicRotate(String path) {
        int degree  = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

}
