package com.ttyp.tiantao.ttmb.activity.rechargecard;

import android.os.Bundle;
import android.os.Message;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.ttyp.tiantao.R;
import com.ttyp.tiantao.ttmb.activity.MyBaseActivity;
import com.ttyp.tiantao.ttmb.adapter.DixkBListAdapter;
import com.ttyp.tiantao.ttmb.entity.DIXKBList;
import com.ttyp.tiantao.ttmb.template.URLValue;
import com.ttyp.tiantao.ttmb.util.ConnectLinkService;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RechargeListActivity extends MyBaseActivity {
    ImageView backImage;
    TextView textView1;
//    LinearLayout layout;
    ListView listView;

    List<DIXKBList> list = new ArrayList<>();
    List<DIXKBList> list1 = new ArrayList<>();
    Boolean isOver = false;

    DixkBListAdapter adapter;

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
        textView1 = findViewById(R.id.dixk_b_text1);
//        layout = findViewById(R.id.dixk_b_layout);
        listView = findViewById(R.id.dixk_b_list);
    }

    private void initData(){
        try{
            Thread th1 = new Thread(new Runnable() {
                @Override
                public void run() {
                    Map<String,String> value = new HashMap<>();
//                  value.put("")
                    try {
                        ConnectLinkService linkService = ConnectLinkService.getInstance();
                        String result = linkService.ConnectHttpsPost(value, URLValue.REGISTER);
                        JSONObject jsonObject = new JSONObject(result);
                        String res = jsonObject.getString("res");
                        String msg = jsonObject.getString("msg");
                        Message message = new Message();
                        if (res.equals("1")) {
                            message.what = 1;
                            Bundle b = new Bundle();
                            message.setData(b);
                        } else {
                            message.what = 0;
                            Bundle b = new Bundle();
                            b.putString("msg", msg);
                            message.setData(b);
                        }
                        handler.sendMessage(message);
                    }catch (Exception e){

                    }


                }
            });
            th1.start();
        }catch (Exception e){

        }
    }

    private void setListener(){
        try{
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
                                        list1.addAll(getMoreList());
                                        Message message = new Message();
                                        message.what = 1;
                                        Bundle b = new Bundle();
                                        message.setData(b);
                                        handler.sendMessage(message);
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

        }
    }

    private List<DIXKBList> getMoreList(){
        List<DIXKBList> ret = new ArrayList<>();

        return ret;
    }

    @Override
    public void handlerW1(Bundle bundle) {
        super.handlerW1(bundle);
        try {
            if (list1 != null && list1.size() > 0) {
                list.clear();
                list.addAll(list1);
                list1.clear();
                adapter.notifyDataSetChanged();
            }
        }catch (Exception e){

        }
    }
}
