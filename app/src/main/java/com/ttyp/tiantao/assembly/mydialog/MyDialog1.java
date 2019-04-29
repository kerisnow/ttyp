package com.ttyp.tiantao.assembly.mydialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ttyp.tiantao.R;

/**
 * 创建自定义的Dialog，主要学习实现原理
 * Created by admin on 2017/8/30.
 */

public class MyDialog1 extends Dialog {
    private Button yes;//确定按钮
    private Button no;//取消按钮
    private TextView title;//消息标题文本
    private TextView title1;//消息标题文本
    private EditText editText;//消息提示文本

    private String titleStr;//从外界设置的title文本
    private String title1Str;//从外界设置的title文本
    private String editHint;
    //确定文本和取消文本的显示的内容
    private String yesStr, noStr;
    private onNoOnclickListener noOnclickListener;//取消按钮被点击了的监听器
    private onYesOnclickListener yesOnclickListener;//确定按钮被点击了的监听器

    public MyDialog1(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
    }

    /**
     * 设置取消按钮的显示内容和监听
     * @param str
     * @param onNoOnclickListener
     */
    public MyDialog1 setNoOnclickListener(String str, onNoOnclickListener onNoOnclickListener) {
        if (str != null) {
            noStr = str;
        }
        this.noOnclickListener = onNoOnclickListener;
        return this;
    }

    /**
     * 设置确定按钮的显示内容和监听
     *
     * @param str
     * @param yesOnclickListener
     */
    public MyDialog1 setYesOnclickListener(String str, onYesOnclickListener yesOnclickListener) {
        if (str != null) {
            yesStr = str;
        }
        this.yesOnclickListener = yesOnclickListener;
        return this;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mydialog);
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
        yes = findViewById(R.id.dialog_yes);
        no = findViewById(R.id.dialog_no);
        title = (TextView) findViewById(R.id.dialog_title);
        title1 = (TextView) findViewById(R.id.dialog_title1);
        editText = (EditText) findViewById(R.id.dialog_edit_text);
    }

    /**
     * 初始化界面控件的显示数据
     */
    private void initData() {
        //如果用户自定了title和message
        if (titleStr != null) {
            title.setText(titleStr);
        }
        if (title1Str != null) {
            title1.setText(titleStr);
            title1.setVisibility(View.VISIBLE);
        }else {
            title1.setVisibility(View.GONE);
        }
        if (editHint != null) {
            editText.setHint(editHint);
            editText.setVisibility(View.VISIBLE);
        }else {
            editText.setVisibility(View.GONE);
        }
        //如果设置按钮文字
        if (yesStr != null) {
            yes.setText(yesStr);
        }
        if (noStr != null) {
            no.setText(noStr);
        }
    }

    /**
     * 初始化界面的确定和取消监听
     */
    private void initEvent() {
        //设置确定按钮被点击后，向外界提供监听
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (yesOnclickListener != null) {
                    yesOnclickListener.onYesOnclick(editText.getText().toString().trim());
                }
            }
        });
        //设置取消按钮被点击后，向外界提供监听
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (noOnclickListener != null) {
                    noOnclickListener.onNoClick();
                }
            }
        });
    }

    public MyDialog1 setEditHint(String edit) {
        editHint = edit;
        return this;
    }

    /**
     * 从外界Activity为Dialog设置标题
     *
     * @param title
     */
    public MyDialog1 setTitle(String title) {
        titleStr = title;
        return this;
    }
    /**
     * 从外界Activity为Dialog设置标题
     *
     * @param title1
     */
    public MyDialog1 setTitle1(String title1) {
        title1Str = title1;
        return this;
    }

    public String getEditText() {
        return editText.getText().toString().trim();
    }

    public interface onNoOnclickListener {
        public void onNoClick();
    }

    public interface onYesOnclickListener {
        public void onYesOnclick(String editText);
    }
}