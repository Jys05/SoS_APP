package com.library.base.dialog;

import android.app.Dialog;
import android.content.Context;
import android.widget.TextView;

import com.library.base.R;


/**
 * Summary ：加载动画框
 * Created by zhangdm on 2015/12/16.
 */
public class LoadingDialog extends Dialog {
    private TextView loadingText;

    public LoadingDialog(Context context) {
        super(context, R.style.Dialog);
        setContentView(R.layout.dialog_loading);
        setCanceledOnTouchOutside(false);
        loadingText = (TextView) findViewById(R.id.loading_text);
    }

    public void setLoadingText(String s) {
        loadingText.setText(s);
    }
}
