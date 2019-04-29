package com.ttyp.tiantao.ttmb.activity.me;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.ttyp.tiantao.R;
import com.ttyp.tiantao.ttmb.activity.login.LoginActivity;
import com.ttyp.tiantao.ttmb.extendsparent.MyFragment;
import com.ttyp.tiantao.ttmb.template.FinalValue;
import com.ttyp.tiantao.ttmb.template.StaticValue;
import com.ttyp.tiantao.ttmb.template.URLValue;
import com.ttyp.tiantao.ttmb.util.ConnectLinkService;
import com.ttyp.tiantao.ttmb.util.GetJSON;
import com.ttyp.tiantao.ttmb.util.OnMultClickListener;

import java.util.HashMap;
import java.util.Map;

public class MeFragment extends MyFragment {
    LinearLayout taobSQ;
    ImageView meSetting;
    FrameLayout meMessage;
    TextView meMessageNum;
    LinearLayout turnToLogin;
    LinearLayout haveLogin;
    ImageView meUserHead;
    ImageView meShare;
    TextView meUserName;
    TextView meUserPhone;

    FrameLayout mePartener;
    TextView mePartenerText1;
    TextView mePartenerText2;
    TextView mePartenerText3;
    TextView mePartenerText4;
    TableRow mePartenerRow;

    ImageView imageView1;
    ImageView imageView2;
    ImageView imageView3;
    TextView textView1;
    TextView textView2;
    LinearLayout layout1;
    LinearLayout layout2;
    LinearLayout layout3;
    LinearLayout layout4;
    LinearLayout layout5;
    LinearLayout layout6;

    private static int showVisibleType = -1;
    private int messageNumber = 0;




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        View view = null;
        try{
            view = inflater.inflate(R.layout.fragment_wod_partner,container,false);
            initView(view);
            initData();
            setListener();
        }catch (Exception e){
            Log.i("me",e.toString());
            e.printStackTrace();
        }
        return view;
    }

    private void initView(View v){
        taobSQ = v.findViewById(R.id.me_taob_sq);
        meSetting = v.findViewById(R.id.me_setting);
        meMessage = v.findViewById(R.id.me_message);
        meMessageNum = v.findViewById(R.id.me_message_num);
        turnToLogin = v.findViewById(R.id.me_turnto_login);
        haveLogin = v.findViewById(R.id.me_have_login);
        meUserHead = v.findViewById(R.id.me_userhead);
        meShare = v.findViewById(R.id.me_share);
        meUserName = v.findViewById(R.id.me_user_name);
        meUserPhone = v.findViewById(R.id.me_user_phone);

        mePartener = v.findViewById(R.id.me_partener_layout1);
        mePartenerText1 = v.findViewById(R.id.me_partener_text1);
        mePartenerText2 = v.findViewById(R.id.me_partener_text2);
        mePartenerText3 = v.findViewById(R.id.me_partener_text3);
        mePartenerText4 = v.findViewById(R.id.me_partener_text4);
        mePartenerRow = v.findViewById(R.id.me_partener_row);

        imageView1 = v.findViewById(R.id.me_image1);
        imageView2 = v.findViewById(R.id.me_image2);
        imageView3 = v.findViewById(R.id.me_ad_image);
        textView1 = v.findViewById(R.id.me_text1);
        textView2 = v.findViewById(R.id.me_text2);
        layout1 = v.findViewById(R.id.me_layout1);
        layout2 = v.findViewById(R.id.me_layout2);
        layout3 = v.findViewById(R.id.me_layout3);
        layout4 = v.findViewById(R.id.me_layout4);
        layout5 = v.findViewById(R.id.me_layout5);
        layout6 = v.findViewById(R.id.me_layout6);

    }

    private void initData(){
        try {
            if (StaticValue.USER == null) {
                showVisibleType = -1;
                setLayoutVisible();
            } else {
                showVisibleType = StaticValue.USER.getIdentification();
                setLayoutVisible();
                getData();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void getData(){
        /**
         * 获取未读信息数  getmessagenum
         */
        Thread th1 = new Thread(new Runnable() {
            @Override
            public void run() {
                Map<String,String> params = new HashMap<>();
                Message message = new Message();
                Bundle b = new Bundle();
                Map<String,Object> result = GetJSON.getInstance().getJSON(ConnectLinkService.getInstance().ConnectHttpsPost(params, URLValue.ME_MESSAGE));
                if(result==null){
                    message.what = 0;
                    b.putString("msg","网络请求异常");
                    message.setData(b);
                    handler.sendMessage(message);
                    return;
                }
                int res = (int) result.get("res");
                String msg = (String) result.get("msg");
                if(res==0){
                    message.what = 0;
                    b.putString("msg",msg);
                    message.setData(b);
                    handler.sendMessage(message);
                }else if(res==1){
                    message.what = 1;
                    b.putString("key","getmessagenum");
                    b.putString("data",(String) result.get("data"));
                    message.setData(b);
                    handler.sendMessage(message);
                }else {
                    message.what = 0;
                    b.putString("msg","网络请求异常");
                    message.setData(b);
                    handler.sendMessage(message);
                }
            }
        });
//        th1.start();
        if (showVisibleType==1) {
            /**
             * 获取合伙人数据   getpartner
             */
            Thread th2 = new Thread(new Runnable() {
                @Override
                public void run() {
                    Map<String, String> params = new HashMap<>();
                    Message message = new Message();
                    Bundle b = new Bundle();
                    Map<String, Object> result = GetJSON.getInstance().getJSON(ConnectLinkService.getInstance().ConnectHttpsPost(params, URLValue.ME_PARTENER));
                    if (result == null) {
                        message.what = 0;
                        b.putString("msg", "网络请求异常");
                        message.setData(b);
                        handler.sendMessage(message);
                        return;
                    }
                    int res = (int) result.get("res");
                    String msg = (String) result.get("msg");
                    if (res==0) {
                        message.what = 0;
                        b.putString("msg", msg);
                        message.setData(b);
                        handler.sendMessage(message);
                    } else if (res==0) {
                        message.what = 1;
                        b.putString("key", "getpartner");
                        b.putString("data", (String) result.get("data"));
                        message.setData(b);
                        handler.sendMessage(message);
                    } else {
                        message.what = 0;
                        b.putString("msg", "网络请求异常");
                        message.setData(b);
                        handler.sendMessage(message);
                    }
                }
            });
//            th2.start();
        }
    }


    private void setLayoutVisible(){
        try {
            switch (showVisibleType) {
                case 0:
                    taobSQ.setVisibility(View.VISIBLE);
                    meMessageNum.setVisibility(View.GONE);
                    turnToLogin.setVisibility(View.GONE);
                    haveLogin.setVisibility(View.VISIBLE);
                    imageView1.setImageResource(R.drawable.me_medal);
                    meUserName.setText(StaticValue.USER.getPickName());
                    meUserPhone.setText(StaticValue.USER.getPhone());
                    textView1.setTextColor(getResources().getColor(R.color.gray_1));
                    textView1.setText("粉丝");
                    imageView2.setImageResource(R.drawable.me_integral2);
                    textView2.setTextColor(getResources().getColor(R.color.gray_1));
                    textView2.setText(StaticValue.USER.getIntegral() + "分");
                    meShare.setVisibility(View.GONE);
                    mePartener.setVisibility(View.GONE);
                    mePartenerRow.setVisibility(View.GONE);
                    break;
                case 1:
                    taobSQ.setVisibility(View.VISIBLE);
                    meMessageNum.setVisibility(View.GONE);
                    turnToLogin.setVisibility(View.GONE);
                    haveLogin.setVisibility(View.VISIBLE);
                    imageView1.setImageResource(R.drawable.me_vip);
                    meUserName.setText(StaticValue.USER.getPickName());
                    meUserPhone.setText(StaticValue.USER.getPhone());
                    textView1.setTextColor(getResources().getColor(R.color.god));
                    textView1.setText("合伙人");
                    imageView2.setImageResource(R.drawable.me_integral);
                    textView2.setTextColor(getResources().getColor(R.color.god));
                    textView2.setText(StaticValue.USER.getIntegral() + "分");
                    meShare.setVisibility(View.VISIBLE);
                    mePartener.setVisibility(View.VISIBLE);
                    mePartenerRow.setVisibility(View.VISIBLE);
                    break;
                default:
                    taobSQ.setVisibility(View.INVISIBLE);
                    meMessageNum.setVisibility(View.GONE);
                    turnToLogin.setVisibility(View.VISIBLE);
                    haveLogin.setVisibility(View.GONE);
                    meUserHead.setImageResource(R.drawable.me_head_image);
                    meShare.setVisibility(View.GONE);
                    mePartener.setVisibility(View.GONE);
                    mePartenerRow.setVisibility(View.GONE);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void setListener() {
        try{
            taobSQ.setOnClickListener(listener);
            meSetting.setOnClickListener(listener);
            meMessage.setOnClickListener(listener);
            imageView2.setOnClickListener(listener);
            turnToLogin.setClickable(true);
            turnToLogin.setOnClickListener(listener);
            layout1.setOnClickListener(listener);
            layout2.setOnClickListener(listener);
            layout3.setOnClickListener(listener);
            layout4.setOnClickListener(listener);
            layout5.setOnClickListener(listener);
            layout6.setOnClickListener(listener);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    private OnMultClickListener listener = new OnMultClickListener() {
        @Override
        public void onMultiClick(View v) {
            try {
                if (showVisibleType < 0) {
                    fragmentResultData.turnForResultToActivity(LoginActivity.class, new HashMap<String, String>(), FinalValue.MAIN_ACTIVTTY_ME);
                } else {
                    switch (v.getId()) {
                        case R.id.me_taob_sq:
                            //跳转淘宝授权页

                            break;
                        case R.id.me_setting:
                            //跳转设置页
//                        Intent intent = new Intent(getContext())
                            fragmentResultData.turnForResultToActivity(MeSettingActivity.class,new HashMap<String, String>(),FinalValue.SETIING_ACTIVTTY);
                            break;
                        case R.id.me_message:
                            //跳转消息页
                            break;
                        case R.id.me_ad_image:
                            //跳转广告链接页
                            break;
                        case R.id.me_layout1:
                            //跳转粉丝订单页
                            break;
                        case R.id.me_layout2:
                            //跳转消息编辑页
                            break;
                        case R.id.me_layout3:
                            //跳转我的淘宝订单页
                            toastShow("敬请期待");
                            break;
                        case R.id.me_layout4:
                            //跳转积分兑换
                            toastShow("敬请期待");
                            break;
                        case R.id.me_layout5:
                            //跳转集分宝
                            toastShow("敬请期待");
                            break;
                        case R.id.me_layout6:
                            //跳转帮助中心
                            toastShow("敬请期待");
                            break;

                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    };

    @Override
    public void handlerW1(Bundle bundle) {
        super.handlerW1(bundle);
        String key = bundle.getString("key");
        switch (key){
            case "getpartner":
                break;
            case "getmessagenum":
                break;
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        initData();
        Log.e("userVisible","123123213123");
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(!hidden){
            initData();
        }
    }

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
        Log.e("me_onActivityResult",requestCode+"___"+resultCode);
        try {
            if(requestCode==FinalValue.MAIN_ACTIVTTY_ME){
                switch (resultCode){
                    case FinalValue.LOGIN_ACTIVTTY:
                    case FinalValue.SETIING_ACTIVTTY:
                        initData();
                        break;

                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
