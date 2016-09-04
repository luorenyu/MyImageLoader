package com.timen4.ronnny.myimageloader;

import android.graphics.Bitmap;
import android.util.LruCache;

/**
 * Created by luore on 2016/9/4.
 */
public class ImageCache {
    //图片缓存
    private LruCache<String ,Bitmap> mImagelruCache;
    public ImageCache(){
        initImageCache();
    }

    //初始化缓存
    private void initImageCache(){
        //计算出可用最大的内存 单位是KB
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        //取1/4的可用内存作为缓存
        final int cacheSize =maxMemory/4;

        mImagelruCache=new LruCache<String ,Bitmap>(cacheSize){
            @Override
            protected int sizeOf(String key, Bitmap value) {
                value.getByteCount();
                return value.getByteCount()/1024;
            }
        };
    }

    public void put(String url,Bitmap bitmap){
        mImagelruCache.put(url,bitmap);
    }

    public Bitmap get(String url){
        return mImagelruCache.get(url);
    }
}
