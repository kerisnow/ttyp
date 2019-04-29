package com.ttyp.tiantao.ttmb.activity.goods;

import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ttyp.tiantao.R;
import com.ttyp.tiantao.ttmb.adapter.HomeWaterFallAdapter;
import com.ttyp.tiantao.ttmb.entity.GoodsListItem;
import com.ttyp.tiantao.ttmb.extendsparent.MyFragment;
import com.ttyp.tiantao.ttmb.template.URLValue;
import com.ttyp.tiantao.ttmb.util.ConnectLinkService;
import com.ttyp.tiantao.ttmb.util.GetJSON;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GoodsListFragment extends MyFragment {
    RecyclerView recyclerView;
    String key;
    private final int size = 20;
    int page = 1;

    private HomeWaterFallAdapter adapter;
    private StaggeredGridLayoutManager mLayoutManager;

    private List<GoodsListItem> list = new ArrayList<>();
    private List<GoodsListItem> list1 = new ArrayList<>();

    public static GoodsListFragment getInstance(String key){
        GoodsListFragment fragment = new GoodsListFragment();
        Bundle bundle = new Bundle();
        bundle.putString("key",key);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = null;
        try{
            view = inflater.inflate(R.layout.fragment_fllayout,container,false);
            initView(view);
            initData();
        }catch (Exception e){
            e.printStackTrace();
            Log.e("goodsFrag",e.toString());
        }
        return view;
    }

    private void initView(View view){
        recyclerView = view.findViewById(R.id.fl_recyc);
    }

    private void initData(){
        try {
            key = getArguments().getString("key");
            mLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
            if(list==null){
                list = new ArrayList<>();
            }
            adapter = new HomeWaterFallAdapter(getActivity(), list, new HomeWaterFallAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    Map<String, String> params = new HashMap<>();
                    params.put("key", list.get(position).getGoodsId() + "");
                    fragmentResultData.turnToActivity(GoodsDetailActivity.class, params);
                }
            });
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setAdapter(adapter);
            recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
                //用来标记是否正在向最后一个滑动
                boolean isSlidingToLast = false;

                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                    StaggeredGridLayoutManager manager = (StaggeredGridLayoutManager) recyclerView.getLayoutManager();
                    // 当不滚动时
                    if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                        //获取最后一个完全显示的ItemPosition
                        int[] lastPositions = new int[(manager).getSpanCount()];

                        manager.findLastVisibleItemPositions(lastPositions);

                        int lastVisibleItem = findMax(lastPositions);

                        if (lastVisibleItem == recyclerView.getLayoutManager().getItemCount() - 1) {
                            Thread th1 = new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        Map<String, String> params = new HashMap<>();
                                        params.put("cid", key);
                                        params.put("page", list.size()/size + "");
                                        params.put("size", size + "");
                                        Message message = new Message();
                                        Bundle bundle = new Bundle();
                                        Map<String, Object> result = GetJSON.getInstance().getJSON(ConnectLinkService.getInstance().ConnectHttpsGET(params, URLValue.GET_GOODSLIST));
                                        if (result == null) {
                                            message.what = 0;
                                            bundle.putString("msg", "网络请求异常");
                                            message.setData(bundle);
                                            handler.sendMessage(message);
                                            return;
                                        }
                                        int res = (int) result.get("res");
                                        String msg = (String) result.get("msg");
                                        if (res == 0) {
                                            message.what = 0;
                                            bundle.putString("msg", msg);
                                            message.setData(bundle);
                                            handler.sendMessage(message);
                                        } else if (res == 1) {
                                            list1.clear();
                                            list1.addAll(list);
                                            JSONArray ja = (JSONArray)result.get("data");
                                            for (int i = 0; i < ja.length(); i++) {
                                                JSONObject jo = ja.getJSONObject(i);
                                                int goodsid = jo.getInt("id");
                                                String goodsImage = jo.getString("pic");
                                                String goodsTitle = jo.getString("title");
                                                String price = jo.getString("price");
                                                String couponPrice = jo.getString("couponPrice");
                                                String couponRate = jo.getString("couponRate");
                                                String commission = jo.getString("commission");
                                                String commissionRate = jo.getString("commissionRate");
                                                String couponAmount = jo.getString("couponAmount");
                                                String shopTitle = jo.getString("shopTitle");
                                                int volume = jo.getInt("volume");
                                                GoodsListItem goodsListItem = new GoodsListItem(goodsid);
                                                goodsListItem.setGoodsoffset(couponAmount)
                                                        .setGoodsearn(commission)
                                                        .setGoodsTitle(goodsTitle)
                                                        .setGoodsImage(goodsImage)
                                                        .setPrice_now(couponPrice)
                                                        .setShopname(shopTitle)
                                                        .setPrice_old(price)
                                                        .setSales(volume + "");
                                                list1.add(goodsListItem);
                                            }
                                            message.what = 1;
                                            bundle.putString("key", "scroll");
                                            message.setData(bundle);
                                            handler.sendMessage(message);
                                        } else {
                                            message.what = 0;
                                            bundle.putString("msg", "网络请求异常");
                                            message.setData(bundle);
                                            handler.sendMessage(message);
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                            th1.start();
                        }
                    }
                }

                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    //dx用来判断横向滑动方向，dy用来判断纵向滑动方向
                    if (dx > 0) {
                        //大于0表示正在向右滚动
                        isSlidingToLast = true;
                    } else {
                        //小于等于0表示停止或向左滚动
                        isSlidingToLast = false;
                    }
                }
            });

            Thread th1 = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Map<String, String> params = new HashMap<>();
                        params.put("cid", key);
                        params.put("page", page + "");
                        params.put("size", size + "");
                        Message message = new Message();
                        Bundle bundle = new Bundle();
                        Map<String, Object> result = GetJSON.getInstance().getJSON(ConnectLinkService.getInstance().ConnectHttpsGET(params, URLValue.GET_GOODSLIST));
                        if (result == null) {
                            message.what = 0;
                            bundle.putString("msg", "网络请求异常12");
                            message.setData(bundle);
                            handler.sendMessage(message);
                            return;
                        }
                        int res = (int) result.get("res");
                        String msg = (String) result.get("msg");
                        if (res==0){
                            message.what = 0;
                            bundle.putString("msg", msg + "-1");
                            message.setData(bundle);
                            handler.sendMessage(message);
                        } else if (res==1) {
                            list1.clear();
                            JSONArray ja = (JSONArray)result.get("data");
                            for (int i = 0; i < ja.length(); i++) {
                                JSONObject jo = ja.getJSONObject(i);
                                int goodsid = jo.getInt("id");
                                String goodsImage = jo.getString("pic");
                                String goodsTitle = jo.getString("title");
                                String price = jo.getString("price");
                                String couponPrice = jo.getString("couponPrice");
                                String couponRate = jo.getString("couponRate");
                                String commission = jo.getString("commission");
                                String commissionRate = jo.getString("commissionRate");
                                String couponAmount = jo.getString("couponAmount");
                                String shopTitle = jo.getString("shopTitle");
                                int volume = jo.getInt("volume");
                                GoodsListItem goodsListItem = new GoodsListItem(goodsid);
                                goodsListItem.setGoodsoffset(couponAmount+"元")
                                        .setGoodsearn(commission+"元")
                                        .setGoodsTitle(goodsTitle)
                                        .setGoodsImage(goodsImage)
                                        .setPrice_now(couponPrice)
                                        .setShopname(shopTitle)
                                        .setPrice_old(price)
                                        .setSales(volume + "");
                                list1.add(goodsListItem);
                            }
                            message.what = 1;
                            bundle.putString("key", "getdata");
                            message.setData(bundle);
                            handler.sendMessage(message);
                        } else {
                            message.what = 0;
                            bundle.putString("msg", "网络请求异常");
                            message.setData(bundle);
                            handler.sendMessage(message);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            th1.start();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void handlerW1(Bundle bundle) {
        super.handlerW1(bundle);
        try {
            String key = bundle.getString("key");
            if (key.equals("getdata")) {
                list.clear();
                list.addAll(list1);
                adapter.notifyDataSetChanged();
            }
            if (key.equals("scroll")) {
                list.clear();
                list.addAll(list1);
                adapter.notifyDataSetChanged();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }



    private int findMax(int[] lastPositions) {
        int max = lastPositions[0];
        for (int value : lastPositions) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }
}
