package com.ttyp.tiantao.assembly.mydialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ttyp.tiantao.R;

/**
 * 创建自定义的Dialog，主要学习实现原理
 * Created by admin on 2017/8/30.
 */

public class MyDialog2 extends Dialog {
    private LinearLayout conpon_yes;//确定按钮
    private ImageView callback;//取消按钮
    private TextView conponText;//消息文本

    private String titleStr;//从外界设置的title文本
    //确定文本和取消文本的显示的内容
    private onNoOnclickListener noOnclickListener;//取消按钮被点击了的监听器
    private onYesOnclickListener yesOnclickListener;//确定按钮被点击了的监听器

    public MyDialog2(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
    }

    /**
     * 设置取消按钮的显示内容和监听
     *
     * @param onNoOnclickListener
     */
    public MyDialog2 setNoOnclickListener( onNoOnclickListener onNoOnclickListener) {
        this.noOnclickListener = onNoOnclickListener;
        return this;
    }

    /**
     * 设置确定按钮的显示内容和监听
     *
     * @param yesOnclickListener
     */
    public MyDialog2 setYesOnclickListener( onYesOnclickListener yesOnclickListener) {
        this.yesOnclickListener = yesOnclickListener;
        return this;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mydialog2);
        //空白处不能取消动画
        setCanceledOnTouchOutside(false);

        //初始化界面控件
        initView();

        //初始化界面数据
        initData();
        //初始化界面控件的事件
        initEvent();
    }

    /**
     * 初始化界面控件
     */
    private void initView() {
        conpon_yes = findViewById(R.id.conpon_yes);
        callback = findViewById(R.id.conpon_callback);
        conponText = (TextView) findViewById(R.id.conpon_text);
    }

    /**
     * 初始化界面控件的显示数据
     */
    private void initData() {
        //如果用户自定了title和message
        if (titleStr != null) {
            conponText.setText(titleStr);
        }
    }

    /**
     * 初始化界面的确定和取消监听
     */
    private void initEvent() {
        //设置确定按钮被点击后，向外界提供监听
        conpon_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (yesOnclickListener != null) {
                    yesOnclickListener.onYesOnclick();
                }
            }
        });
        //设置取消按钮被点击后，向外界提供监听
        callback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (noOnclickListener != null) {
                    noOnclickListener.onNoClick();
                }
            }
        });
    }


    /**
     * 从外界Activity为Dialog设置优惠券内容
     *
     * @param conponText
     */
    public MyDialog2 setConponText(String conponText) {
        this.titleStr = conponText;
        return this;
    }


    public interface onNoOnclickListener {
        public void onNoClick();
    }

    public interface onYesOnclickListener {
        public void onYesOnclick();
    }
}