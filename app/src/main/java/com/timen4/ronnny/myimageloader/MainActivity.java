package com.timen4.ronnny.myimageloader;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private ImageView iv_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iv_image = (ImageView) findViewById(R.id.iv_image);

    }

    @Override
    protected void onResume() {
        super.onResume();
        ImageLoader loader=new ImageLoader();
//        String url="http://jiangsu.china.com.cn/uploadfile/2016/0608/1465351643173155.jpg";
        String url="http://www.yqdown.com/img2015/9/18/2015091839647893.jpg";
        loader.useDoubleCache(true);
        loader.useDiskCache(false);
        loader.displayImage(url,iv_image);
    }
}
