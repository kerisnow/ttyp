package com.ttyp.tiantao.ttmb.activity.rechargecard;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jaychan.view.MultipleTextView;
import com.ttyp.tiantao.R;
import com.ttyp.tiantao.ttmb.activity.login.LoginActivity;
import com.ttyp.tiantao.ttmb.extendsparent.MyFragment;
import com.ttyp.tiantao.ttmb.template.FinalValue;
import com.ttyp.tiantao.ttmb.template.StaticValue;
import com.ttyp.tiantao.ttmb.util.OnMultClickListener;

import java.util.HashMap;

public class RechargeCardFragment extends MyFragment {
    MultipleTextView textView1;
    ImageView imageView;
    ImageView linearLayout1;
    ImageView linearLayout2;
    ImageView linearLayout3;
    ImageView linearLayout4;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        View view = null;
        try{
            view = inflater.inflate(R.layout.fragment_dixk,container,false);
            initView(view);
            setListener();
        }catch (Exception e){
            e.printStackTrace();
            Log.e("recharge",e.toString());
        }
        return view;
    }

    private void initView(View view){
        textView1 = view.findViewById(R.id.dixk_text1);     //抵现卡金额
        imageView = view.findViewById(R.id.dixk_ad_image);      //充值记录图标
        linearLayout1 = view.findViewById(R.id.dixk_layout1);       //充值
        linearLayout2 = view.findViewById(R.id.dixk_layout2);       //充值记录
        linearLayout3 = view.findViewById(R.id.dixk_layout3);       //消费记录
        linearLayout4 = view.findViewById(R.id.dixk_layout4);       //领取的卡片
    }



    private void setListener(){
        try{
            if(StaticValue.USER==null){
                fragmentResultData.turnToActivity(LoginActivity.class,new HashMap<String, String>());
                return;
            }
            textView1.setText(StaticValue.USER.getRechargeMoney());
            linearLayout1.setOnClickListener(listener);
            linearLayout2.setOnClickListener(listener);
            linearLayout3.setOnClickListener(listener);
            linearLayout4.setOnClickListener(listener);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    OnMultClickListener listener = new OnMultClickListener() {
        @Override
        public void onMultiClick(View v) {
            try{
                switch (v.getId()){
                    case R.id.dixk_layout1:
                        fragmentResultData.turnForResultToActivity(DiXKRechargeActivity.class,new HashMap<String, String>(), FinalValue.MAIN_ACTIVTTY_RECHARGE);
                        break;
                    case R.id.dixk_layout2:
                        fragmentResultData.turnToActivity(RechargeListActivity.class,new HashMap<String, String>());
                        break;
                    case R.id.dixk_layout3:
                        fragmentResultData.turnToActivity(ConsumeListActivity.class,new HashMap<String, String>());
                        break;
                    case R.id.dixk_layout4:
                       toastShow("敬请期待");
                        break;
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    };


    @Override
    public boolean canCallBack() {
        return true;
    }

    @Override
    public void callBack() {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == FinalValue.MAIN_ACTIVTTY_RECHARGE) {
                switch (resultCode) {
                    case FinalValue.RECHARGE_ACTIVTTY:
                    case FinalValue.LOGIN_ACTIVTTY:
                        setListener();
                        break;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
