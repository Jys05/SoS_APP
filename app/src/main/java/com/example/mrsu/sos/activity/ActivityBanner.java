package com.example.mrsu.sos.activity;

import android.hardware.Camera;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mrsu.sos.R;
import com.example.mrsu.sos.frame.FrameActivity;
import com.example.mrsu.sos.manager.activityBrightnessManager;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


/**
 * Created by Mr.Su on 2017-3-11.
 */

public class ActivityBanner extends FrameActivity {


    @InjectView(R.id.helpTxt)
    TextView helpTxt;
    @InjectView(R.id.rl)
    RelativeLayout rl;
    @InjectView(R.id.back)
    ImageView back;

    private Timer timer;
    Camera camera = Camera.open();
    Camera.Parameters p = camera.getParameters();
    private activityBrightnessManager brightnessManager;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0x123) {
                if (0xffffffff == helpTxt.getCurrentTextColor()) {
                    rl.setBackgroundColor(0xffffffff);
                    back.setImageResource(R.mipmap.back);
                    helpTxt.setTextColor(0xff000000);
                } else if (0xff000000 == helpTxt.getCurrentTextColor()) {
                    rl.setBackgroundColor(0xff000000);
                    back.setImageResource(R.mipmap.back2);
                    helpTxt.setTextColor(0xffffffff);
                }
                flashlightUtils();
            }
        }
    };

    @Override
    protected int layoutId() {
        return R.layout.activity_banner;
    }

    @Override
    protected void initComponent() {
        helpTxt.setTextColor(0xff000000);
        back.setImageResource(R.mipmap.back);
        timer = new Timer();
        timer.schedule(sendSedule, 1000, 1000);
        //设置屏幕亮度
        brightnessManager.setActivityBrightness(1.0f, this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        timer.purge();
        timer.cancel();
        if (isFlashlightOn(false)) {
            Closeshoudian();
            camera = null;
        }
    }

    /*
         * 退出按钮
         */
    @OnClick(R.id.back)
    public void onBack(View view){
        timer.purge();
        timer.cancel();
        if (isFlashlightOn(false)) {
            Closeshoudian();
            camera = null;
        }
        finish();
    }


    private TimerTask sendSedule = new TimerTask() {
        @Override
        public void run() {
            handler.sendEmptyMessage(0x123);
        }
    };

//****************************************************************

    /*
         * 是否开启了闪光灯
         * @return
         */
    public boolean isFlashlightOn(boolean flag) {
        try {
            Camera.Parameters parameters = camera.getParameters();
            String flashMode = parameters.getFlashMode();
            if (flashMode.equals(Camera.Parameters.FLASH_MODE_TORCH)) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 闪光灯开关
     */
    public void flashlightUtils() {
        if (isFlashlightOn(false)) {
            Closeshoudian();
            camera = null;
        } else {
            Openshoudian();
        }
    }

    public void Openshoudian() {
        //异常处理一定要加，否则Camera打开失败的话程序会崩溃
        try {
            Log.d("smile", "camera打开");
            camera = Camera.open();
        } catch (Exception e) {
            Log.d("smile", "Camera打开有问题");
            Toast.makeText(ActivityBanner.this, "Camera被占用，请先关闭", Toast.LENGTH_SHORT).show();
        }

        if (camera != null) {
            //打开闪光灯
            camera.startPreview();
            Camera.Parameters parameter = camera.getParameters();
            parameter.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
            camera.setParameters(parameter);
            Log.d("smile", "闪光灯打开");
        }
    }

    public void Closeshoudian() {
        if (camera != null) {
            //关闭闪光灯
            Log.d("smile", "closeCamera()");
            camera.getParameters().setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
            camera.setParameters(camera.getParameters());
            camera.stopPreview();
            camera.release();
            camera = null;
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.inject(this);
    }
}
