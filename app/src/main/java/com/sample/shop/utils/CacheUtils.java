package com.sample.shop.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 作者：Administrator on 2017/7/1 16:32
 * 作用：
 */
public class CacheUtils {

    private static final String SP_NAME = "sp_shop";
    private static FileOutputStream fos;
    private static FileInputStream fis;
    private static ByteArrayOutputStream bos;

    //获取文件
    public static String getString(Context context, String key) {

        String result = "";
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            //MD5加密
            // String fileName = MD5.md5(key);
            File file = new File(Environment.getExternalStorageDirectory() + "/shangcheng/files", key);
            try {
                if (file.exists()) {
                    fis = new FileInputStream(file);
                    bos = new ByteArrayOutputStream();
                    int len;
                    byte[] b = new byte[1024];
                    while ((len = fis.read(b)) != -1) {
                        bos.write(b, 0, len);
                    }
                    result = bos.toString();
                }
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("TAG", "文本取出失败" + e.getMessage());
            } finally {
                try {
                    if (bos != null) {
                        bos.close();
                    }
                    if (fis != null) {
                        fis.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        } else {
            SharedPreferences sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
            result = sp.getString(key, "");
            return result;
        }
        return result;
    }

    //缓存文件
    public static void putString(Context context, String key, String values) {

        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            //MD5加密
            // String fileName = MD5.md5(key);
            File file = new File(Environment.getExternalStorageDirectory() + "/shangcheng/files", key);
            File parentFile = file.getParentFile();
            try {
                //没有SD卡创建目录
                if (!parentFile.exists()) {
                    parentFile.mkdirs();
                }
                if (!file.exists()) {
                    file.createNewFile();
                }
                //保存文本
                fos = new FileOutputStream(file);
                fos.write(values.getBytes());

            } catch (Exception e) {
                e.printStackTrace();
                Log.e("TAG", "文本缓存失败" + e.getMessage());
            } finally {
                try {
                    if (fos != null) {
                        fos.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        } else {
            SharedPreferences sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
            sp.edit().putString(key, values).commit();
        }

    }
}
