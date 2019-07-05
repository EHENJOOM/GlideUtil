package com.concise.util;

import android.content.Context;
import android.os.Looper;
import android.text.TextUtils;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.cache.ExternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;

import java.io.File;
import java.math.BigDecimal;

/**
 * Glide缓存工具类
 *
 * @author 赵洪苛
 * @version 1.0
 */
public class GlideCacheUtil {

    private static GlideCacheUtil glideCacheUtil;

    public static GlideCacheUtil getInstance(){
        if (glideCacheUtil == null){
            glideCacheUtil=new GlideCacheUtil();
        }
        return glideCacheUtil;
    }

    /**
     * 清除图片磁盘缓存
     */
    public void clearDiskCache(final Context context){
        try {
            if (Looper.myLooper() == Looper.getMainLooper()){
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.get(context.getApplicationContext()).clearDiskCache();
                    }
                }).start();
            }else {
                Glide.get(context.getApplicationContext()).clearDiskCache();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 清除图片内存缓存
     */
    public void clearMemoryCache(Context context){
        try {
            if (Looper.myLooper() == Looper.getMainLooper()){
                Glide.get(context.getApplicationContext()).clearMemory();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 清除图片所有缓存
     */
    public void clearAllCache(Context context){
        clearDiskCache(context);
        clearMemoryCache(context);
        deleteFolderFile(context.getApplicationContext().getExternalCacheDir()+ ExternalCacheDiskCacheFactory.DEFAULT_DISK_CACHE_DIR,true);
    }

    /**
     * 获取Glide造成的图片缓存大小
     * @return cacheSize
     */
    public String getCacheSize(Context context){
        try {
            return getFormatSize(getFolderSize(new File(context.getApplicationContext().getCacheDir()+"/"+ InternalCacheDiskCacheFactory.DEFAULT_DISK_CACHE_DIR)));
        }catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取指定文件夹内所有文件大小的和
     * @param file 文件
     * @return 文件大小
     * @throws Exception
     */
    public long getFolderSize(File file) throws Exception{
        long size=0;
        try {
            File[] fileList=file.listFiles();
            for (File aFileList : fileList){
                if (aFileList.isDirectory()){
                    size=size+getFolderSize(aFileList);
                }else {
                    size=size+aFileList.length();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return size;
    }

    /**
     * 删除指定目录下的文件
     * @param filePath 文件目录
     * @param deleteThisPath
     */
    public void deleteFolderFile(String filePath,boolean deleteThisPath){
        if (!TextUtils.isEmpty(filePath)){
            try {
                File file=new File(filePath);
                if (file.isDirectory()){
                    File files[] = file.listFiles();
                    for (File file1 : files){
                        deleteFolderFile(file1.getAbsolutePath(),true);
                    }
                }
                if (deleteThisPath){
                    if (!file.isDirectory()){
                        file.delete();
                    }else {
                        if (file.listFiles().length == 0){
                            file.delete();
                        }
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    /**
     * 格式化单位
     * @param size 大小
     * @return 格式后的字符串
     */
    public static String getFormatSize(double size){
        double kiloByte=size/1024;
        if (kiloByte<1){
            return size+"b";
        }

        double megaByte=kiloByte/1024;
        if (megaByte<1){
            BigDecimal result=new BigDecimal(Double.toString(kiloByte));
            return result.setScale(2,BigDecimal.ROUND_HALF_UP).toPlainString()+"Kb";
        }

        double gigaByte=megaByte/1024;
        if (gigaByte<1){
            BigDecimal result=new BigDecimal(Double.toString(megaByte));
            return result.setScale(2,BigDecimal.ROUND_HALF_UP).toPlainString()+"Mb";
        }

        double teraByte=gigaByte/1024;
        if (teraByte<1){
            BigDecimal result=new BigDecimal(Double.toString(gigaByte));
            return result.setScale(2,BigDecimal.ROUND_HALF_UP).toPlainString()+"Gb";
        }

        BigDecimal result=new BigDecimal(teraByte);
        return result.setScale(2,BigDecimal.ROUND_HALF_UP).toPlainString()+"Tb";
    }
}

