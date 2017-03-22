package com.example.mrsu.sos.frame;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.orhanobut.hawk.Hawk;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;


/**
 * @author zhangdeming
 * @date 创建时间 2016/9/26
 * @description 所有Activity的基础类，负责创建一些基础操作
 */
public abstract class BaseActivity extends AppCompatActivity {

    /**
     * 用于Log数据用的标志
     */
    protected final String TAG = getClass().getSimpleName();
    protected boolean openEventBus = false;
    protected Fragment currentFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityConfigure();
        AppManager.getInstance().add(this);
        setContentView(layoutId());
        //注释绑定控件
        ButterKnife.inject(this);
        Hawk.init(this).build();
        if (getIntent().getExtras() != null) {
            receiveDataFromPreActivity(getIntent().getExtras());
        }
        initData();
        initComponent();
        initListener();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (openEventBus) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    protected void onStop() {
        if (openEventBus) {
            EventBus.getDefault().unregister(this);
        }
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        AppManager.getInstance().remove(this);
        super.onDestroy();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    protected abstract void activityConfigure();

    protected abstract int layoutId();

    protected abstract void receiveDataFromPreActivity(Bundle data);

    protected abstract void initData();

    protected abstract void initComponent();

    protected abstract void initListener();


    /**
     * 跳转到一个新的Activity，不结束当前的Activity
     *
     * @param c
     * @param bundle
     */
    public void gotoActivityNotClose(Class<?> c, Bundle bundle) {
        Intent i = new Intent(this, c);
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
        Intent i = new Intent(this, c);
        if (bundle != null) {
            i.putExtras(bundle);
        }
        this.startActivity(i);
        finish();
    }

    /**
     * 跳转到一个Activity，需要回调结果
     *
     * @param requestCode
     * @param c
     * @param bundle
     */
    public void gotoActivityForResult(int requestCode, Class<?> c, Bundle bundle) {
        Intent i = new Intent(this, c);
        if (bundle != null) {
            i.putExtras(bundle);
        }
        startActivityForResult(i, requestCode);
    }

    /**
     * 用tempFragment替代当前Fragment, 并给tempFragment增加一个tag，以便下次调用，不用新建
     *
     * @param containerId
     * @param fragment
     * @param tag
     */
    protected void replaceFragment(int containerId, Fragment fragment, String tag) {
        currentFragment = fragment;
        boolean isAdd = true;
        Fragment tempFragment = getSupportFragmentManager().findFragmentByTag(tag);
        if (tempFragment == null) {
            tempFragment = fragment;
            isAdd = false;
        }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(containerId, tempFragment, tag);
        if (!isAdd) {
            transaction.addToBackStack(tag);
        }
        transaction.commitAllowingStateLoss();
    }

    /**
     * 获取状态栏高度
     *
     * @return
     */
    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen",
                "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
}
