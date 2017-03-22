package com.library.base.frame;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.library.base.util.LogUtil;

import butterknife.ButterKnife;

/**
 * @author zhangdeming
 * @date 创建时间 2016/12/7
 * @description 描述类的功能
 */

public abstract class FrameFragment extends Fragment {

    protected FrameActivity context;
    protected View rootView;
    protected boolean isInit = false;

    protected abstract int layoutId();

    protected abstract void initComponent();

    protected abstract void refreshUI();

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.inject(this, view);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (!isInit) {
            initComponent();
            isInit = true;
        }
        LogUtil.e("FrameFragment", "onActivityCreated");
//        refreshUI();
    }

    @Override
    public void onStart() {
        super.onStart();
        LogUtil.e("FrameFragment", "onStart");
        refreshUI();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View view = setContentView(inflater, layoutId());
        return view;
    }

    protected FrameActivity getFrameActivity() {
        return (FrameActivity) getActivity();
    }

    /**
     * 调用该办法可避免重复加载UI
     */
    public View setContentView(LayoutInflater inflater, int resId) {
        if (rootView == null) {
            rootView = inflater.inflate(resId, null);
        }
        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null) {
            parent.removeView(rootView);
        }
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        LogUtil.e("msg", "onDestroyView");
        ButterKnife.reset(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtil.e("msg", "onDestroy");
    }

    /**
     * 跳转到一个新的Activity，不结束当前的Activity
     *
     * @param c
     * @param bundle
     */
    public void gotoActivityNotClose(Class<?> c, Bundle bundle) {
        Intent i = new Intent(this.getActivity(), c);
        if (bundle != null) {
            i.putExtras(bundle);
        }
        this.startActivity(i);
    }

    /**
     * 跳转到一个新的Activity，并将当前的Activity结束
     *
     * @param c
     * @param bundle
     */
    public void gotoActivity(Class<?> c, Bundle bundle) {
        Intent i = new Intent(this.getActivity(), c);
        if (bundle != null) {
            i.putExtras(bundle);
        }
        this.startActivity(i);
        this.getActivity().finish();
    }

    /**
     * 跳转到一个Activity，需要回调结果
     *
     * @param requestCode
     * @param c
     * @param bundle
     */
    public void gotoActivityForResult(int requestCode, Class<?> c, Bundle bundle) {
        Intent i = new Intent(this.getActivity(), c);
        if (bundle != null) {
            i.putExtras(bundle);
        }
        startActivityForResult(i, requestCode);
    }
}
