package com.ttyp.tiantao.ttmb.activity.rechargecard;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.ttyp.tiantao.R;
import com.ttyp.tiantao.ttmb.activity.MyBaseActivity;
import com.ttyp.tiantao.ttmb.template.StaticValue;
import com.ttyp.tiantao.ttmb.template.URLValue;
import com.ttyp.tiantao.ttmb.util.ConnectLinkService;
import com.ttyp.tiantao.ttmb.util.GetJSON;
import com.ttyp.tiantao.ttmb.util.OnMultClickListener;

import java.util.HashMap;
import java.util.Map;

public class DiXKRechargeActivity extends MyBaseActivity {

    ImageView backImage;
    TextView title_text;

    EditText editText1;
    EditText editText2;

    FrameLayout layout;

    TextView textView1;
    TextView textView2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_dixk_a);

        initView();
        setListener();
    }

    private void initView() {
        backImage = findViewById(R.id.but_a_backimage);
        title_text = findViewById(R.id.title_text);
        editText1 = findViewById(R.id.dixk_a_edit1);
        editText2 = findViewById(R.id.dixk_a_edit2);
        layout = findViewById(R.id.dixk_a_layout);
        textView1 = findViewById(R.id.dixk_a_text1);
        textView2 = findViewById(R.id.dixk_a_text2);
    }

    private void setListener() {
        try{
            title_text.setText("抵现卡充值");
            backImage.setOnClickListener(listener);
            layout.setOnClickListener(listener);
            textView1.setOnClickListener(listener);
            textView2.setOnClickListener(listener);
        }catch (Exception e){

        }
    }

    private OnMultClickListener listener = new OnMultClickListener() {
        @Override
        public void onMultiClick(View v) {
            switch (v.getId()){
                case R.id.but_a_backimage:
                    exit();
                    break;
                case R.id.dixk_a_layout:
                    //充值
                    rechargeCard();
                    break;
                case R.id.dixk_a_text1:
                    //跳转充值记录
                    turnToActivity(RechargeListActivity.class,new HashMap<String, String>());
                    break;
                case R.id.dixk_a_text2:
                    //跳转消费记录
                    turnToActivity(ConsumeListActivity.class,new HashMap<String, String>());
                    break;
            }
        }
    };

    private void rechargeCard(){
        final String cardNumber = editText1.getText().toString();
        final String cardPassword = editText2.getText().toString();
        if (cardNumber == null || cardNumber.trim().equals("")){
            toastShow("请输入充值卡号");
            return;
        }
        if (cardPassword == null || cardPassword.trim().equals("")){
            toastShow("请输入充值密码");
            return;
        }
        Thread th1 = new Thread(new Runnable() {
            @Override
            public void run() {
                Map<String,String> value = new HashMap<>();
                value.put("uid",StaticValue.UID);
                value.put("card",cardNumber);
                value.put("password",cardPassword);
                try{
                    ConnectLinkService linkService = ConnectLinkService.getInstance();
                    Map<String,Object> result = GetJSON.getInstance().getJSON(linkService.ConnectHttpsPost(value,URLValue.RECHARGECARD_RECHARGE));
                    int res = (int)result.get("res");
                    String msg = (String)result.get("msg");
                    Message message = new Message();
                    if(res==1){
                        message.what = 1;
                        Bundle b = new Bundle();
                        b.putString("num","1");
                        message.setData(b);
                    }else{
                        message.what = 0;
                        Bundle b = new Bundle();
                        b.putString("msg",msg);
                        message.setData(b);
                    }
                    handler.sendMessage(message);
                }catch (Exception e){
                    doCatchError(e,"recharge");
                }
            }
        });
        th1.start();
    }

    @Override
    public void handlerW1(Bundle bundle) {
        super.handlerW1(bundle);
        toastShow("充值成功");
        exit();
    }
}
