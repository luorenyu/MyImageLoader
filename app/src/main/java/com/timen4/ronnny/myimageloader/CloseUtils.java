package com.timen4.ronnny.myimageloader;

import java.io.Closeable;
import java.io.IOException;

/**
 * Created by luore on 2016/9/10.
 */
public class CloseUtils {
    public CloseUtils() {

    }

    public static void closeQuietly(Closeable closeable){
        if (closeable!=null){
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
