package com.njz.letsgoapp.util;

import android.os.Environment;

import com.njz.letsgoapp.constant.Constant;

import java.io.File;

/**
 * Created by LGQ
 * Time: 2018/7/26
 * Function: file工具类
 */

public class FileUtil {

    /**
     * context方法
     * getCacheDir  getFilesDir
     * 1.getCacheDir和getFilesDir是放在/data/data/packagename下的，所以这个目录中的内容必须是root的手机在文件操作系统中才能看到。
     * 2.当然如果在应用程序中清空数据或者卸载应用，那么这两个目录下的文件也将会被清空的。
     * 3.些文件一旦设备内部存储空间不足时，这些保存在 cache下的文件会删除
     * /data/data/包名/cache
     *
     * getExternalFilesDir      getExternalCacheDir
     * 1.这个是放在外置存储卡的，这个目录下的内容 可以使用文件浏览系统查看到，
     * 但是如果清空数据或者卸载应用，俩个目录下的文件也将被清空。
     * /storage/emulated/0/Android/data/com.learn.test/cache
     */

    /**
     * 外部存储 跟随应用
     * getExternalFilesDir()
     * /storage/emulated/0/Android/data/com.learn.test/files
     *
     * getExternalFilesDir("test")
     * /storage/emulated/0/Android/data/com.learn.test/files/test
     *
     * getExternalFilesDir(String type) DIRECTORY_MUSIC
     * /storage/emulated/0/Android/data/com.learn.test/files/Music
     */

    /**
     * 外部存储 不跟随应用 Environment
     * getExternalStorageDirectory = getExternalStoragePublicDirectory
     * /storage/emulated/0
     *
     * Environment.getExternalStoragePublicDirectory("test")
     * /storage/emulated/0/test
     *
     * Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
     * /storage/emulated/0/Pictures
     */

    /**
     * 内部存储
     * Environment.getDataDirectory()
     * /data文件夹,一般应用无权限操作
     *
     * Environment.getDownloadCacheDirectory()
     * /cache文件夹,一般应用无权限操作
     *
     * Environment.getRootDirectory()
     * /system 文件夹,需要root 权限
     *
     */


    //获取文件夹 filename:log
    public static File getFolder(String filename){
        String filePath;
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) { // SD卡根目录的hello.text
            filePath = Environment.getExternalStorageDirectory().getPath() + File.separator +
                    Constant.BASE_PATH + File.separator + filename;
        } else  // 系统下载缓存根目录的hello.text
            filePath = AppUtils.getContext().getFilesDir().getPath() + File.separator + filename;
        File dir = new File(filePath);

        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dir;
    }

    /**
     * 删除文件目录，或指定文件
     */
    public static void delAllFile(File file){
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                File f = files[i];
                delAllFile(f);
            }
        } else if (file.exists()) {
            file.delete();
        }
    }

}
