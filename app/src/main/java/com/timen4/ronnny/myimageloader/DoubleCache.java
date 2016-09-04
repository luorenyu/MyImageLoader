package com.timen4.ronnny.myimageloader;

import android.graphics.Bitmap;

/**
 * Created by luore on 2016/9/4.
 */
public class DoubleCache implements ImageCache {
    DiskCache mDiskCache= new DiskCache();
    MemoryCache mMemoryCache = new MemoryCache();

    @Override
    public Bitmap get(String url){
        Bitmap bitmap=null;
        bitmap=mMemoryCache.get(url);
        if(bitmap==null){
            bitmap=mDiskCache.get(url);
        }
        return bitmap;
    }
    @Override
    public void put(String url,Bitmap bitmap){
        mDiskCache.put(url,bitmap);
        mMemoryCache.put(url,bitmap);
    }

}
