package com.library.base.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.library.base.R;


public class StarBar extends LinearLayout implements View.OnClickListener {

    private int starFullResId;
    private int starHalfResId;
    private int starEmptyResId;
    private int starSize;
    private int starMax;

    private int star = 0;
    private boolean enable;

    public StarBar(Context context) {
        this(context, null);
    }

    public StarBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StarBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.StarBar);
        try {
            starFullResId = array.getResourceId(R.styleable.StarBar_star_full, -1);
            starHalfResId = array.getResourceId(R.styleable.StarBar_star_half, -1);
            starEmptyResId = array.getResourceId(R.styleable.StarBar_star_empty, -1);
            starSize = array.getDimensionPixelOffset(R.styleable.StarBar_star_size, 40);
            starMax = array.getInteger(R.styleable.StarBar_star_max, 5);
            enable = array.getBoolean(R.styleable.StarBar_star_enable, false);
        } finally {
            array.recycle();
        }
        init();
    }

    private void init() {
        removeAllViews();
        for (int i = 1; i <= starMax; i++) {
            addImageView(i, starEmptyResId);
        }
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public int getStar() {
        return star;
    }

    public void setStar(int num) {
        removeAllViews();
        for (int i = 1; i <= starMax; i++) {
            if (i <= num) {
                addImageView(i, starFullResId);
            } else {
                addImageView(i, starEmptyResId);
            }
        }
        star = num;
    }

    private void addImageView(int index, int resId) {
        ImageView imageView = new ImageView(getContext());
        imageView.setImageResource(resId);
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams
                .WRAP_CONTENT);
        imageView.setLayoutParams(params);
        imageView.setTag(index);
        if (enable) {
            imageView.setOnClickListener(this);
        }
        addView(imageView);
    }

    @Override
    public void onClick(View view) {
        Integer index = (Integer) view.getTag();
        if (index != null) {
            setStar(index);
        }
    }
}
