package com.example.mrsu.sos.activity;

import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;

import com.example.mrsu.sos.PerenceKey.preferenceKey;
import com.example.mrsu.sos.R;
import com.example.mrsu.sos.frame.FrameActivity;
import com.orhanobut.hawk.Hawk;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by Mr.Su on 2017-3-11.
 */

public class ActivitySetting extends FrameActivity {

    @InjectView(R.id.inputPhoneNUm)
    EditText mInputPhoneNUm;
    @InjectView(R.id.currentPhonNum)
    TextView mCurrentPhonNum;

    @Override
    protected int layoutId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initData() {
        String phoneNum = Hawk.get(preferenceKey.PHONE_NUM);
        if(phoneNum != null){
            mCurrentPhonNum.setText("当前电话号码："+phoneNum);
        }else {
            mCurrentPhonNum.setText("当前电话号码：空");
        }
    }

    @OnClick(R.id.ok)
    public void onoK() {
        //缓存电话号码
        if(!TextUtils.isEmpty(mInputPhoneNUm.getText().toString())){
            Hawk.put(preferenceKey.PHONE_NUM , mInputPhoneNUm.getText().toString());
            mCurrentPhonNum.setText("当前电话号码："+mInputPhoneNUm.getText().toString());
        }
    }
}
