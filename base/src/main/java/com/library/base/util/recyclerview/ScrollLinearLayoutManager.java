package com.library.base.util.recyclerview;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

/**
 * 计算RecyclerView的实际高度
 * Created by Sunflower on 2015/9/6.
 */
public class ScrollLinearLayoutManager extends LinearLayoutManager {
    public ScrollLinearLayoutManager(Context context) {
        //默认方向是VERTICAL,即ListView
        super(context);
    }

    @Override
    public void onMeasure(RecyclerView.Recycler recycler, RecyclerView.State state, int widthSpec, int heightSpec) {
        if (canScrollHorizontally()) {
            //如果是HORIZONTAL
            return;
        }
        int height = 0;
        Log.i("msg", "onMeasure---MeasureSpec-" + View.MeasureSpec.getSize(heightSpec));
        int childCount = getItemCount();
        for (int i = 0; i < childCount; i++) {
            View child = recycler.getViewForPosition(i);
            measureChild(child, widthSpec, heightSpec);
            int measuredHeight = child.getMeasuredHeight() + getDecoratedBottom(child);
            height += measuredHeight;
        }
        height += getPaddingTop() + getPaddingBottom();
        Log.i("msg", "onMeasure---height-" + height);
        setMeasuredDimension(View.MeasureSpec.getSize(widthSpec), height);
    }
}
