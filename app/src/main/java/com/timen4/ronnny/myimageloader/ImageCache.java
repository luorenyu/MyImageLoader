package com.timen4.ronnny.myimageloader;

import android.graphics.Bitmap;
import android.util.LruCache;

/**
 * Created by luore on 2016/9/4.
 * 通过实现该接口并且在ImageLoader当中调用setImageCache（）方法就可以对决堤的缓存策略进行注入！
 * 从而省去了通过大量的if else判断语句来实现不同的缓存策略！
 */
public interface ImageCache {

    public abstract void put(String url, Bitmap bitmap);

    public abstract Bitmap get(String url);
}
