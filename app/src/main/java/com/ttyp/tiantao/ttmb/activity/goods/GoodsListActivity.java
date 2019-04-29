package com.ttyp.tiantao.ttmb.activity.goods;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.ttyp.tiantao.R;
import com.ttyp.tiantao.ttmb.activity.MyBaseActivity;
import com.ttyp.tiantao.ttmb.adapter.GoodsListAdapter;
import com.ttyp.tiantao.ttmb.entity.GoodsListItem;
import com.ttyp.tiantao.ttmb.template.URLValue;
import com.ttyp.tiantao.ttmb.util.ConnectLinkService;
import com.ttyp.tiantao.ttmb.util.GetJSON;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GoodsListActivity extends MyBaseActivity {
    //适配器
    GoodsListAdapter adapter;
    //页面组件
    ImageView reutrnParent;
    TextView titleText;
    ListView goodsList;
    //页面展示数据
    List<GoodsListItem> list ;
    List<GoodsListItem> list1 ;
    String title;

    //页面list控制参数
    private int limit = 1;
    private final int size = 20;
    private String type = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goodslist);
        initView();
        initData();
//        setListener();
    }

    //初始化绑定页面组件
    private void initView(){
        reutrnParent = findViewById(R.id.but_a_backimage);
        titleText = findViewById(R.id.title_text);
        goodsList = findViewById(R.id.goods_list_listview);
    }

    //初始化绑定页面数据
    private void initData(){
        Intent intent = getIntent();
        if(intent==null){
            exit();
        }
        Bundle b = intent.getExtras();
        title =  b.getString("title");
        final String keywords = b.getString("keywords");
        type = b.getString("type");
        Thread th1 = null;
        switch (type){
            case "message":
                th1 = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Map<String,String> params = new HashMap<>();
                        params.put("q",keywords);
                        params.put("page",limit+"");
                        params.put("size",size+"");
                        Message message = new Message();
                        Bundle b = new Bundle();
                        Map<String,Object> result = GetJSON.getInstance().getJSON(ConnectLinkService.getInstance().ConnectHttpsPost(params, URLValue.GOODS_LIST_DATA));
                        if(result==null){
                            message.what = 0;
                            b.getString("msg","网络请求异常");
                            message.setData(b);
                            handler.sendMessage(message);
                            return;
                        }
                        int res = (int) result.get("res");
                        String msg = (String) result.get("msg");
                        if(res==0){
                            message.what = 0;
                            b.getString("msg",msg);
                            message.setData(b);
                            handler.sendMessage(message);
                        }else if(res==1){
                            message.what = 1;
                            b.getString("key","recommendMore");
                            b.getString("data",(String) result.get("data"));
                            message.setData(b);
                            handler.sendMessage(message);
                        }else {
                            message.what = 0;
                            b.getString("msg","网络请求异常");
                            message.setData(b);
                            handler.sendMessage(message);
                        }

                    }
                });
                break;
            case "recommendMore":
                th1 = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Map<String,String> params = new HashMap<>();
                        params.put("favorites_id",keywords);
                        params.put("size",size+"");
                        params.put("page",limit+"");
                        params.put("order","recommend");
                        params.put("sort","asc");
                        params.put("coupon","0");
                        Message message = new Message();
                        Bundle b = new Bundle();
                        Map<String,Object> result = GetJSON.getInstance().getJSON(ConnectLinkService.getInstance().ConnectHttpsPost(params, URLValue.RECOMMEND_MORE));
                        if(result==null){
                            message.what = 0;
                            b.getString("msg","网络请求异常");
                            message.setData(b);
                            handler.sendMessage(message);
                            return;
                        }
                        int res = (int) result.get("res");
                        String msg = (String) result.get("msg");
                        if(res==0){
                            message.what = 0;
                            b.getString("msg",msg);
                            message.setData(b);
                            handler.sendMessage(message);
                        }else if(res==1){
                            message.what = 1;
                            b.getString("key","recommendMore");
                            b.getString("data",result.get("data").toString());
                            message.setData(b);
                            handler.sendMessage(message);
                        }else {
                            message.what = 0;
                            b.getString("msg","网络请求异常");
                            message.setData(b);
                            handler.sendMessage(message);
                        }

                    }
                });
                break;
            case "menuButton":
                th1 = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Map<String,String> params = new HashMap<>();
                        params.put("q",keywords);
                        params.put("page",limit+"");
                        params.put("size",size+"");
                        Message message = new Message();
                        Bundle b = new Bundle();
                        Map<String,Object> result = GetJSON.getInstance().getJSON(ConnectLinkService.getInstance().ConnectHttpsPost(params, URLValue.GOODS_LIST_DATA));
                        if(result==null){
                            message.what = 0;
                            b.getString("msg","网络请求异常");
                            message.setData(b);
                            handler.sendMessage(message);
                            return;
                        }
                        int res = (int) result.get("res");
                        String msg = (String) result.get("msg");
                        if(res==0){
                            message.what = 0;
                            b.getString("msg",msg);
                            message.setData(b);
                            handler.sendMessage(message);
                        }else if(res==1){
                            message.what = 1;
                            b.getString("key","recommendMore");
                            b.getString("data",(String) result.get("data"));
                            message.setData(b);
                            handler.sendMessage(message);
                        }else {
                            message.what = 0;
                            b.getString("msg","网络请求异常");
                            message.setData(b);
                            handler.sendMessage(message);
                        }

                    }
                });
                break;
            case "searchGoods":
                th1 = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Map<String,String> params = new HashMap<>();
                        params.put("q",keywords);
                        params.put("page",limit+"");
                        params.put("size",size+"");
                        Message message = new Message();
                        Bundle b = new Bundle();
                        Map<String,Object> result = GetJSON.getInstance().getJSON(ConnectLinkService.getInstance().ConnectHttpsPost(params, URLValue.GOODS_LIST_DATA));
                        if(result==null){
                            message.what = 0;
                            b.getString("msg","网络请求异常");
                            message.setData(b);
                            handler.sendMessage(message);
                            return;
                        }
                        int res = (int) result.get("res");
                        String msg = (String) result.get("msg");
                        if(res==0){
                            message.what = 0;
                            b.getString("msg",msg);
                            message.setData(b);
                            handler.sendMessage(message);
                        }else if(res==1){
                            message.what = 1;
                            b.getString("key","searchGoods");
                            b.getString("data",(String) result.get("data"));
                            message.setData(b);
                            handler.sendMessage(message);
                        }else {
                            message.what = 0;
                            b.getString("msg","网络请求异常");
                            message.setData(b);
                            handler.sendMessage(message);
                        }

                    }
                });
                break;
            case "my_order":
                th1 = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Map<String,String> params = new HashMap<>();
                        params.put("q",keywords);
                        params.put("page",limit+"");
                        params.put("size",size+"");
                        Message message = new Message();
                        Bundle b = new Bundle();
                        Map<String,Object> result = GetJSON.getInstance().getJSON(ConnectLinkService.getInstance().ConnectHttpsPost(params, URLValue.GOODS_LIST_DATA));
                        if(result==null){
                            message.what = 0;
                            b.getString("msg","网络请求异常");
                            message.setData(b);
                            handler.sendMessage(message);
                            return;
                        }
                        int res = (int) result.get("res");
                        String msg = (String) result.get("msg");
                        if(res==0){
                            message.what = 0;
                            b.getString("msg",msg);
                            message.setData(b);
                            handler.sendMessage(message);
                        }else if(res==1){
                            message.what = 1;
                            b.getString("key","my_order");
                            b.getString("data",(String) result.get("data"));
                            message.setData(b);
                            handler.sendMessage(message);
                        }else {
                            message.what = 0;
                            b.getString("msg","网络请求异常");
                            message.setData(b);
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

    //初始化绑定页面事件
    private void setListener(){
        reutrnParent.setOnClickListener(clickListener);
        titleText.setText(title);
        adapter = new GoodsListAdapter(this,R.layout.item_goods_list,list);
        goodsList.setAdapter(adapter);
        goodsList.setOnItemClickListener(itemClickListener);
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.but_a_backimage:
                    GoodsListActivity.this.finish();
                    break;
                default:
            }
        }
    };

    private AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            toastShow("行点击事件响应：{第"+position+"行，id："+list.get(position).getGoodsId()+"}");
        }
    };

    @Override
    public void handlerW1(Bundle bundle) {
        super.handlerW1(bundle);
        String key = bundle.getString("key");
        switch (key){
            case "getData":
                JSONArray ja = new JSONArray();
                try {
                    String data = bundle.getString("data");
                    ja = new JSONArray(data);
                    for(int i = 0;i<ja.length();i++){
                        JSONObject jo = ja.getJSONObject(i);
                        int category = jo.getInt("category");//一级类目id
                        int coupon_total_count = jo.getInt("coupon_total_count");//优惠券总量
                        int coupon_remain_count = jo.getInt("coupon_remain_count");//优惠券剩余数量
                        int num_iid = jo.getInt("num_iid");//商品id
                        int seller_id = jo.getInt("seller_id");//卖家id
                        int user_type = jo.getInt("user_type");//卖家类型
                        int volume = jo.getInt("volume");//30天销量
                        String commission_rate = jo.getString("commission_rate");//佣金比例
                        String coupon_click_url = jo.getString("coupon_click_url");//
                        String coupon_end_time = jo.getString("coupon_end_time");
                        String coupon_info = jo.getString("coupon_info");
                        String coupon_start_time = jo.getString("coupon_start_time");
                        String item_description = jo.getString("item_description");
                        String item_url = jo.getString("item_url");
                        String nick = jo.getString("nick");
                        String pict_url = jo.getString("pict_url");
                        String shop_title = jo.getString("shop_title");
                        String title = jo.getString("title");
                        String zk_final_price = jo.getString("zk_final_price");
                        JSONObject jo1 = jo.getJSONObject("small_images");
                        JSONArray ja1 = jo1.getJSONArray("string");
                        for (int k = 0;k<ja1.length();k++){
                            String strs = ja1.getString(k);
                        }
                        GoodsListItem goodsListItem = new GoodsListItem(num_iid);
                    }
                }catch (Exception e){

                }
                break;
        }
    }
}
