package com.ttyp.tiantao.ttmb.extendsparent;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.lang.ref.WeakReference;
import java.util.Map;

public abstract class MyFragment extends Fragment {
    public FragmentResultData fragmentResultData;
    private Toast toast;
    public MyFragment() {
        super();
    }
    protected boolean isUserVisible;
    protected boolean isPrepared;//标志已经初始化完成



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentResultData = new FragmentResultData() {
            @Override
            public void turnToActivity(Class aClass, Map<String, String> params) {
                Intent intent = new Intent(getActivity(),aClass);
                if(params!=null) {
                    for (Map.Entry<String, String> entry : params.entrySet()) {
                        intent.putExtra(entry.getKey(), entry.getValue());
                    }
                }
                startActivity(intent);
            }

            @Override
            public void turnForResultToActivity(Class aClass, Map<String, String> params, int resultCode) {
                Intent intent = new Intent(getActivity(),aClass);
                if(params!=null) {
                    for (Map.Entry<String, String> entry : params.entrySet()) {
                        intent.putExtra(entry.getKey(), entry.getValue());
                    }
            }
                startActivityForResult(intent,resultCode);
            }
        };
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public interface FragmentResultData {
        void turnToActivity(Class activityClass, Map<String,String> params);
        void turnForResultToActivity(Class activityClass, Map<String,String> params,int resultCode);
    }

    public Handler handler = new PagerHandler(MyFragment.this);

    private static final class PagerHandler extends Handler {
        WeakReference<MyFragment> mMainActivityWeakReference;
        public PagerHandler(MyFragment myBaseActivity) {
            mMainActivityWeakReference = new WeakReference<>(myBaseActivity);
        }
        @Override
        public void handleMessage(Message msg) {
            try {
                super.handleMessage(msg);
                MyFragment mainActivity = mMainActivityWeakReference.get();
                Log.i("MyFragment",msg.what+"");
                if (msg.what == 0) {
                    mainActivity.toastShow(msg.getData().getString("msg"));
                } else if (msg.what == 1) {
                    mainActivity.handlerW1(msg.getData());
                }
            }catch (Exception e){
                e.printStackTrace();
                Log.e("MyFragment",e.toString());
            }
        }
    }

    public boolean canCallBack(){return false;};

    public void callBack(){};

    public void toastShow(String str){
        toast = Toast.makeText(getActivity(),str,Toast.LENGTH_SHORT);
        toast.show();
    }

    public void toastLongShow(String str){
        toast = Toast.makeText(getActivity(),str,Toast.LENGTH_LONG);
        toast.show();
    }

    public void handlerW1(Bundle bundle){}

}
