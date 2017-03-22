package com.library.base.frame;

import android.os.Bundle;
import android.view.View;

/**
 * @author zhangdeming
 * @date 创建时间 2016/9/26
 * @description 基础的框架类
 */
public class FrameActivity extends BaseActivity {

    @Override
    protected void activityConfigure() {

    }

    @Override
    protected int layoutId() {
        return 0;
    }

    @Override
    protected void receiveDataFromPreActivity(Bundle data) {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initComponent() {

    }

    @Override
    protected void initListener() {

    }

    public void onReturnClick(View v) {
        finish();
    }
}
