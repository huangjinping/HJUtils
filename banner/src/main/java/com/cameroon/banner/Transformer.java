package com.cameroon.banner;

import android.support.v4.view.ViewPager.PageTransformer;

import com.cameroon.banner.transformer.AccordionTransformer;
import com.cameroon.banner.transformer.BackgroundToForegroundTransformer;
import com.cameroon.banner.transformer.CubeInTransformer;
import com.cameroon.banner.transformer.CubeOutTransformer;
import com.cameroon.banner.transformer.DefaultTransformer;
import com.cameroon.banner.transformer.DepthPageTransformer;
import com.cameroon.banner.transformer.FlipHorizontalTransformer;
import com.cameroon.banner.transformer.FlipVerticalTransformer;
import com.cameroon.banner.transformer.ForegroundToBackgroundTransformer;
import com.cameroon.banner.transformer.RotateDownTransformer;
import com.cameroon.banner.transformer.RotateUpTransformer;
import com.cameroon.banner.transformer.ScaleInOutTransformer;
import com.cameroon.banner.transformer.StackTransformer;
import com.cameroon.banner.transformer.TabletTransformer;
import com.cameroon.banner.transformer.ZoomInTransformer;
import com.cameroon.banner.transformer.ZoomOutSlideTransformer;
import com.cameroon.banner.transformer.ZoomOutTranformer;

public class Transformer {
    public static Class<? extends PageTransformer> Default = DefaultTransformer.class;
    public static Class<? extends PageTransformer> Accordion = AccordionTransformer.class;
    public static Class<? extends PageTransformer> BackgroundToForeground = BackgroundToForegroundTransformer.class;
    public static Class<? extends PageTransformer> ForegroundToBackground = ForegroundToBackgroundTransformer.class;
    public static Class<? extends PageTransformer> CubeIn = CubeInTransformer.class;
    public static Class<? extends PageTransformer> CubeOut = CubeOutTransformer.class;
    public static Class<? extends PageTransformer> DepthPage = DepthPageTransformer.class;
    public static Class<? extends PageTransformer> FlipHorizontal = FlipHorizontalTransformer.class;
    public static Class<? extends PageTransformer> FlipVertical = FlipVerticalTransformer.class;
    public static Class<? extends PageTransformer> RotateDown = RotateDownTransformer.class;
    public static Class<? extends PageTransformer> RotateUp = RotateUpTransformer.class;
    public static Class<? extends PageTransformer> ScaleInOut = ScaleInOutTransformer.class;
    public static Class<? extends PageTransformer> Stack = StackTransformer.class;
    public static Class<? extends PageTransformer> Tablet = TabletTransformer.class;
    public static Class<? extends PageTransformer> ZoomIn = ZoomInTransformer.class;
    public static Class<? extends PageTransformer> ZoomOut = ZoomOutTranformer.class;
    public static Class<? extends PageTransformer> ZoomOutSlide = ZoomOutSlideTransformer.class;
}
