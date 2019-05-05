package com.ttyp.tiantao.ttmb.activity.goods;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jaychan.view.MultipleTextView;
import com.ttyp.tiantao.R;
import com.ttyp.tiantao.assembly.mydialog.MyDialog2;
import com.ttyp.tiantao.ttmb.activity.MyBaseActivity;
import com.ttyp.tiantao.ttmb.activity.login.LoginActivity;
import com.ttyp.tiantao.ttmb.adapter.HomeWaterFallAdapter;
import com.ttyp.tiantao.ttmb.adapter.ViewPageShowAdapter;
import com.ttyp.tiantao.ttmb.entity.GoodsEntity;
import com.ttyp.tiantao.ttmb.entity.GoodsListItem;
import com.ttyp.tiantao.ttmb.template.StaticValue;
import com.ttyp.tiantao.ttmb.template.URLValue;
import com.ttyp.tiantao.ttmb.util.ConnectLinkService;
import com.ttyp.tiantao.ttmb.util.GetJSON;

import org.json.JSONArray;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GoodsDetailActivity extends MyBaseActivity {

    //适配器
    //轮播图适配器
    private ViewPageShowAdapter viewPageShowAdapter ;
    private HomeWaterFallAdapter adapter;
    private StaggeredGridLayoutManager mLayoutManager;
    private MyDialog2 myDialog2 = null;

    //轮播图组件
    LinearLayout vp_layout;
    ViewPager vp_main;
    LinearLayout ll_main;
    MultipleTextView shareEarn;

    //商品标题
    TextView goodsListImageTextView;
    TextView priceDiscount;
    MultipleTextView priceTaob;
    MultipleTextView salesVolume;

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

    //猜你喜欢
    RecyclerView moreGoodsList;

    LinearLayout layout1;
    LinearLayout layout2;
    LinearLayout layout3;
    LinearLayout layout4;

    //数据常量
    private int previousPosition = 0;

    //数据list集合
    List<String> vp_main_list2_1 = new ArrayList<>();
    List<GoodsListItem> list = new ArrayList<>();
    List<GoodsListItem> list1 = new ArrayList<>();
    List<String> goodsdetails = new ArrayList<>();
    GoodsEntity model = new GoodsEntity(0);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_detail);
        try {
            initView();
            initData();
        }catch (Exception e){
            e.printStackTrace();
        }
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
        priceTaob = findViewById(R.id.price_taob);
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

        layout1 = findViewById(R.id.goods_detail_recharge);
        layout2 = findViewById(R.id.goods_detail_share);
        layout3 = findViewById(R.id.goods_detail_buyprice);
        layout4 = findViewById(R.id.goods_detail_buycounpon);

    }
    String goodsid;
    //初始化绑定页面数据
    private void initData(){
        try {
            goodsid  = getIntent().getExtras().getString("goodsid");
            Thread th1 = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Map<String, String> params = new HashMap<>();
                        params.put("numid", goodsid);
                        Message message = new Message();
                        Bundle b = new Bundle();
                        Log.e("GOODS_DETAIL_GOODSID",goodsid);
                        Map<String, Object> result = GetJSON.getInstance().getJSON(ConnectLinkService.getInstance().ConnectHttpsGET(params, URLValue.GOODS_DETAIL_DATA));
                        if (result == null) {
                            message.what = 0;
                            b.putString("msg", "网络请求异常");
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
                        } else if (res == 1) {
                            message.what = 1;
                            b.putString("key", "getdata");
                            b.putString("data", result.get("data").toString());
                            message.setData(b);
                            handler.sendMessage(message);
                        } else {
                            message.what = 0;
                            b.putString("msg", "网络请求异常");
                            message.setData(b);
                            handler.sendMessage(message);
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            });
            th1.start();

            mLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
            if (list == null) {
                list = new ArrayList<>();
            }
            adapter = new HomeWaterFallAdapter(GoodsDetailActivity.this, list, new HomeWaterFallAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(long position) {
                    Map<String, String> params = new HashMap<>();
                    params.put("goodsid", position + "");
                    turnToActivity(GoodsDetailActivity.class, params);
                }
            });
            moreGoodsList.setLayoutManager(mLayoutManager);
            moreGoodsList.setAdapter(adapter);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //初始化绑定页面事件
    private void setListener() {
        try {
            //轮播图事件绑定
            if (vp_main_list2_1 == null) {
                vp_main_list2_1 = new ArrayList<>();
            }
            vp_main_list2_1.clear();
            vp_main_list2_1.addAll(model.getGoods_adv());

            ll_main.removeAllViews();
            for (int i = 0; i < vp_main_list2_1.size(); i++) {
                // 添加指示点
                ImageView point = new ImageView(GoodsDetailActivity.this);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(5, 5);
                params.rightMargin = 20;
                point.setLayoutParams(params);
                point.setImageResource(R.drawable.point_bg);
                if (i == 0) {
                    point.setEnabled(true);
                } else {
                    point.setEnabled(false);
                }
                ll_main.addView(point);
            }

            viewPageShowAdapter = new ViewPageShowAdapter(GoodsDetailActivity.this, vp_main_list2_1, null, null);
            vp_main.setAdapter(viewPageShowAdapter);
            vp_main.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(final int position) {
                    if (vp_main_list2_1.size() > 0) {
                        int newPostion = position % vp_main_list2_1.size();
                        //取出postion的位置小圆点设置为true
                        ll_main.getChildAt(newPostion).setEnabled(true);
                        //把一个小圆点设置为false
                        ll_main.getChildAt(previousPosition).setEnabled(false);
                        previousPosition = newPostion;
                    }
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
            if (StaticValue.USER != null) {
                setLayout(StaticValue.USER.getIdentification());
            } else {
                setLayout(-1);
            }
            goodsDetail.removeAllViews();
            if (goodsdetails.size() > 0) {
                for (int i = 0; i < goodsdetails.size(); i++) {
                    View view = LayoutInflater.from(GoodsDetailActivity.this).inflate(R.layout.item_viewpage_image, null);
                    ImageView imageView = view.findViewById(R.id.viewpage_image);
                    Glide.with(GoodsDetailActivity.this).asBitmap().load(goodsdetails.get(i)).into(imageView);
                    goodsDetail.addView(view);
                }
            }


            offsetControl.setOnClickListener(clickListener);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void setLayout(int type){
        try {
            switch (type) {
                case 0:
                    shareEarn.setVisibility(View.GONE);
                    break;
                case 1:
                    shareEarn.setVisibility(View.VISIBLE);
                    shareEarn.setContentText(model.getCommission());
                    break;
                case -1:
                    shareEarn.setVisibility(View.GONE);
                    break;
            }
            BigDecimal bg = new BigDecimal(model.getCouponPrice());
            double f1 = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

            int a = (int) (f1 * 100);
            int p_n = a / 100;
            int p_m = a % 100;
            goodsListImageTextView.setText(model.getTitle());
            priceDiscount.setText(Html.fromHtml("抵用价:￥<big>" + p_n + "</big>" + "." + p_m));
            priceTaob.setContentText(model.getPrice());
            salesVolume.setContentText(model.getVolume() + "");
            offsetPrice.setText(model.getCouponAmount());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date1 = model.getBegintime();
            Date date2 = model.getEndtime();
            String datetim = "有效期:" + sdf.format(date1) + "至" + sdf.format(date2);
            offsetDate.setText(datetim);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void ofsset_receive_click(){
        Date date2 = model.getEndtime();
        Date date1 = new Date();
        if(date2.getTime() > date1.getTime()) {
            if(StaticValue.USER==null){
                turnToActivity(LoginActivity.class,new HashMap<String, String>());
            }else {
                if (myDialog2 == null) {
                    myDialog2 = new MyDialog2(GoodsDetailActivity.this, R.style.MyDialogStyleBottom);
                    myDialog2.setConponText(model.getCouponAmount()).setNoOnclickListener(new MyDialog2.onNoOnclickListener() {
                        @Override
                        public void onNoClick() {
                            myDialog2.dismiss();
                        }
                    }).setYesOnclickListener(new MyDialog2.onYesOnclickListener() {
                        @Override
                        public void onYesOnclick() {
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        Map<String, String> params = new HashMap<>();
                                        params.put("uid", StaticValue.USER.getUid());
                                        params.put("auctionid", model.getNumIid() + "");
                                        Message message = new Message();
                                        Bundle b = new Bundle();
                                        Map<String, Object> result = GetJSON.getInstance().getJSON(ConnectLinkService.getInstance().ConnectHttpsGET(params, URLValue.GOODS_DETAIL_GETCOUPON));
                                        if (result == null) {
                                            message.what = 0;
                                            b.putString("msg", "网络请求异常");
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
                                        } else if (res == 1) {
                                            message.what = 1;
                                            b.putString("key", "getcoupon");
                                            b.putString("data", result.get("data").toString());
                                            message.setData(b);
                                            handler.sendMessage(message);
                                        } else {
                                            message.what = 0;
                                            b.putString("msg", "网络请求异常");
                                            message.setData(b);
                                            handler.sendMessage(message);
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }).start();
                        }
                    });
                    myDialog2.show();
                } else {
                    myDialog2.show();
                }
            }
        }else {
            toastShow("该优惠券已过期");
        }
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
                list1.clear();
                for(int i = 0;i<res_goods.length();i++){
                    JSONObject jo1 = res_goods.getJSONObject(i);
                    int numIid = jo1.getInt("num_iid");
                    String pic = jo1.getString("pict_url");
                    String provcty = jo1.getString("provcity");
                    String price = jo1.getString("reserve_price");
                    String title = jo1.getString("title");
                    int volume = jo1.getInt("volume");
                    String zk_final_price = jo1.getString("zk_final_price");
                    GoodsListItem goodsListItem = new GoodsListItem(numIid);
                    goodsListItem.setIsGoodsoffset(false)
                            .setIsGoodsearn(false)
                            .setGoodsTitle(title)
                            .setGoodsImage(pic)
                            .setPrice_now(zk_final_price)
                            .setPrice_old(price)
                            .setSales(volume + "");
                    list1.add(goodsListItem);
                }
                list.clear();
                list.addAll(list1);
                adapter.notifyDataSetChanged();
                JSONArray adv = jo.getJSONArray("adv");
                for(int i = 0;i<adv.length();i++){
                    String advpath = adv.getString(i);
                    goodsdetails.add(advpath);
                }
                int adcount = jo.getInt("adcount");

                JSONObject goods = jo.getJSONObject("goods");
                long numIid = goods.getLong("numIid");
                String title = goods.getString("title");
                String pic = goods.getString("pic");
                String price = goods.getString("price");
                String couponPrice = goods.getString("couponPrice");
                int couponRate = goods.getInt("couponRate");
                String commission = goods.getString("commission");
                int volume = goods.getInt("volume");
                String clickUrl = goods.getString("clickUrl");
                String couponAmount = goods.getString("couponAmount");
                String content = goods.getString("content");
                String shopTitle = goods.getString("shopTitle");
                String goods_adv = goods.getString("adv");
                String startTime = goods.getString("startTime");
                String endTime = goods.getString("endTime");
                List<String> goodsAdvs = new ArrayList<>();
                if(goods_adv!=null&&!"".equals(goods_adv)){
                    String[] strings = goods_adv.split(",");
                    for (int i = 0;i<strings.length;i++){
                        goodsAdvs.add(strings[i]);
                    }
                }
                model = new GoodsEntity(numIid);
                long lst = Long.parseLong(startTime);
                long led = Long.parseLong(endTime);
                Date sD = new Date(lst*1000L);
                Date eD = new Date(led*1000L);
                model.setTitle(title).setPic(pic).setPrice(price)
                        .setCouponPrice(couponPrice).setCouponRate(couponRate)
                        .setCommission(commission).setVolume(volume)
                        .setClickUrl(clickUrl).setCouponAmount(couponAmount)
                        .setContent(content).setShopTitle(shopTitle)
                        .setBegintime(sD).setEndtime(eD)
                        .setGoods_adv(goodsAdvs);

                setListener();
            }
            if(key.equals("getcoupon")){

            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
