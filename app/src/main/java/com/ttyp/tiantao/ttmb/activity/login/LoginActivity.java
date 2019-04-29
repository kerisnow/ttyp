package com.ttyp.tiantao.ttmb.activity.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ttyp.tiantao.R;
import com.ttyp.tiantao.ttmb.activity.MyBaseActivity;
import com.ttyp.tiantao.ttmb.entity.UserEntity;
import com.ttyp.tiantao.ttmb.template.FinalValue;
import com.ttyp.tiantao.ttmb.template.StaticValue;
import com.ttyp.tiantao.ttmb.template.URLValue;
import com.ttyp.tiantao.ttmb.util.BaseUtil;
import com.ttyp.tiantao.ttmb.util.ConnectLinkService;
import com.ttyp.tiantao.ttmb.util.CountDownTimersUtils;
import com.ttyp.tiantao.ttmb.util.GetJSON;
import com.ttyp.tiantao.ttmb.util.OnMultClickListener;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends MyBaseActivity {
//    页面组件
//    返回
    ImageView backImage;
    //登录块
    LinearLayout login_normal;
    TextView login_normal_register;
    EditText login_normal_edit1;
    EditText login_normal_edit2;
    LinearLayout login_normal_login;
    TextView login_normal_fogpassword;
    TextView login_normal_messagelogin;
    //快捷登录
    LinearLayout login_message;
    EditText login_message_edit1;
    EditText login_message_edit2;
    TextView login_message_register;
    LinearLayout login_message_login;
    //忘记密码
    LinearLayout forget_password;
    EditText forget_password_edit1;
    EditText forget_password_edit2;
    TextView forget_password_register;
    EditText forget_password_edit3;
    EditText forget_password_edit4;
    LinearLayout forget_password_login;
    //注册块1
    LinearLayout registered1;
    EditText registered1_edit1;
    EditText registered1_edit2;
    TextView registered1_register;
    TextView registered1_text1;
    LinearLayout registered1_login;
    //注册块 2
    LinearLayout registered2;
    EditText registered2_edit1;
    EditText registered2_edit2;
    LinearLayout registered2_login;
    //注册块 2
    LinearLayout registered3;
    EditText registered3_edit1;
    TextView registered3_skip;
    LinearLayout registered3_login;

    String currentPage = "loginnormal";
    String parentPage = "";

    CountDownTimersUtils timersUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        try {
            initView();
            initData();
            setListener();
//        loadWeb();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            chooseLabel(parentPage);
            return true;
        }else {
            return super.onKeyDown(keyCode, event);
        }
    }

    private void initView(){
        backImage = findViewById(R.id.login_back_image);
        login_normal = findViewById(R.id.login_normal);
        login_normal_register = findViewById(R.id.login_normal_register);
        login_normal_edit1 = findViewById(R.id.login_normal_edit1);
        login_normal_edit2 = findViewById(R.id.login_normal_edit2);
        login_normal_login = findViewById(R.id.login_normal_login);
        login_normal_fogpassword = findViewById(R.id.login_normal_fogpassword);
        login_normal_messagelogin = findViewById(R.id.login_normal_messagelogin);
        login_message = findViewById(R.id.login_message);
        login_message_edit1 = findViewById(R.id.login_message_edit1);
        login_message_edit2 = findViewById(R.id.login_message_edit2);
        login_message_register = findViewById(R.id.login_message_register);
        login_message_login = findViewById(R.id.login_message_login);
        forget_password = findViewById(R.id.forget_password);
        forget_password_edit1 = findViewById(R.id.forget_password_edit1);
        forget_password_edit2 = findViewById(R.id.forget_password_edit2);
        forget_password_edit3 = findViewById(R.id.forget_password_edit3);
        forget_password_edit4 = findViewById(R.id.forget_password_edit4);
        forget_password_register = findViewById(R.id.forget_password_register);
        forget_password_login = findViewById(R.id.forget_password_login);
        registered1 = findViewById(R.id.registered1);
        registered1_edit1 = findViewById(R.id.registered1_edit1);
        registered1_edit2 = findViewById(R.id.registered1_edit2);
        registered1_register = findViewById(R.id.registered1_register);
        registered1_text1 = findViewById(R.id.registered1_text1);
        registered1_login = findViewById(R.id.registered1_login);
        registered2 = findViewById(R.id.registered2);
        registered2_edit1 = findViewById(R.id.registered2_edit1);
        registered2_edit2 = findViewById(R.id.registered2_edit2);
        registered2_login = findViewById(R.id.registered2_login);
        registered3_login = findViewById(R.id.registered3_login);
        registered3_edit1 = findViewById(R.id.registered3_edit1);
        registered3_skip = findViewById(R.id.registered3_skip);
        registered3 = findViewById(R.id.registered3);
    }

    private void initData(){
        try {
            Intent intent = getIntent();
            Bundle bundle = intent.getExtras();
            String showKey = "loginnormal";
            if (bundle != null) {
                showKey = bundle.getString("showkey");
            }
            chooseLabel(showKey);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void setListener(){
        try{
            backImage.setOnClickListener(listener);
            login_normal_register.setOnClickListener(listener);
            login_normal_login.setOnClickListener(listener);
            login_normal_fogpassword.setOnClickListener(listener);
            login_normal_messagelogin.setOnClickListener(listener);
            login_normal_edit1.setOnEditorActionListener(onEditorActionListener);
            login_normal_edit2.setOnEditorActionListener(onEditorActionListener);
            login_message.setOnClickListener(listener);
            login_message_register.setOnClickListener(listener);
            login_message_login.setOnClickListener(listener);
            login_message_edit1.setOnEditorActionListener(onEditorActionListener);
            login_message_edit2.setOnEditorActionListener(onEditorActionListener);
            forget_password_edit1.setOnEditorActionListener(onEditorActionListener);
            forget_password_edit2.setOnEditorActionListener(onEditorActionListener);
            forget_password_edit3.setOnEditorActionListener(onEditorActionListener);
            forget_password_edit4.setOnEditorActionListener(onEditorActionListener);
            forget_password_register.setOnClickListener(listener);
            forget_password_login.setOnClickListener(listener);
            registered1_edit1.setOnEditorActionListener(onEditorActionListener);
            registered1_edit2.setOnEditorActionListener(onEditorActionListener);
            registered1_edit2.setOnEditorActionListener(onEditorActionListener);
            registered1_register.setOnClickListener(listener);
            registered1_text1.setOnClickListener(listener);
            registered1_login.setOnClickListener(listener);
            registered2_edit1.setOnEditorActionListener(onEditorActionListener);
            registered2_edit2.setOnEditorActionListener(onEditorActionListener);
            registered2_login.setOnClickListener(listener);
            registered3_skip.setOnClickListener(listener);
            registered3_edit1.setOnEditorActionListener(onEditorActionListener);
            registered2_login.setOnClickListener(listener);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void chooseLabel(String showKey){
        try {
            switch (showKey) {
                case "loginnormal":
                    login_normal.setVisibility(View.VISIBLE);
                    login_message.setVisibility(View.GONE);
                    forget_password.setVisibility(View.GONE);
                    registered1.setVisibility(View.GONE);
                    registered2.setVisibility(View.GONE);
                    registered3.setVisibility(View.GONE);
                    registered3_skip.setVisibility(View.GONE);

                    currentPage = showKey;
                    parentPage = "";
                    break;
                case "loginmessage":
                    login_normal.setVisibility(View.GONE);
                    login_message.setVisibility(View.VISIBLE);
                    forget_password.setVisibility(View.GONE);
                    registered1.setVisibility(View.GONE);
                    registered2.setVisibility(View.GONE);
                    registered3.setVisibility(View.GONE);
                    registered3_skip.setVisibility(View.GONE);

                    currentPage = showKey;
                    parentPage = "loginnormal";
                    break;
                case "fogpassword":
                    login_normal.setVisibility(View.GONE);
                    login_message.setVisibility(View.GONE);
                    forget_password.setVisibility(View.VISIBLE);
                    registered1.setVisibility(View.GONE);
                    registered2.setVisibility(View.GONE);
                    registered3.setVisibility(View.GONE);
                    registered3_skip.setVisibility(View.GONE);

                    currentPage = showKey;
                    parentPage = "loginnormal";
                    break;
                case "register1":
                    login_normal.setVisibility(View.GONE);
                    login_message.setVisibility(View.GONE);
                    forget_password.setVisibility(View.GONE);
                    registered1.setVisibility(View.VISIBLE);
                    registered2.setVisibility(View.GONE);
                    registered3.setVisibility(View.GONE);
                    registered3_skip.setVisibility(View.GONE);

                    currentPage = showKey;
                    parentPage = "loginnormal";
                    break;
                case "registered2":
                    login_normal.setVisibility(View.GONE);
                    login_message.setVisibility(View.GONE);
                    forget_password.setVisibility(View.GONE);
                    registered1.setVisibility(View.GONE);
                    registered2.setVisibility(View.VISIBLE);
                    registered3.setVisibility(View.GONE);
                    registered3_skip.setVisibility(View.GONE);

                    currentPage = showKey;
                    parentPage = "register1";
                    break;
                case "registered3":
                    login_normal.setVisibility(View.GONE);
                    login_message.setVisibility(View.GONE);
                    forget_password.setVisibility(View.GONE);
                    registered1.setVisibility(View.GONE);
                    registered2.setVisibility(View.GONE);
                    registered3.setVisibility(View.VISIBLE);
                    registered3_skip.setVisibility(View.VISIBLE);

                    currentPage = showKey;
                    parentPage = "registered2";
                    break;
                case "registered4":
                    login_normal.setVisibility(View.GONE);
                    login_message.setVisibility(View.GONE);
                    forget_password.setVisibility(View.GONE);
                    registered1.setVisibility(View.GONE);
                    registered2.setVisibility(View.GONE);
                    registered3.setVisibility(View.VISIBLE);
                    registered3_skip.setVisibility(View.GONE);

                    currentPage = showKey;
                    parentPage = "";
                    break;
                case "":
                    exit();
                    break;

            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private TextView.OnEditorActionListener onEditorActionListener = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if(event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                Boolean res = false;
                try {
                    switch (v.getId()) {
                        case R.id.login_normal_edit1:
                            login_normal_edit2.requestFocus();
                            res = true;
                            break;
                        case R.id.login_normal_edit2:
                            setKeyP();
                            login_normal_login.performClick();
                            res = true;
                            break;
                        case R.id.login_message_edit1:
                            login_message_edit2.requestFocus();
                            res = true;
                            break;
                        case R.id.login_message_edit2:
                            setKeyP();
                            login_normal_login.performClick();
                            res = true;
                            break;
                        case R.id.forget_password_edit1:
                            forget_password_edit2.requestFocus();
                            res = true;
                            break;
                        case R.id.forget_password_edit2:
                            forget_password_edit3.requestFocus();
                            res = true;
                            break;
                        case R.id.forget_password_edit3:
                            forget_password_edit4.requestFocus();
                            res = true;
                            break;
                        case R.id.forget_password_edit4:
                            setKeyP();
                            forget_password_login.performClick();
                            res = true;
                            break;
                        case R.id.registered1_edit1:
                            registered1_edit2.requestFocus();
                            res = true;
                            break;
                        case R.id.registered1_edit2:
                            setKeyP();
                            registered1_login.performClick();
                            res = true;
                            break;
                        case R.id.registered2_edit1:
                            registered2_edit2.requestFocus();
                            res = true;
                            break;
                        case R.id.registered2_edit2:
                            setKeyP();
                            registered2_login.performClick();
                            res = true;
                            break;
                        case R.id.registered3_edit1:
                            setKeyP();
                            registered3_login.performClick();
                            res = true;
                            break;
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
                return res;
            }
            return false;
        }
    };

    private OnMultClickListener listener = new OnMultClickListener() {
        @Override
        public void onMultiClick(View v) {
            try {
                switch (v.getId()) {
                    case R.id.login_back_image:
                        chooseLabel(parentPage);
                        break;
                    case R.id.login_normal_register:
                        parentPage = currentPage;
                        currentPage = "register1";
                        chooseLabel(currentPage);
                        break;
                    case R.id.login_normal_fogpassword:
                        parentPage = currentPage;
                        currentPage = "fogpassword";
                        chooseLabel(currentPage);
                        break;
                    case R.id.login_normal_messagelogin:
                        parentPage = currentPage;
                        currentPage = "loginmessage";
                        chooseLabel(currentPage);
                        break;
                    case R.id.login_normal_login:
                        login();
                        break;
                    case R.id.login_message_login:
                        mesageLogin();
                        break;
                    case R.id.login_message_register:
                        sendMessage("loginmessage");
                        break;
                    case R.id.forget_password_register:
                        sendMessage("forgetpassword");
                        break;
                    case R.id.forget_password_login:
                        forgetPasswordLogin();
                        break;
                    case R.id.registered1_register:
                        sendMessage("registered");
                        break;
                    case R.id.registered1_login:
                        registered();
                        break;
                    case R.id.registered1_text1:
                        showText();
                        break;
                    case R.id.registered2_login:
                        registeredLogin();
                        break;
                    case R.id.registered3_login:
                        invitation_Login();
                        break;
                    case R.id.registered3_skip:
                        exit();
                        break;
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    };

    private void setKeyP(){
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        boolean isOpen=imm.isActive();//isOpen若返回true，则表示输入法打开
        if (isOpen){
            imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    private void login(){
        final String num = login_normal_edit1.getText().toString();
        final String pas = login_normal_edit2.getText().toString();
        if(num==null||num.trim().equals("")){
            toastShow("请填写手机号");
            return;
        }
        if(pas==null||pas.trim().equals("")){
            toastShow("请填写密码");
            return;
        }
        if(!BaseUtil.CheckPhone(num)){
            toastShow("请填写正确的手机号");
            return;
        }
        Thread th1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    ConnectLinkService linkService = ConnectLinkService.getInstance();
                    Message message = new Message();
                    Bundle b = new Bundle();
                    Map<String, String> value = new HashMap<>();
                    value.put("phone", num);
                    value.put("password", BaseUtil.encryptToSHA(pas));
//                String params = SetJSON.setJSON(value);
                    Map<String, Object> result = GetJSON.getInstance().getJSON(linkService.ConnectHttpsPost(value, URLValue.PasswordLOGIN));
                    if (result == null) {
                        message.what = 0;
                        b.putString("msg", "网络连接异常");
                        message.setData(b);
                        handler.sendMessage(message);
                        return;
                    }
                    int res = (int) result.get("res");
                    String msg = (String) result.get("msg");
                    String uid = (String) result.get("uid");
                    if (res == 1) {
                        StaticValue.UID = uid;
                        message.what = 1;
                        b.putString("uid", uid);
                        message.setData(b);
                    } else if (res == 0) {
                        message.what = 0;
                        b.putString("msg", msg);
                        message.setData(b);
                    } else {
                        message.what = 0;
                        b.putString("msg", "网络连接异常");
                        message.setData(b);
                    }
                    handler.sendMessage(message);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }) ;
        th1.start();
    }

    private void mesageLogin(){
        Thread th1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    String phone = login_message_edit1.getText().toString().trim();
                    String code = login_message_edit2.getText().toString().trim();
                    Map<String,String> params = new HashMap<>();
                    Bundle b = new Bundle();
                    Message message = new Message();
                    if (phone == null || "".equals(phone)){
                        message.what = 0;
                        b.putString("msg","请输入手机号");
                        message.setData(b);
                        handler.sendMessage(message);
                        return;
                    }
                    if (code==null||"".equals(code)){
                        message.what = 0;
                        b.putString("msg","请输入验证码");
                        message.setData(b);
                        handler.sendMessage(message);
                        return;
                    }
                    params.put("phone",phone);
                    params.put("code",code);
                    Map<String,Object> result = GetJSON.getInstance().getJSON(ConnectLinkService.getInstance().ConnectHttpsPost(params,URLValue.MessageLogin));
                    if(result==null){
                        message.what = 0;
                        b.putString("msg","网络连接异常");
                        message.setData(b);
                        handler.sendMessage(message);
                        return;
                    }
                    int res = (int)result.get("res");
                    String msg = (String)result.get("msg");
                    if(res==0){
                        message.what = 0;
                        b.putString("msg",msg);
                        message.setData(b);
                        handler.sendMessage(message);
                        return;
                    }else if (res==1){
                        String uid = (String) result.get("uid");
                        if(uid == null || "".equals(uid)){
                            message.what = 0;
                            b.putString("msg",msg);
                            message.setData(b);
                            handler.sendMessage(message);
                            return;
                        }else {
                            StaticValue.USER = new UserEntity(uid);
                            StaticValue.UID = uid;
                            exit();
                        }
                    }else {
                        message.what = 0;
                        b.putString("msg","网络连接异常");
                        message.setData(b);
                        handler.sendMessage(message);
                        return;
                    }

                }catch (Exception e){
                    doCatchError(e,"login");
                    e.printStackTrace();
                }
            }
        });
        th1.start();
    }

    private void forgetPasswordLogin(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                String phone = forget_password_edit1.getText().toString().trim();
                String code = forget_password_edit2.getText().toString().trim();
                String password1 = forget_password_edit3.getText().toString().trim();
                String password2 = forget_password_edit4.getText().toString().trim();
                Map<String,String> params = new HashMap<>();
                Bundle b = new Bundle();
                Message message = new Message();
                if(phone==null || "".equals(phone)){
                    message.what = 0;
                    b.putString("msg","请输入手机号");
                    message.setData(b);
                    handler.sendMessage(message);
                    return;
                }
                if(code==null || "".equals(code)){
                    message.what = 0;
                    b.putString("msg","请输入验证码");
                    message.setData(b);
                    handler.sendMessage(message);
                    return;
                }
                if(password1==null || password2 == null || "".equals(password1) || "".equals(password2)){
                    message.what = 0;
                    b.putString("msg","请设置新密码");
                    message.setData(b);
                    handler.sendMessage(message);
                    return;
                }
                if (!password1.equals(password2)){
                    message.what = 0;
                    b.putString("msg","两次密码设置不一样，请重新输入");
                    message.setData(b);
                    handler.sendMessage(message);
                    return;
                }
                Map<String,Object> result = GetJSON.getInstance().getJSON(ConnectLinkService.getInstance().ConnectHttpsPost(params,URLValue.ForgetLogin));
                if(result == null){
                    message.what = 0;
                    b.putString("msg","网络连接异常");
                    message.setData(b);
                    handler.sendMessage(message);
                    return;
                }
                int res = (int)result.get("res");
                String msg = (String)result.get("msg");
                if(res==0){
                    message.what = 0;
                    b.putString("msg",msg);
                    message.setData(b);
                    handler.sendMessage(message);
                    return;
                }else if(res==1){
                    String uid = (String) result.get("uid");
                    StaticValue.USER = new UserEntity(uid);
                    exit();
                }else {
                    message.what = 0;
                    b.putString("msg","网络连接异常");
                    message.setData(b);
                    handler.sendMessage(message);
                    return;
                }
            }
        });
        thread.start();
    }

    private void registered(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String phone = registered1_edit1.getText().toString().trim();
                    String code = registered1_edit2.getText().toString().trim();
                    Map<String, String> params = new HashMap<>();
                    Bundle b = new Bundle();
                    Message message = new Message();
                    if (phone == null || "".equals(phone)) {
                        message.what = 0;
                        b.putString("msg", "请输入手机号");
                        message.setData(b);
                        handler.sendMessage(message);
                        return;
                    }
                    if (code == null || "".equals(code)) {
                        message.what = 0;
                        b.putString("msg", "请输入验证码");
                        message.setData(b);
                        handler.sendMessage(message);
                        return;
                    }
                    if(!BaseUtil.CheckMacth(phone,BaseUtil.PhonePattern)){
                        message.what = 0;
                        b.putString("msg","手机号码不正确");
                        message.setData(b);
                        handler.sendMessage(message);
                        return;
                    }
                    params.put("phone", phone);
                    params.put("code", code);
                    params.put("word", "");
                    Map<String, Object> result = GetJSON.getInstance().getJSON(ConnectLinkService.getInstance().ConnectHttpsPost(params, URLValue.REGISTER));
                    if (result == null) {
                        message.what = 0;
                        b.putString("msg", "网络连接异常");
                        message.setData(b);
                        handler.sendMessage(message);
                        return;
                    }
                    int res = (int) result.get("res");
                    String msg = (String) result.get("msg");
                    if (res == 0) {
                        message.what = 0;
                        b.putString("msg", msg);
                        message.setData(b);
                        handler.sendMessage(message);
                        return;
                    } else if (res == 1) {
                        String uid = (String) result.get("uid");
                        if (uid == null || "".equals(uid)) {
                            message.what = 0;
                            b.putString("msg", msg);
                            message.setData(b);
                            handler.sendMessage(message);
                            return;
                        } else {
                            StaticValue.USER = new UserEntity(uid);
                            StaticValue.UID = uid;
                            message.what=1;
                            b.putString("key","registered");
                            message.setData(b);
                            handler.sendMessage(message);
                        }
                    }
                }catch (Exception e){
                    doCatchError(e,"login");
                }
            }
        });
        thread.start();
    }

    /**
     * 展示用户协议
     */
    private void showText(){

    }

    private void registeredLogin(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                String phone = registered1_edit1.getText().toString().trim();
                String password1 = registered2_edit1.getText().toString().trim();
                String password2 = registered2_edit2.getText().toString().trim();
                Map<String,String> params = new HashMap<>();
                Message message = new Message();
                Bundle b = new Bundle();
                if(password1==null || password2 == null || "".equals(password1) || "".equals(password2)){
                    message.what = 0;
                    b.putString("msg","请设置新密码");
                    message.setData(b);
                    handler.sendMessage(message);
                    return;
                }
                if (!password1.equals(password2)){
                    message.what = 0;
                    b.putString("msg","两次密码设置不一样，请重新输入");
                    message.setData(b);
                    handler.sendMessage(message);
                    return;
                }
                if(!BaseUtil.CheckMacth(password1,BaseUtil.MatchKEY)){
                    message.what = 0;
                    b.putString("msg","密码过于简单");
                    message.setData(b);
                    handler.sendMessage(message);
                    return;
                }
                params.put("uid",StaticValue.UID);
                params.put("password",BaseUtil.encryptToSHA(password1));
                Map<String,Object> result = GetJSON.getInstance().getJSON(ConnectLinkService.getInstance().ConnectHttpsPost(params,URLValue.RegisterLogin));
                if(result == null){
                    message.what = 0;
                    b.putString("msg","网络连接异常");
                    message.setData(b);
                    handler.sendMessage(message);
                    return;
                }
                int res = (int)result.get("res");
                String msg = (String)result.get("msg");
                if(res == 0 ){
                    message.what = 0;
                    b.putString("msg",msg);
                    message.setData(b);
                    handler.sendMessage(message);
                    return;
                }else if(res == 1 ){
                    message.what=1;
                    b.putString("key","registered2");
                    message.setData(b);
                    handler.sendMessage(message);
                }else {
                    message.what = 0;
                    b.putString("msg","网络连接异常");
                    message.setData(b);
                    handler.sendMessage(message);
                    return;
                }

            }
        });
        thread.start();
    }

    private void invitation_Login(){
        final Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String word = registered3_edit1.getText().toString().trim();
                    Map<String, String> params = new HashMap<>();
                    Bundle b = new Bundle();
                    Message message = new Message();
                    if (word == null || "".equals(word)) {
                        message.what = 0;
                        b.putString("msg", "请输入邀请码");
                        message.setData(b);
                        handler.sendMessage(message);
                        return;
                    }
                    String uid = StaticValue.UID;
                    if(uid==null||"".equals(uid)){
                        uid = StaticValue.USER.getUid();
                    }
                    params.put("uid", uid);
                    params.put("word", word);
                    Map<String, Object> result = GetJSON.getInstance().getJSON(ConnectLinkService.getInstance().ConnectHttpsPost(params, URLValue.INVITATION));
                    if (result == null) {
                        message.what = 0;
                        b.putString("msg", "网络连接异常");
                        message.setData(b);
                        handler.sendMessage(message);
                        return;
                    }
                    int res = (int) result.get("res");
                    String msg = (String) result.get("msg");
                    if (res == 0) {
                        message.what = 0;
                        b.putString("msg", msg);
                        message.setData(b);
                        handler.sendMessage(message);
                        return;
                    } else if (res == 1) {
                        exit();
                    }
                } catch (Exception e) {
                    doCatchError(e, "login");
                }
            }
        });
        thread.start();
    }


    private void sendMessage(final String keytype){
        String phone1 = "";
        String type1 = "0";
        String key1 = "";
        switch (keytype) {
            case "registered":
                phone1 = registered1_edit1.getText().toString().trim();
                type1 = "0";
                key1 = "RegisteredMS";
                break;
            case "loginmessage":
                phone1 = login_message_edit1.getText().toString().trim();
                type1 = "1";
                key1 = "LoginMS";
                break;
            case "forgetpassword":
                phone1 = forget_password_edit1.getText().toString().trim();
                type1 = "2";
                key1 = "ForgetMS";
                break;
        }
        if(!BaseUtil.isTelPhoneNumber(phone1)){
            toastShow("手机号码格式不正确！");
            return;
        }
        final String phone = phone1;
        final String path = URLValue.GET_MESSAGE;
        final String type = type1;
        final String key = key1;
        if (phone == null || "".equals(phone)) {
            toastShow("手机号码不能为空！");
            return;
        }
        Thread th1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Map<String, String> params = new HashMap<>();
                    params.put("phone", phone);
                    params.put("type", type);
                    ConnectLinkService linkService = ConnectLinkService.getInstance();
                    Map<String,Object> result = GetJSON.getInstance().getJSON(linkService.ConnectHttpsPost(params, path));
                    int res = (int) result.get("res");
                    String msg = (String) result.get("msg");
                    Message message = new Message();
                    if(res==0){
                        message.what = 0;
                        Bundle b = new Bundle();
                        b.putString("msg",msg);
                        message.setData(b);
                    }else if (res==1){
                        message.what = 1;
                        Bundle b = new Bundle();
                        b.putString("key",key);
//                        b.putString();
                        message.setData(b);
                    }else {
                        message.what = 0;
                        Bundle b = new Bundle();
                        b.putString("msg","网络请求失败");
                        message.setData(b);
                    }
                    handler.sendMessage(message);

                }catch (Exception e){
                    doCatchError(e,"login");
                }
            }
        });
        th1.start();
    }

    @Override
    public void handlerW1(Bundle bundle) {
        super.handlerW1(bundle);
        String key = bundle.getString("key");
        try {
            switch (key) {
                case "LoginMS":
                    timersUtils = new CountDownTimersUtils(login_message_register, 0, 0);
                    timersUtils.onTick(60000);//开始倒计时

                    break;
                case "ForgetMS":
                    timersUtils = new CountDownTimersUtils(forget_password_register, 0, 0);
                    timersUtils.onTick(60000);//开始倒计时
                    break;
                case "RegisteredMS":
                    timersUtils = new CountDownTimersUtils(registered1_register, 1000, 1000);
                    timersUtils.onTick(60000);//开始倒计时
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                int i = 60;
                                while (i>0) {
                                    i--;
                                    Thread.sleep(1000);
                                    Message message = new Message();
                                    message.what = 1;
                                    Bundle b = new Bundle();
                                    b.putString("key", "sleep");
                                    b.putInt("millis", i*1000);
                                    message.setData(b);
                                }
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                    }).start();
                    break;
                case "registered":
                    chooseLabel("registered2");
                    break;
                case "registered2":
                    chooseLabel("registered3");
                    break;
                case "sleep":
                    int millis = bundle.getInt("millis");
                    timersUtils.onTick(millis);
                    break;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void handlerW2(Bundle bundle) {
        super.handlerW2(bundle);
        setResult(FinalValue.LOGIN_ACTIVTTY);
        super.exit();
    }

    @Override
    public void exit() {
        if(StaticValue.UID == null || "".equals(StaticValue.UID) ){
            super.exit();
        }else {
            Thread th1 = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Map<String, String> params = new HashMap<>();
                        params.put("uid", StaticValue.UID);
                        Map<String, Object> result = GetJSON.getInstance().getJSON(ConnectLinkService.getInstance().ConnectHttpsPost(params, URLValue.INITUSER));
                        if (result == null) {
                            return;
                        }
                        int res = (int) result.get("res");
                        if (res == 1) {
                            Map<String, Object> userData = GetJSON.getInstance().getJSON(result.get("data").toString());
                            StaticValue.USER = new UserEntity(StaticValue.UID);
                            StaticValue.USER.setPhone((String) userData.get("phone"));
                            String name = (String) userData.get("nickname");
                            if(name==null||"".equals(name)){
                                StaticValue.USER.setPickName("用户"+(String) userData.get("phone"));
                            }else {
                                StaticValue.USER.setPickName(name);
                            }
                            StaticValue.USER.setHeadimage((String) userData.get("logo"));
                            StaticValue.USER.setRechargeMoney((String) userData.get("money"));
                            StaticValue.USER.setIntegral((int) userData.get("score"));
                            StaticValue.USER.setIdentification((int) userData.get("type"));
                            String path = getFilesDir().getAbsolutePath();
                            String fileString = BaseUtil.ReadFile(path, FinalValue.LOCAL_FILE_NAME);
                            Boolean flag = false;
                            if (fileString == null) {
                                flag = BaseUtil.newFile(path, FinalValue.LOCAL_FILE_NAME);
                            } else if ("".equals(fileString)) {
                                flag = true;
                            }
                            if (flag) {
                                BaseUtil.writeFile(LoginActivity.this, FinalValue.LOCAL_FILE_NAME, result.get("data").toString());
                            }

                        }
                        handler.sendEmptyMessage(2);
                    }catch (IOException e){
                        e.printStackTrace();
                        handler.sendEmptyMessage(2);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            });
            th1.start();
        }
    }

}
