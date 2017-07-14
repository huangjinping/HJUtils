package com.hjp.utils;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.cameroon.banner.Banner;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Banner banner;

    private List<String> dataList;
    private ImageView img_icon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();


        Glide.with(this).load("http://www.quanjing.com/image/2016index/gryy2.jpg").into(img_icon);


        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        dataList = new ArrayList<>();
        dataList.add("http://pic.qiushibaike.com/system/pictures/11928/119284374/medium/app119284374.jpg");
        dataList.add("http://pic.qiushibaike.com/system/pictures/11927/119279933/medium/app119279933.jpg");
        dataList.add("http://pic.qiushibaike.com/system/pictures/11928/119285292/medium/app119285292.jpg");
        banner.setImages(dataList);
        //banner设置方法全部调用完毕时最后调用
        banner.start();
    }

    private void initView() {
        banner = (Banner) findViewById(R.id.banner);
        img_icon = (ImageView) findViewById(R.id.img_icon);
    }
}
