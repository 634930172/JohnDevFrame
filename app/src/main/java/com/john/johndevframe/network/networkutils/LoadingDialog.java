package com.john.johndevframe.network.networkutils;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.widget.TextView;

import com.john.johndevframe.R;


/**
 * Author: John
 * E-mail: 634930172@qq.com
 * Date: 2018/1/10 0010 14:31
 * <p/>
 * Description:加载的Dialog
 */

public class LoadingDialog extends Dialog {


    private TextView loading_text;
    private String loadingMsg;

    public LoadingDialog(@NonNull Context context) {
        this(context, R.style.loading_style);
    }

    private LoadingDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    public LoadingDialog(@NonNull Context context, String loadingMsg) {
        this(context, R.style.loading_style);
        this.loadingMsg=loadingMsg;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading_layout);
        setCanceledOnTouchOutside(false);
        initView();
        initData();
    }

    private void initData() {
        if(!TextUtils.isEmpty(loadingMsg)){
            loading_text.setText(loadingMsg);
        }

    }

    private void initView() {
        loading_text=findViewById(R.id.loading_text);
    }


}
