package com.timen4.ronnny.myimageloader;

import android.graphics.Bitmap;

/**
 * Created by luore on 2016/9/4.
 */
public class DoubleCache {
    DiskCache mDiskCache= new DiskCache();
    ImageCache mMemoryCache = new ImageCache();


    public Bitmap get(String url){
        Bitmap bitmap=null;
        bitmap=mMemoryCache.get(url);
        if(bitmap==null){
            bitmap=mDiskCache.get(url);
        }
        return bitmap;
    }

    public void put(String url,Bitmap bitmap){
        mDiskCache.put(url,bitmap);
        mMemoryCache.put(url,bitmap);
    }

}
