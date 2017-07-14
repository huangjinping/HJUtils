package com.hjp.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.cameroon.banner.loader.ImageLoader;

/**
 * author Created by harrishuang on 2017/7/14.
 * email : huangjinping@hdfex.com
 */

public class GlideImageLoader extends ImageLoader {

    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        Glide.with(context).load((String)path).into(imageView);
    }
}
