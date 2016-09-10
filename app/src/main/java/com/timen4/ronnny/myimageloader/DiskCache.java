package com.timen4.ronnny.myimageloader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;


import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * Created by luore on 2016/9/4.
 */
public class DiskCache implements ImageCache{
    private String cacheDir="sdcard/MyImageLoader/cache";
    @Override
    public Bitmap get(String url){
        return BitmapFactory.decodeFile(cacheDir+url);
    }
    @Override
    public void put(String url,Bitmap bitmap){
        FileOutputStream fileOutputStream=null;

        try {
            fileOutputStream=new FileOutputStream(cacheDir+url);
            bitmap.compress(Bitmap.CompressFormat.PNG,100,fileOutputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }finally {
            //使用接口隔离原则，依赖于抽象而不是具体实现
            CloseUtils.closeQuietly(fileOutputStream);
        }

    }

}
