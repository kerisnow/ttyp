package com.ttyp.tiantao.ttmb.activity.me;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ttyp.tiantao.R;
import com.ttyp.tiantao.ttmb.activity.MyBaseActivity;
import com.ttyp.tiantao.ttmb.entity.UserEntity;
import com.ttyp.tiantao.ttmb.template.StaticValue;
import com.ttyp.tiantao.ttmb.template.URLValue;
import com.ttyp.tiantao.ttmb.util.ConnectLinkService;
import com.ttyp.tiantao.ttmb.util.CountDownTimersUtils;
import com.ttyp.tiantao.ttmb.util.GetJSON;
import com.ttyp.tiantao.ttmb.util.OnMultClickListener;

import java.util.HashMap;
import java.util.Map;

public class MeSettingAActivity extends MyBaseActivity {
    ImageView backImage;
    Button yesButton;
    LinearLayout alipayLayout;
    TextView alipayPhone;
    EditText alipayMessage;
    TextView alipayGetmessage;
    EditText alipayName;
    EditText alipayAccount;
    LinearLayout phoneLayout;
    TextView phoneOld;
    EditText phoneMessage;
    TextView phoneGetmessage;
    LinearLayout phoneLayout1;
    EditText phoneOld1;
    EditText phoneMessage1;
    TextView phoneGetmessage1;
    LinearLayout passwordLayout;
    TextView passwordPhone;
    EditText passwordMessage;
    TextView passwordGetmessage;
    EditText passwordNew;
    EditText passwordNew1;

    public static final int ALILAYOUT_SHOW = 0;
    public static final int PHONELAYOUT_SHOW = 1;
    public static final int PHONELAYOUT1_SHOW = 2;
    public static final int PASSWORDLAYOUT_SHOW = 3;

    CountDownTimersUtils timersUtils = null;
    CountDownTimersUtils timersUtils1 = null;
    private int typeKey = -1;
    private UserEntity userEntity;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_a);
        initView();
        initData();
        setListener();
    }

    private void initView(){
        backImage = findViewById(R.id.setting_a_backimage);
        yesButton = findViewById(R.id.setting_a_yes);
        alipayLayout = findViewById(R.id.setting_a_alipay_account_layout);
        alipayPhone = findViewById(R.id.setting_a_alipay_phone);
        alipayMessage = findViewById(R.id.setting_a_alipay_message);
        alipayGetmessage = findViewById(R.id.setting_a_alipay_getmessage);
        alipayName = findViewById(R.id.setting_a_alipay_name);
        alipayAccount = findViewById(R.id.setting_a_alipay_account);
        phoneLayout = findViewById(R.id.setting_a_phone_layout);
        phoneOld = findViewById(R.id.setting_a_phone);
        phoneMessage = findViewById(R.id.setting_a_phone_message);
        phoneGetmessage = findViewById(R.id.setting_a_phone_getmessage);
        phoneLayout1 = findViewById(R.id.setting_a_phone1_layout);
        phoneOld1 = findViewById(R.id.setting_a_phone1);
        phoneMessage1 = findViewById(R.id.setting_a_phone1_message);
        phoneGetmessage1 = findViewById(R.id.setting_a_phone1_getmessage);
        passwordLayout = findViewById(R.id.setting_a_password_layout);
        passwordPhone = findViewById(R.id.setting_a_password_phone);
        passwordMessage = findViewById(R.id.setting_a_password_message);
        passwordGetmessage = findViewById(R.id.setting_a_password_getmessage);
        passwordNew = findViewById(R.id.setting_a_password_new);
        passwordNew1 = findViewById(R.id.setting_a_password_new1);
    }

    private void initData(){
        Intent intent = getIntent();
        if(intent==null){
            exit();
        }
        Bundle bundle = intent.getExtras();
        typeKey = Integer.parseInt(bundle.getString("key"));
        setLayout();
    }

    private void setLayout(){
        userEntity = StaticValue.USER;
        switch (typeKey){
            case ALILAYOUT_SHOW:
                alipayLayout.setVisibility(View.VISIBLE);
                phoneLayout.setVisibility(View.GONE);
                phoneLayout1.setVisibility(View.GONE);
                passwordLayout.setVisibility(View.GONE);
                alipayPhone.setText(userEntity.getPhone());
                if(timersUtils!=null){
                    timersUtils.onFinish();
                }
                break;
            case PHONELAYOUT_SHOW:
                alipayLayout.setVisibility(View.GONE);
                phoneLayout.setVisibility(View.VISIBLE);
                phoneLayout1.setVisibility(View.GONE);
                passwordLayout.setVisibility(View.GONE);
                phoneOld.setText(userEntity.getPhone());
                if(timersUtils!=null){
                    timersUtils.onFinish();
                }
                break;
            case PHONELAYOUT1_SHOW:
                alipayLayout.setVisibility(View.GONE);
                phoneLayout.setVisibility(View.GONE);
                phoneLayout1.setVisibility(View.VISIBLE);
                passwordLayout.setVisibility(View.GONE);
                if(timersUtils1!=null){
                    timersUtils1.onFinish();
                }
                break;
            case PASSWORDLAYOUT_SHOW:
                alipayLayout.setVisibility(View.GONE);
                phoneLayout.setVisibility(View.GONE);
                phoneLayout1.setVisibility(View.GONE);
                passwordLayout.setVisibility(View.VISIBLE);
                passwordPhone.setText(userEntity.getPhone());
                if(timersUtils!=null){
                    timersUtils.onFinish();
                }
                break;
            default:
        }
    }

    private void setListener(){
        alipayGetmessage.setClickable(true);
        alipayGetmessage.setOnClickListener(clickListener);
    }

    private View.OnClickListener clickListener = new OnMultClickListener() {
        @Override
        public void onMultiClick(View v) {
            switch (v.getId()){
                case R.id.setting_a_alipay_getmessage:
                    sendmessage("alipay");
                    break;
                case R.id.setting_a_phone_getmessage:
                    sendmessage("phone");
                    break;
                case R.id.setting_a_phone1_getmessage:
                    sendmessage("phone1");
                    break;
                case R.id.setting_a_password_getmessage:
                    sendmessage("password");
                    break;
                case R.id.setting_a_backimage:
                    exit();
                    break;
                case R.id.setting_a_yes:
                    doCommit();
                    break;
            }
        }
    };

    private void sendmessage(final String key){
        String url1 = "";
        String phone1 = "";
        switch (key) {
            case "alipay":
                url1 = URLValue.SETTING_A_ALIPAYMESSAGE;
                phone1 = userEntity.getPhone();
                break;
            case "phone":
                url1 = URLValue.SETTING_A_PHONEMESSAGE;
                phone1 = userEntity.getPhone();
                break;
            case "phone1":
                url1 = URLValue.SETTING_A_PHONE1MESSAGE;
                phone1 = phoneOld1.getText().toString().trim();
                if (phone1 == null || "".equals(phone1)) {
                    toastShow("请输入新手机号");
                    return;
                }
                break;
            case "password":
                url1 = URLValue.SETTING_A_PASSWORDMESSAGE;
                phone1 = userEntity.getPhone();
                break;
        }
        final String phone = phone1;
        final String url = url1;
        Thread th1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    Map<String, String> params = new HashMap<>();
                    params.put("phone", phone);
                    Message message = new Message();
                    Bundle bundle = new Bundle();
                    Map<String, Object> result = GetJSON.getInstance().getJSON(ConnectLinkService.getInstance().ConnectHttpsPost(params, url));
                    if (result == null) {
                        message.what = 0;
                        bundle.putString("msg", "网络连接异常");
                        message.setData(bundle);
                        handler.sendMessage(message);
                        return;
                    }
                    int res = (int) result.get("res");
                    String msg = (String) result.get("msg");
                    if (res==0) {
                        message.what = 0;
                        bundle.putString("msg", msg);
                        message.setData(bundle);
                        handler.sendMessage(message);
                    } else if (res==1) {
                        message.what = 1;
                        bundle.putString("key", key + "getmessage");
                        message.setData(bundle);
                        handler.sendMessage(message);
                    } else {
                        message.what = 0;
                        bundle.putString("msg", "网络连接异常");
                        message.setData(bundle);
                        handler.sendMessage(message);
                    }
                } catch (Exception e) {
                    doCatchError(e,"settingA");
                }
            }
        });
        th1.start();

    }

    private void doCommit(){
        final String text1,text2,text3,text4;
        Thread th1 = null;
        switch (typeKey){
            case 0:
                text1 = userEntity.getPhone();
                text2 = alipayMessage.getText().toString().trim();
                text3 = alipayName.getText().toString().trim();
                text4 = alipayAccount.getText().toString().trim();
                if(text1==null||"".equals(text1)){
                    toastShow("获取信息异常");
                    return;
                }
                if(text2==null||"".equals(text2)){
                    toastShow("请输入验证码");
                    return;
                }
                if(text3==null||"".equals(text3)){
                    toastShow("支付宝收款人不能为空");
                    return;
                }
                if(text4==null||"".equals(text4)){
                    toastShow("请输入支付宝账号");
                    return;
                }
                th1 = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Message message = new Message();
                        Bundle bundle = new Bundle();
                        Map<String,String> params = new HashMap<>();
                        params.put("uid",userEntity.getUid());
                        params.put("code",text2);
                        params.put("alinick",text3);
                        params.put("alipay",text4);
                        Map<String,Object> result = GetJSON.getInstance().getJSON(ConnectLinkService.getInstance().ConnectHttpsPost(params,URLValue.SETTING_A_COMMIT));
                        if(result==null){
                            message.what = 0;
                            bundle.putString("msg","网络请求异常");
                            message.setData(bundle);
                            handler.sendMessage(message);
                            return;
                        }
                        int res = (int)result.get("res");
                        String msg = (String)result.get("msg");
                        if(res==0){
                            message.what = 0;
                            bundle.putString("msg",msg);
                            message.setData(bundle);
                            handler.sendMessage(message);
                        }else if(res==1){
                            message.what = 1;
                            bundle.putString("key","alipay");
                            bundle.putString("data",(String)result.get("data"));
                            message.setData(bundle);
                            handler.sendMessage(message);
                        }else {
                            message.what = 0;
                            bundle.putString("msg","网络请求异常");
                            message.setData(bundle);
                            handler.sendMessage(message);
                        }
                    }
                });
                break;
            case 1:
                text1 = userEntity.getPhone();
                text2 = phoneMessage.getText().toString().trim();
                if(text1==null||"".equals(text1)){
                    toastShow("获取信息异常");
                    return;
                }
                if(text2==null||"".equals(text2)){
                    toastShow("请输入验证码");
                    return;
                }
                th1 = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Message message = new Message();
                        Bundle bundle = new Bundle();
                        Map<String,String> params = new HashMap<>();
                        params.put("uid",userEntity.getUid());
                        params.put("code",text2);
                        Map<String,Object> result = GetJSON.getInstance().getJSON(ConnectLinkService.getInstance().ConnectHttpsPost(params,URLValue.SETTING_A_PHONE_COMMIT));
                        if(result==null){
                            message.what = 0;
                            bundle.putString("msg","网络请求异常");
                            message.setData(bundle);
                            handler.sendMessage(message);
                            return;
                        }
                        String res = (String)result.get("res");
                        String msg = (String)result.get("msg");
                        if(res.equals("0")){
                            message.what = 0;
                            bundle.putString("msg",msg);
                            message.setData(bundle);
                            handler.sendMessage(message);
                        }else if(res.equals("1")){
                            message.what = 1;
                            bundle.putString("key","phone");
                            bundle.putString("data",(String)result.get("data"));
                            message.setData(bundle);
                            handler.sendMessage(message);
                        }else {
                            message.what = 0;
                            bundle.putString("msg","网络请求异常");
                            message.setData(bundle);
                            handler.sendMessage(message);
                        }
                    }
                });
                break;
            case 2:
                text1 = phoneOld1.getText().toString().trim();
                text2 = phoneMessage1.getText().toString().trim();
                if(text1==null||"".equals(text1)){
                    toastShow("请输入新手机号");
                    return;
                }
                if(text2==null||"".equals(text2)){
                    toastShow("请输入验证码");
                    return;
                }
                th1 = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Message message = new Message();
                        Bundle bundle = new Bundle();
                        Map<String,String> params = new HashMap<>();
                        params.put("uid",userEntity.getUid());
                        params.put("phone",text1);
                        params.put("code",text2);
                        Map<String,Object> result = GetJSON.getInstance().getJSON(ConnectLinkService.getInstance().ConnectHttpsPost(params,URLValue.SETTING_A_COMMIT));
                        if(result==null){
                            message.what = 0;
                            bundle.putString("msg","网络请求异常");
                            message.setData(bundle);
                            handler.sendMessage(message);
                            return;
                        }
                        String res = (String)result.get("res");
                        String msg = (String)result.get("msg");
                        if(res.equals("0")){
                            message.what = 0;
                            bundle.putString("msg",msg);
                            message.setData(bundle);
                            handler.sendMessage(message);
                        }else if(res.equals("1")){
                            message.what = 1;
                            bundle.putString("key","phone1");
                            bundle.putString("data",(String)result.get("data"));
                            message.setData(bundle);
                            handler.sendMessage(message);
                        }else {
                            message.what = 0;
                            bundle.putString("msg","网络请求异常");
                            message.setData(bundle);
                            handler.sendMessage(message);
                        }
                    }
                });
                break;
            case 3:
                text1 = userEntity.getPhone();
                text2 = passwordMessage.getText().toString().trim();
                text3 = passwordNew.getText().toString().trim();
                text4 = passwordNew1.getText().toString().trim();
                if(text1==null||"".equals(text1)){
                    toastShow("获取信息异常");
                    return;
                }
                if(text2==null||"".equals(text2)){
                    toastShow("请输入验证码");
                    return;
                }
                if(text3==null||"".equals(text3)){
                    toastShow("请输入密码");
                    return;
                }
                if(text4==null||"".equals(text4)){
                    toastShow("请再次输入密码");
                    return;
                }
                if(!text3.equals(text4)){
                    toastShow("两次密码输入不一致，请重新输入");
                    return;
                }
                th1 = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Message message = new Message();
                        Bundle bundle = new Bundle();
                        Map<String,String> params = new HashMap<>();
                        params.put("uid",userEntity.getUid());
                        params.put("code",text2);
                        params.put("password",text3);
                        Map<String,Object> result = GetJSON.getInstance().getJSON(ConnectLinkService.getInstance().ConnectHttpsPost(params,URLValue.SETTING_A_COMMIT));
                        if(result==null){
                            message.what = 0;
                            bundle.putString("msg","网络请求异常");
                            message.setData(bundle);
                            handler.sendMessage(message);
                            return;
                        }
                        String res = (String)result.get("res");
                        String msg = (String)result.get("msg");
                        if(res.equals("0")){
                            message.what = 0;
                            bundle.putString("msg",msg);
                            message.setData(bundle);
                            handler.sendMessage(message);
                        }else if(res.equals("1")){
                            message.what = 1;
                            bundle.putString("key","password");
                            bundle.putString("data",(String)result.get("data"));
                            message.setData(bundle);
                            handler.sendMessage(message);
                        }else {
                            message.what = 0;
                            bundle.putString("msg","网络请求异常");
                            message.setData(bundle);
                            handler.sendMessage(message);
                        }
                    }
                });
                break;
        }
        if(th1!=null) {
            th1.start();
        }
    }

    @Override
    public void handlerW1(Bundle bundle) {
        super.handlerW1(bundle);
        String key = bundle.getString("key");
        switch (key){
            case "passwordgetmessage":
                timersUtils = new CountDownTimersUtils(passwordGetmessage,0,0);
                timersUtils.onTick(60000);//开始倒计时
                toastShow("发送成功");
                break;
            case "password":
                toastShow("修改成功");
                exit();
                break;
            case "phone":
                typeKey = 2;
                setLayout();
                break;
            case "phonegetmessage":
                timersUtils = new CountDownTimersUtils(phoneGetmessage,0,0);
                timersUtils.onTick(60000);//开始倒计时
                toastShow("发送成功");
                break;
            case "phone1":
                toastShow("修改成功");
                StaticValue.USER.setPhone(phoneOld1.getText().toString().trim());
                break;
            case "phone1getmessage":
                timersUtils = new CountDownTimersUtils(phoneGetmessage1,0,0);
                timersUtils.onTick(60000);//开始倒计时
                toastShow("发送成功");
                break;
            case "alipay":
                toastShow("保存成功");
                StaticValue.USER.setAlipayName(alipayName.getText().toString().trim());
                StaticValue.USER.setAlipayAcount(alipayAccount.getText().toString().trim());
                exit();
                break;
            case "alipaygetmessage":
                timersUtils = new CountDownTimersUtils(alipayGetmessage,0,0);
                timersUtils.onTick(60000);//开始倒计时
                toastShow("发送成功");
                break;
        }
    }
}
