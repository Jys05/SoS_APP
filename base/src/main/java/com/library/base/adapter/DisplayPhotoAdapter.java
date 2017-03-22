package com.library.base.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.library.base.util.CheckUtil;
import com.library.base.view.photoview.PhotoView;
import com.library.base.view.photoview.PhotoViewAttacher;

import java.io.File;

/**
 * Created by ewhale on 2015/6/11.
 */
public class DisplayPhotoAdapter extends PagerAdapter {
    public static String TAG = "PhotoDisplayAdapter";
    private String[] mImagePaths;
    private Context mContext;
    private int mDefaultPic;

    public DisplayPhotoAdapter(Context mContext, String[] mImagePaths, int mDefaultPic) {
        this.mContext = mContext;
        this.mImagePaths = mImagePaths;
        this.mDefaultPic = mDefaultPic;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        PhotoView mPhotoView = new PhotoView(container.getContext());
        mPhotoView.setImageResource(mDefaultPic);

        if (CheckUtil.checkURL(mImagePaths[position])) {
            Glide.with(mContext)
                    .load(mImagePaths[position])
                    .dontAnimate()
                    .into(mPhotoView);
        } else {
            File mFile = new File(mImagePaths[position]);
            if (mFile.exists()) {
                Bitmap mBitmap = BitmapFactory.decodeFile(mImagePaths[position]);
                mPhotoView.setImageBitmap(mBitmap);
            }
        }

        container.addView(mPhotoView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        mPhotoView.setOnViewTapListener(new PhotoViewAttacher.OnViewTapListener() {
            @Override
            public void onViewTap(View view, float x, float y) {
                ((Activity) mContext).finish();
            }
        });
        return mPhotoView;
    }

    @Override
    public int getCount() {
        return null != mImagePaths ? mImagePaths.length : 0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
