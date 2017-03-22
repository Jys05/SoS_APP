package com.library.base.widget.banner;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * @author zhangdeming
 * @date 创建时间 2016/10/31
 * @description 描述类的功能
 */
public class BannerAdapter extends PagerAdapter {
    private List<View> views;
    private ViewPager viewPager;

    public BannerAdapter(List<View> views, ViewPager viewPager) {
        this.views = views;
    }

    @Override
    public int getCount() {
        //Integer.MAX_VALUE = 2147483647
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        if (views.size() > 0) {
            //position % view.size()是指虚拟的position会在[0，view.size()）之间循环
            View view = views.get(position % views.size());
            if (container.equals(view.getParent())) {
                container.removeView(view);
            }
            container.addView(view);
            return view;
        }
        return null;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
    }

}
