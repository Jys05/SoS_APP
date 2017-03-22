package com.example.mrsu.sos.dialog;

import android.app.Dialog;
import android.view.View;

import com.example.mrsu.sos.R;
import com.example.mrsu.sos.activity.ActivityBanner;
import com.example.mrsu.sos.activity.ActivitySetting;
import com.example.mrsu.sos.frame.FrameActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Mr.Su on 2017-3-11.
 */

public class dialogSettingPhone extends Dialog {

    private FrameActivity context = null;

    public dialogSettingPhone(FrameActivity context) {
        super(context, R.style.Dialog);
        this.context = context;
        setContentView(R.layout.dialog_settingphone);
        //初始化“黄油刀”
        ButterKnife.inject(this);
    }

    @OnClick({R.id.no, R.id.cancel})
    public void onCancel(View view) {
        dismiss();
    }

    @OnClick(R.id.gotoSetting)
    public void onGoto() {
        dismiss();
        context.gotoActivityNotClose(ActivitySetting.class , null);
    }
}
