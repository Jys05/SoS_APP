package com.library.base.function;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.library.base.R;
import com.library.base.adapter.DisplayPhotoAdapter;
import com.library.base.util.CheckUtil;
import com.library.base.view.photoview.PhotoView;
import com.library.base.view.photoview.PhotoViewAttacher;

import java.io.File;

/**
 * @Description 展示图片
 * @auth zhangdm
 * @date 2016/1/26 14:07
 */
public class DisplayPhotoActivity extends AppCompatActivity {
    public static String TAG = "DisplayPhotoActivity";

    private ViewPager mViewPager;
    private DisplayPhotoAdapter mPhotoDisplayAdapter;
    private RelativeLayout mMultiple;
    private PhotoView mLeaflet;//单张图片显示所使用的控件
    private TextView mPhotoNumber;//多张图片显示时，照片页码

    private int mDefaultPic;//默认的显示图片
    private boolean mOnlyOne;//是否为单张显示
    private int mPosition;//当前显示的照片页码
    private String[] mImagePaths;//保存显示的图片数组的路径

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_photo);
        initData();
        initView();
        initListener();
    }

    /**
     * 获取数据
     */
    protected void initData() {
        Bundle mBundle = getIntent().getExtras();
        mOnlyOne = mBundle.getBoolean("onlyOne");
        mImagePaths = mBundle.getStringArray("imagePaths");
        mPosition = mBundle.getInt("position");
        mDefaultPic = mBundle.getInt("defaultPic");
        Log.i(TAG, "mOnlyOne=" + mOnlyOne + ";mImagePaths=" + mImagePaths.toString());
    }

    /**
     * 初始化控件
     */
    protected void initView() {
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mMultiple = (RelativeLayout) findViewById(R.id.multipleRL);
        mLeaflet = (PhotoView) findViewById(R.id.leafletPV);
        mPhotoNumber = (TextView) findViewById(R.id.photo_number);
        if (!mOnlyOne) {
            mMultiple.setVisibility(View.VISIBLE);
            mPhotoDisplayAdapter = new DisplayPhotoAdapter(this, mImagePaths, mDefaultPic);
            mViewPager.setAdapter(mPhotoDisplayAdapter);
            mViewPager.setCurrentItem(mPosition);
            mPhotoNumber.setText((mPosition + 1) + "/" + mImagePaths.length);
        } else {
            mLeaflet.setVisibility(View.VISIBLE);
            mLeaflet.setImageResource(mDefaultPic);
            if (CheckUtil.checkURL(mImagePaths[0])) {
                Log.i(TAG, getResources().getString(R.string.network_pic));
                Glide.with(this).load(mImagePaths[0]).into(mLeaflet);
            } else {
                Log.i(TAG, getResources().getString(R.string.local_pic));
                File mFile = new File(mImagePaths[0]);
                if (mFile.exists()) {
                    Bitmap mBitmap = BitmapFactory.decodeFile(mImagePaths[0]);
                    mLeaflet.setImageBitmap(mBitmap);
                }
            }
            mLeaflet.setOnViewTapListener(new PhotoViewAttacher.OnViewTapListener() {
                @Override
                public void onViewTap(View view, float x, float y) {
                    DisplayPhotoActivity.this.finish();
                }
            });
        }
    }

    /**
     * 初始化监听器
     */
    protected void initListener() {
        if (true != mOnlyOne) {
            mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    mPhotoNumber.setText((position + 1) + "/" + mImagePaths.length);
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
        }
    }
}
