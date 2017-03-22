package com.library.base.util.recyclerview;

import android.view.View;

/**
 * RecyclerView点击事件的接口
 */
public interface OnItemClickListener {
    /**
     * 短按事件
     *
     * @param view
     * @param position
     */
    void onItemClick(View view, int position);

    /**
     * 长按事件
     *
     * @param view
     * @param position
     */
    void onItemLongClick(View view, int position);
}