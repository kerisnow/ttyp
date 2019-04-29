package com.ttyp.tiantao.ttmb.activity.goods;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jaychan.view.MultipleTextView;
import com.ttyp.tiantao.R;
import com.ttyp.tiantao.ttmb.activity.MyBaseActivity;
import com.ttyp.tiantao.ttmb.adapter.ViewPageShowAdapter;
import com.ttyp.tiantao.ttmb.template.URLValue;
import com.ttyp.tiantao.ttmb.util.ConnectLinkService;
import com.ttyp.tiantao.ttmb.util.GetJSON;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GoodsDetailActivity extends MyBaseActivity {

    //适配器
    //轮播图适配器
    ViewPageShowAdapter viewPageShowAdapter ;

    //轮播图组件
    LinearLayout vp_layout;
    ViewPager vp_main;
    LinearLayout ll_main;
    TextView shareEarn;

    //商品标题
    TextView goodsListImageTextView;
    MultipleTextView priceDiscount;
    MultipleTextView priceTaob;
    TextView salesVolume;

    //抵用券领取
    ImageView offsetControl;
    TextView offsetPrice;
    TextView offsetDate;

    //店铺信息
    ImageView shopLogo;
    TextView shopName;
    LinearLayout addressControl;
    TextView shopAddress;
    MultipleTextView score1;
    MultipleTextView score2;
    MultipleTextView score3;

    //商品详情
    LinearLayout goodsDetail;
    View[] goodsDetails;

    //猜你喜欢
    GridView moreGoodsList;

    //数据常量
    private int previousPosition = 0;

    //数据list集合
    List<ImageView> vp_main_list;
    List<String> vp_main_url_list;
    List<String> vp_main_list2_1 = new ArrayList<>();
    List<String> vp_main_list2 = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_detail);
        initView();
        initData();
    }

    //初始化绑定页面组件
    private void initView(){
        //轮播图组件
        vp_layout = findViewById(R.id.home_rotation_control);
        vp_main = findViewById(R.id.vp_main);
        ll_main = findViewById(R.id.ll_main);
        shareEarn = findViewById(R.id.share_earn);

        //商品标题
        goodsListImageTextView = findViewById(R.id.goods_detail_image_text);

        //商品价格
        priceDiscount = findViewById(R.id.price_discount);
        priceDiscount = findViewById(R.id.price_taob);
        salesVolume = findViewById(R.id.sales_volume);

        //抵用券领取
        offsetControl = findViewById(R.id.offset_receive_control);
        offsetPrice = findViewById(R.id.offset_price);
        offsetDate = findViewById(R.id.offset_date);

        //店铺信息
        shopLogo = findViewById(R.id.goods_detail_shop_logo);
        shopName = findViewById(R.id.goods_detail_shopname);
        addressControl = findViewById(R.id.goods_detail_shop_address_control);
        shopAddress = findViewById(R.id.goods_detail_shop_address);

        //商品详情
        goodsDetail = findViewById(R.id.goods_detail_image_detail);

        //猜你喜欢
        moreGoodsList = findViewById(R.id.more_goods_list);

    }

    //初始化绑定页面数据
    private void initData(){
        final String goodsid = getIntent().getExtras().getString("goodsid");
        Thread th1 = new Thread(new Runnable() {
            @Override
            public void run() {
                Map<String,String> params = new HashMap<>();
                params.put("numid",goodsid);
                Message message = new Message();
                Bundle b = new Bundle();
                Map<String,Object> result = GetJSON.getInstance().getJSON(ConnectLinkService.getInstance().ConnectHttpsPost(params, URLValue.GOODS_DETAIL_DATA));
                if(result == null){
                    message.what = 0;
                    b.putString("msg","网络请求异常");
                    message.setData(b);
                    handler.sendMessage(message);
                    return;
                }
                int res = (int)result.get("res");
                String msg = (String) result.get("msg");
                if(res==0){
                    message.what = 0;
                    b.putString("msg",msg);
                    message.setData(b);
                    handler.sendMessage(message);
                }else if (res==1){
                    message.what = 1;
                    b.putString("key","getData");
                    b.putString("data",result.get("data").toString());
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
        th1.start();
    }

    //初始化绑定页面事件
    private void setListener() {
        //轮播图事件绑定
        if(vp_main_list2_1 == null){
            vp_main_list2_1 = new ArrayList<>();
        }
        if(vp_main_url_list ==null){
            vp_main_url_list = new ArrayList<>();
        }
        viewPageShowAdapter = new ViewPageShowAdapter(GoodsDetailActivity.this,vp_main_list2_1,vp_main_url_list,null);
        vp_main.setAdapter(viewPageShowAdapter);
        int item = Integer.MAX_VALUE / 2 - (Integer.MAX_VALUE / 2 % vp_main_list.size());
        vp_main.setCurrentItem(item);
        ll_main.getChildAt(previousPosition).setEnabled(true);
        vp_main.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset,int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(final int position) {
                int newPostion = position % vp_main_list.size();
                //取出postion的位置小圆点设置为true
                ll_main.getChildAt(newPostion).setEnabled(true);
                //把一个小圆点设置为false
                ll_main.getChildAt(previousPosition).setEnabled(false);
//                    title.setText(imageDescArrs[newPostion]);
                previousPosition = newPostion;

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        offsetControl.setOnClickListener(clickListener);
    }

    private void ofsset_receive_click(){
        toastShow("抵扣劵领取弹出");
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.offset_receive_control:
                    ofsset_receive_click();
                    break;
            }
        }
    };

    @Override
    public void handlerW1(Bundle bundle) {
        super.handlerW1(bundle);
        String key = bundle.getString("key");
        try {
            if(key.equals("getdata")){
                JSONObject jo = new JSONObject(bundle.getString("data"));
                JSONArray res_goods = jo.getJSONArray("res_goods");
                JSONArray adv = jo.getJSONArray("adv");
                int adcount = jo.getInt("adcount");
                JSONObject goods = jo.getJSONObject("goods");

            }
            setListener();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
