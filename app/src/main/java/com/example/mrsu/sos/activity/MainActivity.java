package com.example.mrsu.sos.activity;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.telecom.Call;
import android.view.View;

import com.example.mrsu.sos.PerenceKey.preferenceKey;
import com.example.mrsu.sos.R;
import com.example.mrsu.sos.dialog.dialogSettingPhone;
import com.example.mrsu.sos.frame.FrameActivity;
import com.orhanobut.hawk.Hawk;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends FrameActivity {

    @Override
    protected int layoutId() {
        return R.layout.activity_main;
    }


    @OnClick({R.id.phone, R.id.message, R.id.QQ, R.id.WeChat, R.id.noise, R.id.banner, R.id.setting})
    public void onClick(View view) {
        switch (view.getId()) {
            //拨打电话
            case R.id.phone:
                String phoneNum = Hawk.get(preferenceKey.PHONE_NUM);
                if (phoneNum != null) {
                    //只要有缓存电话号码，就去拨号界面（或者直接去拨号）——到时候在设置界面，中添加设置
                    toCallPhoneNum(phoneNum);
                }else {
                    new dialogSettingPhone(this).show();
                }
                break;
            //发送短信
            case R.id.message:
                String phoneNum2 = Hawk.get(preferenceKey.PHONE_NUM);
                if (phoneNum2 != null) {
                    gotoActivityNotClose(ActivityMessage.class , null);
                }else {
                    new dialogSettingPhone(this).show();
                }
                break;
            //分享QQ
            case R.id.QQ:
                break;
            //分享微信
            case R.id.WeChat:
                break;
            //发出噪声
            case R.id.noise:
                break;
            //Banner图，以及亮起闪光灯
            case R.id.banner:
                gotoActivityNotClose(ActivityBanner.class, null);
                break;
            //设置手机号码
            case R.id.setting:
                gotoActivityNotClose(ActivitySetting.class, null);
                break;
        }
    }

    private void toCallPhoneNum(String phoneNum){
        //ACTION_CALL为直接拨打电话号码，ACTION_DIAL为只是跳转到拨号界面
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNum));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        startActivity(intent);
    }
}
