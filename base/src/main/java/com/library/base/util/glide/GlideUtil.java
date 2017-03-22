package com.library.base.util.glide;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.widget.ImageView;

import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.library.base.util.LogUtil;

/**
 * Summary ：加载网络图片使用的工具类，基于Glide
 * Created by zhangdm on 2016/2/20.
 */
public class GlideUtil {
    public static final String TAG = "GlideUtil";

    /**
     * Glide的请求管理器类
     */
    private static RequestManager mRequestManager;
    private static Context mContext;

    /**
     * 初始化Glide工具
     *
     * @param context
     */
    public static void init(Context context) {
        mContext = context;
        mRequestManager = Glide.with(context);
    }

    /**
     * Glide工具类是否已经初始化
     *
     * @return 已初始化则返回true
     */
    public static boolean isInit() {
        if (mContext == null || mRequestManager == null) {
            LogUtil.i(TAG, TAG + "not initComponent");
            return false;
        }
        return true;
    }

    /**
     * 加载正方形的网络图片
     *
     * @param url       网络地址
     * @param imageView 目标控件
     */
    public static void loadPicture(String url, ImageView imageView) {
        loadPicture(url, imageView, -1);
    }

    /**
     * 加载正方形的网络图片
     *
     * @param url        网络地址
     * @param imageView  目标控件
     * @param defaultImg 默认的图片 若不需要则输入-1
     */
    public static void loadPicture(String url, ImageView imageView, @DrawableRes int defaultImg) {
        if (!isInit()) {
            return;
        }
        DrawableRequestBuilder builder = mRequestManager
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .dontAnimate();
        if (defaultImg != -1) {
            builder = builder.placeholder(defaultImg);
        }
        builder.into(imageView);
    }

    /**
     * 加载方形的图片  CenterCrop
     *
     * @param url
     * @param imageView
     * @param defaultImg
     */
    public static void loadPictureFC(String url, ImageView imageView, @DrawableRes int defaultImg) {
        if (!isInit()) {
            return;
        }
        DrawableRequestBuilder builder = mRequestManager
                .load(url)
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .dontAnimate();
        if (defaultImg != -1) {
            builder = builder.placeholder(defaultImg);
        }
        builder.into(imageView);
    }

    public static void loadPictureCC(String url, ImageView imageView, @DrawableRes int defaultImg) {
        if (!isInit()) {
            return;
        }
        DrawableRequestBuilder builder = mRequestManager
                .load(url)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .dontAnimate();
        if (defaultImg != -1) {
            builder = builder.placeholder(defaultImg);
        }
        builder.into(imageView);
    }

    /**
     * 加载圆形的网络图片
     *
     * @param url       网络地址
     * @param imageView 目标控件
     */
    public static void loadCirclePicture(String url, ImageView imageView) {
        loadCirclePicture(url, imageView, -1);
    }

    /**
     * 加载圆形的网络图片
     *
     * @param url        网络地址
     * @param imageView  目标控件
     * @param defaultImg 默认的图片 若不需要则输入-1
     */
    public static void loadCirclePicture(String url, ImageView imageView, @DrawableRes int defaultImg) {
        if (!isInit()) {
            return;
        }
        DrawableRequestBuilder builder = mRequestManager
                .load(url)
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .bitmapTransform(new CropCircleTransformation(mContext));
        if (defaultImg != -1) {
            builder = builder.placeholder(defaultImg);
        }
        builder.into(imageView);
    }
}
