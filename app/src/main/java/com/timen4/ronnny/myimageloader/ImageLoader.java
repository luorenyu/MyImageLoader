package com.timen4.ronnny.myimageloader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import java.net.HttpURLConnection;
import java.net.URL;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * Created by luore on 2016/9/4.
 */
public class ImageLoader {

    //内存缓存
    ImageCache mMemoryCache=new ImageCache();
    //SD卡缓存
    DiskCache mDiskCache=new DiskCache();
    //双缓存
    DoubleCache mDoubleCache =new DoubleCache();

    private Boolean useDiskCache=false;
    private Boolean useDoubleCache=false;

    //线程池，线程池数量为cpu数
    private ExecutorService mExecutorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());


    public void displayImage(final String url, final ImageView imageView){
        imageView.setTag(url);
        Bitmap cacheBitmap =null;
        if (useDoubleCache){
            cacheBitmap=mDoubleCache.get(url);
        }else if (useDiskCache){
            cacheBitmap=mDiskCache.get(url);
        }else{
            cacheBitmap=mMemoryCache.get(url);
        }


        if (cacheBitmap!=null){
            imageView.setImageBitmap(cacheBitmap);
            return;
        }

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
                if(useDoubleCache){
                    mDoubleCache.put(url,bitmap);
                }else if(useDiskCache){
                    mDiskCache.put(url,bitmap);
                }else{
                    mMemoryCache.put(url,bitmap);
                }
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

    public void useDiskCache(Boolean useDiskeCache){
        this.useDiskCache = useDiskeCache;
    }

    public void useDoubleCache(Boolean useDoubleCache){
        this.useDoubleCache = useDoubleCache;
    }
}
