package com.ttyp.tiantao.ttmb.activity.tabtools;

import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.ttyp.tiantao.R;
import com.ttyp.tiantao.ttmb.activity.MyBaseActivity;
import com.ttyp.tiantao.ttmb.activity.goods.GoodsDetailActivity;
import com.ttyp.tiantao.ttmb.adapter.GoodsListAdapter;
import com.ttyp.tiantao.ttmb.entity.GoodsListItem;
import com.ttyp.tiantao.ttmb.template.URLValue;
import com.ttyp.tiantao.ttmb.util.ConnectLinkService;
import com.ttyp.tiantao.ttmb.util.GetJSON;
import com.ttyp.tiantao.ttmb.util.OnMultClickListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchActivity extends MyBaseActivity {
    //页面组件
    ImageView searchButton;
    EditText searchText;
    FrameLayout returnButton;
    ListView searchAssociation;
    //适配器
    GoodsListAdapter searchItemAdapter;

    //适配器数据list
    private List<GoodsListItem> list = new ArrayList<>();
    private List<GoodsListItem> list1 = new ArrayList<>();

    private int page = 1;
    private int size = 20;
    private String search = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initView();
        initDate();
        setListener();
    }

    private void initView(){
        searchButton = findViewById(R.id.search_search_pic);
        searchText = findViewById(R.id.search_search_text);
        returnButton = findViewById(R.id.back);
        searchAssociation = findViewById(R.id.search_association_list);
    }

    private void initDate(){

    }

    private void setListener(){
        if(list==null){
            list = new ArrayList<>();
        }
        searchItemAdapter = new GoodsListAdapter(SearchActivity.this,R.layout.item_goods_list,list);
        searchAssociation.setAdapter(searchItemAdapter);
        searchAssociation.setOnItemClickListener(itemClickListener);
        searchAssociation.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                switch (scrollState) {
                    // 当不滚动时
                    case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
                        // 判断滚动到底部
                        if (searchAssociation.getLastVisiblePosition() == (searchAssociation.getCount() - 1)) {
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    Map<String,String> params = new HashMap<>();
                                    list1.clear();
                                    if(search==null||"".equals(search)){
                                        handler.sendEmptyMessage(2);
                                    }
                                    list1.addAll(list);
                                    params.put("q",search);
                                    params.put("page",list.size()/size+"");
                                    params.put("size",size+"");
                                    Message message = new Message();
                                    Bundle b = new Bundle();
                                    Map<String,Object> result = GetJSON.getInstance().getJSON(ConnectLinkService.getInstance().ConnectHttpsGET(params, URLValue.GOODS_LIST_DATA));
                                    if (result == null) {
                                        b.putString("msg", "网络连接异常");
                                        message.what = 0;
                                        message.setData(b);
                                        handler.sendMessage(message);
                                    }
                                    int res = (int) result.get("res");
                                    String msg = (String) result.get("msg");
                                    if (res == 0) {
                                        b.putString("msg", msg);
                                        message.what = 0;
                                        message.setData(b);
                                        handler.sendMessage(message);
                                    } else if (res == 1) {
                                        String data = result.get("data").toString();
                                        if (data != null) {
                                            b.putString("key", "scroll");
                                            b.putString("data", data);
                                            message.what = 1;
                                            message.setData(b);
                                            handler.sendMessage(message);
                                        }
                                    } else {
                                        b.putString("msg", "网络连接异常");
                                        message.what = 0;
                                        message.setData(b);
                                        handler.sendMessage(message);
                                    }
                                }
                            }).start();
                        }
                        break;
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });

        searchButton.setOnClickListener(clickListener);
        returnButton.setOnClickListener(clickListener);
        searchText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(final TextView v, int actionId, KeyEvent event) {
                if((event != null && KeyEvent.KEYCODE_ENTER == event.getKeyCode() && KeyEvent.ACTION_DOWN == event.getAction())){
                    setKeyP();
                    searchButton.performClick();
                    return true;
                }
                return false;
            }
        });
    }

    private View.OnClickListener clickListener = new OnMultClickListener() {
        @Override
        public void onMultiClick(View v) {
            switch (v.getId()){
                case R.id.search_search_pic:
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Map<String,String> params = new HashMap<>();
                            search = searchText.getText().toString().trim();
                            list1.clear();
                            if(search==null||"".equals(search)){
                                handler.sendEmptyMessage(2);
                            }
                            params.put("q",search);
                            params.put("page",page+"");
                            params.put("size",size+"");
                            Message message = new Message();
                            Bundle b = new Bundle();
                            Map<String,Object> result = GetJSON.getInstance().getJSON(ConnectLinkService.getInstance().ConnectHttpsGET(params, URLValue.GOODS_LIST_DATA));
                            if (result == null) {
                                b.putString("msg", "网络连接异常");
                                message.what = 0;
                                message.setData(b);
                                handler.sendMessage(message);
                            }
                            int res = (int) result.get("res");
                            String msg = (String) result.get("msg");
                            if (res == 0) {
                                b.putString("msg", msg);
                                message.what = 0;
                                message.setData(b);
                                handler.sendMessage(message);
                            } else if (res == 1) {
                                String data = result.get("data").toString();
                                if (data != null) {
                                    b.putString("key", "search_ratation");
                                    b.putString("data", data);
                                    message.what = 1;
                                    message.setData(b);
                                    handler.sendMessage(message);
                                }
                            } else {
                                b.putString("msg", "网络连接异常");
                                message.what = 0;
                                message.setData(b);
                                handler.sendMessage(message);
                            }
                        }
                    }).start();
                    break;
                case R.id.back:
                    exit();
                    break;
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

    private AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Map<String,String> params = new HashMap<>();
            params.put("goodsid",list.get(position).getGoodsId()+"");
            turnToActivity(GoodsDetailActivity.class,params);
        }
    };

    @Override
    public void handlerW1(Bundle bundle) {
        super.handlerW1(bundle);
        String key = bundle.getString("key");

        try{
            switch (key) {
                case "scroll":
                    String data = bundle.getString("data");
                    if(data!=null) {
                        JSONArray ja = new JSONArray(data);
                        for (int i = 0; i < ja.length(); i++) {
                            JSONObject jo = ja.getJSONObject(i);
                            String title = jo.getString("title");
                            String pic = jo.getString("pic");
                            int numIid = jo.getInt("numIid");
                            String price = jo.getString("price");
                            int couponPrice = jo.getInt("couponPrice");
                            int commission = jo.getInt("commission");
                            boolean isGoodsearn = false;
                            if (commission != 0) {
                                isGoodsearn = true;
                            }
                            int volume = jo.getInt("volume");
//                    String clickUrl = jo.getString("clickUrl");
                            String couponAmount = jo.getString("couponAmount");
                            boolean isGoodsoffset;
                            if (couponAmount == null | "".equals(couponAmount)) {
                                isGoodsoffset = false;
                            } else {
                                isGoodsoffset = true;
                            }
//                    String taoToken = jo.getString("taoToken");
                            String shopTitle = jo.getString("shopTitle");
                            GoodsListItem goodsListItem = new GoodsListItem(numIid);
                            goodsListItem.setGoodsTitle(title).setGoodsImage(pic)
                                    .setPrice_old(price).setPrice_now(couponPrice + "")
                                    .setGoodsearn(commission + "").setIsGoodsearn(isGoodsearn)
                                    .setGoodsoffset(couponAmount).setIsGoodsoffset(isGoodsoffset)
                                    .setSales(volume + "").setShopname(shopTitle).setIsShopaddress(false);
                            list1.add(goodsListItem);
                        }
                        list.clear();
                        list.addAll(list1);
                        searchItemAdapter.notifyDataSetChanged();
                    }
                    break;
                case "search_ratation":
                    String da = bundle.getString("data");
                    if(da!=null) {
                        JSONArray ja = new JSONArray(da);
                        for (int i = 0; i < ja.length(); i++) {
                            JSONObject jo = ja.getJSONObject(i);
                            String title = jo.getString("title");
                            String pic = jo.getString("pic");
                            int numIid = jo.getInt("numIid");
                            String price = jo.getString("price");
                            int couponPrice = jo.getInt("couponPrice");
                            int commission = jo.getInt("commission");
                            boolean isGoodsearn = false;
                            if (commission != 0) {
                                isGoodsearn = true;
                            }
                            int volume = jo.getInt("volume");
//                    String clickUrl = jo.getString("clickUrl");
                            String couponAmount = jo.getString("couponAmount");
                            boolean isGoodsoffset;
                            if (couponAmount == null | "".equals(couponAmount)) {
                                isGoodsoffset = false;
                            } else {
                                isGoodsoffset = true;
                            }
//                    String taoToken = jo.getString("taoToken");
                            String shopTitle = jo.getString("shopTitle");
                            GoodsListItem goodsListItem = new GoodsListItem(numIid);
                            goodsListItem.setGoodsTitle(title).setGoodsImage(pic)
                                    .setPrice_old(price).setPrice_now(couponPrice + "")
                                    .setGoodsearn(commission + "").setIsGoodsearn(isGoodsearn)
                                    .setGoodsoffset(couponAmount).setIsGoodsoffset(isGoodsoffset)
                                    .setSales(volume + "").setShopname(shopTitle).setIsShopaddress(false);
                            list1.add(goodsListItem);
                        }
                        list.clear();
                        list.addAll(list1);
                        searchItemAdapter.notifyDataSetChanged();
                    }
                    break;
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void handlerW2(Bundle bundle) {
        super.handlerW2(bundle);
        try{
            list.clear();
            list.addAll(list1);
            searchItemAdapter.notifyDataSetChanged();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
