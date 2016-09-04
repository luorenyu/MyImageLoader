package com.timen4.ronnny.myimageloader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.util.LruCache;
import android.widget.ImageView;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Created by luore on 2016/9/4.
 */
public class ImageLoader {
    //图片缓存
    private LruCache<String ,Bitmap> mImagelruCache;
    //线程池，线程池数量为cpu数
    private ExecutorService mExecutorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    public ImageLoader() {
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
    //
    public void displayImage(final String url, final ImageView imageView){
        imageView.setTag(url);
        mExecutorService.submit(new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap=downloadImage(url);
                if (bitmap==null){
                    return;
                }
                if (imageView.getTag().equals(url)){
                    imageView.setImageBitmap(bitmap);
                }
                mImagelruCache.put(url,bitmap);
            }
        });

    }

    private Bitmap downloadImage(String imageUrl){
        Bitmap bitmap=null;
        try {
            URL url=new URL(imageUrl);
            HttpURLConnection conn= (HttpURLConnection) url.openConnection();
            bitmap = BitmapFactory.decodeStream(conn.getInputStream());
            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return bitmap;
    }
}
