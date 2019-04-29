package com.ttyp.tiantao.ttmb.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import java.lang.ref.WeakReference;
import java.util.Map;

public class MyBaseActivity extends AppCompatActivity  {

    public static MyBaseActivity instance;
    private Toast toast;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;
    }

    public void toastShow(String str){
        toast = Toast.makeText(MyBaseActivity.this,str,Toast.LENGTH_SHORT);
        toast.show();
    }
    public void toastLongShow(String str){
        toast = Toast.makeText(MyBaseActivity.this,str,Toast.LENGTH_LONG);
        toast.show();
    }
    public void turnToActivity(Class aClass, Map<String,String> params){
        Intent intent = new Intent(this,aClass);
        if(params!=null) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                intent.putExtra(entry.getKey(), entry.getValue());
            }
        }
        startActivity(intent);
    }
    public void turnForResultToActivity(Class aClass, Map<String,String> params,int resultCode){
        Intent intent = new Intent(this,aClass);
        if(params!=null) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                intent.putExtra(entry.getKey(), entry.getValue());
            }
        }
        startActivityForResult(intent,resultCode);
    }

    public Handler handler = new PagerHandler(this);

    public void handlerW1(Bundle bundle){}

    public void handlerW2(Bundle bundle){}

    public void doCatchError(Exception e,String tag){
        Log.e(tag,e.toString());
        e.printStackTrace();
    }

    public void exit(){
        this.finish();
    }


    private static final class PagerHandler extends Handler {
        WeakReference<MyBaseActivity> mMainActivityWeakReference;

        public PagerHandler(MyBaseActivity myBaseActivity) {
            mMainActivityWeakReference = new WeakReference<>(myBaseActivity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            MyBaseActivity mainActivity = mMainActivityWeakReference.get();
            if (msg.what == 0) {
                mainActivity.toastShow(msg.getData().getString("msg"));
            }else if (msg.what==1){
                mainActivity.handlerW1(msg.getData());
            }else if(msg.what == 2){
                mainActivity.handlerW2(msg.getData());
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
