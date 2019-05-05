package com.ttyp.tiantao.ttmb.activity.rechargecard;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.ttyp.tiantao.R;
import com.ttyp.tiantao.assembly.mydatapicker.CustomDatePickerDialogFragment;
import com.ttyp.tiantao.ttmb.activity.MyBaseActivity;
import com.ttyp.tiantao.ttmb.activity.login.LoginActivity;
import com.ttyp.tiantao.ttmb.adapter.DixkBListAdapter;
import com.ttyp.tiantao.ttmb.entity.DIXKBList;
import com.ttyp.tiantao.ttmb.template.FinalValue;
import com.ttyp.tiantao.ttmb.template.KEYVALUE;
import com.ttyp.tiantao.ttmb.template.StaticValue;
import com.ttyp.tiantao.ttmb.template.URLValue;
import com.ttyp.tiantao.ttmb.util.ConnectLinkService;
import com.ttyp.tiantao.ttmb.util.GetJSON;
import com.ttyp.tiantao.ttmb.util.OnMultClickListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RechargeListActivity extends MyBaseActivity implements CustomDatePickerDialogFragment.OnSelectedDateListener{
    ImageView backImage;
    TextView title_text;
    TextView textView1;
//    LinearLayout layout;
    ListView listView;
    ImageView dixk_b_rl;
    TextView dixk_b_rl_text;

    List<DIXKBList> list = new ArrayList<>();
    List<DIXKBList> list1 = new ArrayList<>();
    Boolean isOver = false;

    DixkBListAdapter adapter;
    private final int size = 1;
    private int page = 20;
    private Date start = null;
    private Date end = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_dixk_b);
        initView();
        setListener();
        initData();
    }

    private void initView(){
        backImage = findViewById(R.id.but_a_backimage);
        title_text = findViewById(R.id.title_text);
        textView1 = findViewById(R.id.dixk_b_text1);
        listView = findViewById(R.id.dixk_b_list);
        dixk_b_rl = findViewById(R.id.dixk_b_rl);
        dixk_b_rl_text = findViewById(R.id.dixk_b_rl_text);
    }

    private void initData(){
        if(StaticValue.USER==null||StaticValue.USER.getUid()==null){
            turnForResultToActivity(LoginActivity.class,new HashMap<String, String>(), FinalValue.MAIN_ACTIVTTY_RECHARGE_B);
        }
        title_text.setText("充值记录");
        textView1.setText("全部");
        getData("initData",start,end);
    }

    private void getData(final String key, final Date startDate, final Date endDate){
        Thread th1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
//                    Log.e("uid",StaticValue.USER.getUid());
                    Map<String,String> value = new HashMap<>();
                    value.put("uid", StaticValue.USER.getUid());
                    value.put("type", "money");
                    if(list.size()>0) {
                        value.put("page", list.size() / size+"");
                    }else {
                        value.put("page", page + "");
                    }
                    value.put("size", size + "");
                    if(startDate!=null){
                        value.put("start", startDate.getTime()+"");
                    }
                    if(endDate!=null){
                        value.put("end", endDate.getTime()+"");
                    }
                    value.put("action", KEYVALUE.DEDUCTION.getIdentificationKey()+"");
                    Message message = new Message();
                    Bundle bundle = new Bundle();
                    Map<String,Object> result = GetJSON.getInstance().getJSON(ConnectLinkService.getInstance().ConnectHttpsPost(value, URLValue.RECHARGECARD_RECHARGE_LIST));
                    if(result==null){
                        message.what = 0;
                        bundle.putString("msg","网络请求异常");
                        message.setData(bundle);
                        handler.sendMessage(message);
                        return;
                    }
                    int res = (int)result.get("res");
                    String msg = (String) result.get("msg");
                    if (res==1) {
                        JSONArray ja = new JSONArray(result.get("data").toString());
                        for(int i = 0;i<ja.length();i++){
                            JSONObject jo = ja.getJSONObject(i);
                            String num = jo.getString("num");
                            String remark = jo.getString("remark");
                            Long created = jo.getLong("created");
                            Date createTime = new Date(created);
                            DIXKBList dixkbList = new DIXKBList(remark,createTime,num);
                            list1.add(dixkbList);
                        }
                        message.what = 1;
                        bundle.putString("key",key);
                        message.setData(bundle);
                        handler.sendMessage(message);
                    } else {
                        message.what = 0;
                        bundle.putString("msg",msg);
                        message.setData(bundle);
                        handler.sendMessage(message);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        th1.start();
    }

    private void setListener(){
        try{
            backImage.setClickable(true);
            backImage.setOnClickListener(listener);
            dixk_b_rl.setClickable(true);
            dixk_b_rl_text.setClickable(true);
            dixk_b_rl.setOnClickListener(listener);
            dixk_b_rl_text.setOnClickListener(listener);

            if(list==null) list = new ArrayList<>();
            adapter = new DixkBListAdapter(RechargeListActivity.this,R.layout.item_dixk_b,list);
            listView.setAdapter(adapter);
            listView.setOnScrollListener(new AbsListView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(AbsListView view, int scrollState) {
                    //当不滚动时
                    if(scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE){
                        //判断是否滚动到底部
                        if(view.getLastVisiblePosition() == view.getCount() - 1){
                            //加载更多
                            if(!isOver){
                                Thread th1 = new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        list1.clear();
                                        list1.addAll(list);
                                        getData("scroll",start,end);
                                    }
                                });
                                th1.start();

                            }
                        }
                    }
                }

                @Override
                public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private View.OnClickListener listener = new OnMultClickListener() {
        Calendar c = Calendar.getInstance();
        @Override
        public void onMultiClick(View v) {
            switch (v.getId()){
                case R.id.but_a_backimage:
                    exit();
                    break;
                case R.id.dixk_b_rl:
                case R.id.dixk_b_rl_text:
                    showDatePickDialog();
                    break;
            }
        }
    };

    long day = 24 * 60 * 60 * 1000;

    //日历选择
    private void showDatePickDialog() {
        CustomDatePickerDialogFragment fragment = new CustomDatePickerDialogFragment();
        fragment.setOnSelectedDateListener(this);
        Bundle bundle = new Bundle();
        Calendar currentDate = Calendar.getInstance();
        currentDate.setTimeInMillis(System.currentTimeMillis());
        currentDate.set(Calendar.HOUR_OF_DAY,0);
        currentDate.set(Calendar.MINUTE,0);
        currentDate.set(Calendar.SECOND,0);
        currentDate.set(Calendar.MILLISECOND,0);
        bundle.putSerializable(CustomDatePickerDialogFragment.CURRENT_DATE,currentDate);


        long start = currentDate.getTimeInMillis() - day * 365;
        long end = currentDate.getTimeInMillis() - day;
        Calendar startDate = Calendar.getInstance();
        startDate.setTimeInMillis(start);
        Calendar endDate = Calendar.getInstance();
        endDate.setTimeInMillis(end);
        bundle.putSerializable(CustomDatePickerDialogFragment.START_DATE,startDate);
        bundle.putSerializable(CustomDatePickerDialogFragment.END_DATE,currentDate);

        fragment.setArguments(bundle);
        fragment.show(getSupportFragmentManager(),CustomDatePickerDialogFragment.class.getSimpleName());
    }

    @Override
    public void onSelectedDate(int year, int monthOfYear, int dayOfMonth) {
//        toastShow(year+"年"+(monthOfYear+1)+"月"+dayOfMonth+"日");
        String startTime = year+"-"+monthOfYear+"-1 00:00:00";
        String endTime;
        if(monthOfYear < 12) {
            endTime = year + "-" + (monthOfYear + 1)+"-1 00:00:00";
        }else{
            endTime = (year + 1) +"-1-1 00:00:00";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date startDate = null;
        Date endDate = null;
        try {
            startDate = sdf.parse(startTime);
            endDate = sdf.parse(endTime);
        }catch (Exception e){
            e.printStackTrace();
        }
        if(startDate!=null){
            start = startDate;
        }
        if(endDate!=null){
            end = endDate;
        }
        list1.clear();
        getData("new",start,end);
    }

    @Override
    public void handlerW1(Bundle bundle) {
        super.handlerW1(bundle);
        String key = bundle.getString("key");

        try {
            list.clear();
            list.addAll(list1);
            adapter.notifyDataSetChanged();
//            switch (key){
//                case "scroll":
//
//                    break;
//
//            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==FinalValue.MAIN_ACTIVTTY_RECHARGE_B){
            switch (resultCode){
                case FinalValue.LOGIN_ACTIVTTY:
                    if(StaticValue.USER==null){
                        exit();
                    }else {
                        initData();
                    }
                    break;
            }
        }
    }
}
