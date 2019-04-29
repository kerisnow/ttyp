package com.ttyp.tiantao.ttmb.activity.recommend;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ttyp.tiantao.R;
import com.ttyp.tiantao.assembly.myButton.MenuButton;
import com.ttyp.tiantao.ttmb.activity.connectweb.ConnectWebActivity;
import com.ttyp.tiantao.ttmb.activity.goods.GoodsListActivity;
import com.ttyp.tiantao.ttmb.activity.tabtools.SearchActivity;
import com.ttyp.tiantao.ttmb.adapter.YouxBrandList1Adapter;
import com.ttyp.tiantao.ttmb.entity.RecommendList;
import com.ttyp.tiantao.ttmb.entity.RecommendListEntry;
import com.ttyp.tiantao.ttmb.extendsparent.MyFragment;
import com.ttyp.tiantao.ttmb.template.URLValue;
import com.ttyp.tiantao.ttmb.util.ConnectLinkService;
import com.ttyp.tiantao.ttmb.util.GetJSON;
import com.ttyp.tiantao.ttmb.util.MyApplication;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecommendFragment  extends MyFragment {
    //数据存放
    //功能路径
    Map<String,Object> pagesUrlMap = new HashMap<>();
    //模拟功能路径
    //是否开启模拟
    Boolean isDefalut = false;

    //数据list集合
    List<RecommendListEntry> recommendListEntries = new ArrayList<>();
    List<RecommendListEntry> recommendListEntries1 = new ArrayList<>();
    //数据常量

    //适配器
    YouxBrandList1Adapter brandList1Adapter;

    //搜素组件
    LinearLayout search_layout;
    ImageView search_search_pic;
    TextView search_search_text;

    //品牌组件
    LinearLayout menuButton_layout;
    MenuButton[] menuButtons = new MenuButton[10];

    //广告位
    LinearLayout advertisement_layout;
    ImageView advertisement_pic;

    //品牌列表
    ListView brand_listview;

    private int counter = 0;

    //继承类方法开始
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);

        View view = null;
        try{
            view = inflater.inflate(R.layout.fragment_youx,container,false);
            initView(view);
            initData();
//            setListener();
        }catch (Exception e){
            e.printStackTrace();
            Log.i("recommend",e.toString());
        }
        return view;
    }

    @Override
    public boolean canCallBack() {
//        return this.webView.canGoBack();
        return false;
    }

    @Override
    public void callBack() {
//        this.webView.goBack();
    }
    //继承类方法结束

    //自定义方法开始
    //初始化控件
    private void initView(View v){
        //搜索组件
        search_layout = v.findViewById(R.id.youx_search_control);
        search_search_pic = v.findViewById(R.id.youx_search_search_pic);
        search_search_text = v.findViewById(R.id.youx_search_search_text);

        //菜单组件
        menuButton_layout = v.findViewById(R.id.youx_menu_bar_control);
        menuButtons[0] = v.findViewById(R.id.youx_menu_bar_button1);
        menuButtons[1] = v.findViewById(R.id.youx_menu_bar_button2);
        menuButtons[2] = v.findViewById(R.id.youx_menu_bar_button3);
        menuButtons[3] = v.findViewById(R.id.youx_menu_bar_button4);
        menuButtons[4] = v.findViewById(R.id.youx_menu_bar_button5);
        menuButtons[5] = v.findViewById(R.id.youx_menu_bar_button6);
        menuButtons[6] = v.findViewById(R.id.youx_menu_bar_button7);
        menuButtons[7] = v.findViewById(R.id.youx_menu_bar_button8);
        menuButtons[8] = v.findViewById(R.id.youx_menu_bar_button9);
        menuButtons[9] = v.findViewById(R.id.youx_menu_bar_button10);

        //广告位
        advertisement_layout = v.findViewById(R.id.home_advertisement_control);
        advertisement_pic = v.findViewById(R.id.home_advertisement_pic);

        //品牌列表
        brand_listview = v.findViewById(R.id.youx_brand_list_control);
    }

    //初始化数据
    private void initData(){
        Thread th2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Message message = new Message();
                    Bundle b = new Bundle();
                    Map<String, Object> result = GetJSON.getInstance().getJSON(ConnectLinkService.getInstance().ConnectHttpsPost(null, URLValue.HOME_ROTATION));
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
                            b.putString("key", "home_ratation");
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
                }catch (Exception e){
                    e.printStackTrace();
                    Log.e("home4",e.toString()+"_"+e.getLocalizedMessage());
                }
            }
        });
        th2.start();

        Thread th1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Map<String, String> params = new HashMap<>();
                    Message message = new Message();
                    Bundle b = new Bundle();
                    Map<String, Object> result = GetJSON.getInstance().getJSON(ConnectLinkService.getInstance().ConnectHttpsPost(params, URLValue.RECOMMEND_DATA));
                    if (result == null) {
                        message.what = 0;
                        b.putString("msg", "网络连接异常");
                        message.setData(b);
                        handler.sendMessage(message);
                        return;
                    }
                    int res = (int) result.get("res");
                    String msg = (String) result.get("msg");
                    if (res==0) {
                        message.what = 0;
                        b.putString("msg", msg);
                        message.setData(b);
                        handler.sendMessage(message);
                    } else if (res==1) {
                        message.what = 1;
                        b.putString("key", "getdata");
                        b.putString("data",result.get("data").toString());
                        message.setData(b);
                        handler.sendMessage(message);
                    } else {
                        message.what = 0;
                        b.putString("msg", "网络连接异常");
                        message.setData(b);
                        handler.sendMessage(message);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        th1.start();
    }

    //初始化控件事件
    private void setListener(){
        //查询框事件绑定
        try {
            search_search_pic.setOnClickListener(clickListener);
            search_search_pic.setClickable(true);
            search_search_text.setOnKeyListener(keyListener);

            //菜单组件事件绑定
            for (int i = 0; i < menuButtons.length; i++) {
                menuButtons[i].setClickable(true);
                menuButtons[i].setOnClickListener(clickListener);
            }

            //广告位事件绑定
            advertisement_pic.setOnClickListener(clickListener);
            recommendListEntries.clear();
            recommendListEntries.addAll(recommendListEntries1);
            //品牌列表事件绑定
            brandList1Adapter = new YouxBrandList1Adapter(MyApplication.getContext(), R.layout.item_grid_list, recommendListEntries, new YouxBrandList1Adapter.OnclickerRun() {
                @Override
                public void goodsOnclick(String id, int index) {
                    Toast.makeText(MyApplication.getContext(), "商品跳转弹出，商品id:" + id, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void moreOnclick(int id, int index) {
//                Toast.makeText(MyApplication.getContext(),"店铺商品列表跳转弹出，店铺id:"+id,Toast.LENGTH_SHORT).show();
                    Map<String, String> params = new HashMap<>();
                    params.put("title", recommendListEntries.get(index).getFavorites_title());
                    params.put("type", "recommendMore");
                    params.put("keywords", id + "");
                    fragmentResultData.turnToActivity(GoodsListActivity.class, params);
                }
            });
            brand_listview.setAdapter(brandList1Adapter);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //点击事件监听
    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try {
                switch (v.getId()) {
                    case R.id.youx_search_search_pic:
                        search_Onclick();
                        break;
                    case R.id.menu_bar_button1:
                        menuBar_Onclick(0);
                        break;
                    case R.id.menu_bar_button2:
                        menuBar_Onclick(1);
                        break;
                    case R.id.menu_bar_button3:
                        menuBar_Onclick(2);
                        break;
                    case R.id.menu_bar_button4:
                        menuBar_Onclick(3);
                        break;
                    case R.id.menu_bar_button5:
                        menuBar_Onclick(4);
                        break;
                    case R.id.menu_bar_button6:
                        menuBar_Onclick(5);
                        break;
                    case R.id.menu_bar_button7:
                        menuBar_Onclick(6);
                        break;
                    case R.id.menu_bar_button8:
                        menuBar_Onclick(7);
                        break;
                    case R.id.menu_bar_button9:
                        menuBar_Onclick(8);
                        break;
                    case R.id.menu_bar_button10:
                        menuBar_Onclick(9);
                        break;
                    case R.id.home_advertisement_pic:
                        advertisement_Onclick(0);
                        break;
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    };

    //软键盘输入事件监听
    private View.OnKeyListener keyListener = new View.OnKeyListener() {
        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            if(keyCode == KeyEvent.KEYCODE_ENTER){
                search_Onclick();
                return true;
            }
            return false;
        }
    };

    //查询事件
    private void search_Onclick(){
//        InputMethodManager imm = (InputMethodManager) MyApplication.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
//        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
//        Toast.makeText(MyApplication.getContext(),"查询商品框 查询事件响应弹出 ，搜索内容："+ search_search_text.getText(),Toast.LENGTH_SHORT).show();
        fragmentResultData.turnToActivity(SearchActivity.class,new HashMap<String, String>());
    }

    //菜单组件点击事件
    private void menuBar_Onclick(int index){
        String url = null;
        url = (String) pagesUrlMap.get("menu_bar_"+index);
//        Toast.makeText(getActivity(),"菜单栏 点击事件响应弹出 ，url："+ url,Toast.LENGTH_SHORT).show();
        Map<String, String> params = new HashMap<>();

        if(url.indexOf("http") != -1) {
            params.put("title",menuButtons[index].getText());
            params.put("type","menuButton");
            params.put("keywords",url);
            fragmentResultData.turnToActivity(GoodsListActivity.class, params);
        }else{
            params.put("url", url);
            fragmentResultData.turnToActivity(ConnectWebActivity.class,params);
        }
    }

    //广告位点击事件
    private void advertisement_Onclick(int index){
        String url = null;
//        if(isDefalut){
//            pagesUrlMap = pagesUrlDefalutMap;
//        }
//        url = (String) pagesUrlMap.get("advertisement_" + index);
        toastShow("广告位 点击事件响应弹出 ，url："+ url);
    }

    @Override
    public void handlerW1(Bundle bundle) {
        super.handlerW1(bundle);
        String key = bundle.getString("key");
        try {
            switch (key) {
                case "getdata":
                    counter ++;
                    String resData = bundle.getString("data");
                    if(resData!=null) {
                        JSONArray ja = new JSONArray(resData);
                        recommendListEntries1.clear();
                        for (int i = 0; i < ja.length(); i++) {
                            JSONObject jo = ja.getJSONObject(i);
                            int id = jo.getInt("id");
                            int favorites_id = jo.getInt("favorites_id");
                            String favorites_title = jo.getString("favorites_title");
                            int type = jo.getInt("type");
                            int created = jo.getInt("created");
                            int changed = jo.getInt("changed");
                            JSONArray ja1 = jo.getJSONArray("list");
                            List<RecommendList> list = new ArrayList<>();
                            for (int k = 0; k < ja1.length(); k++) {
                                JSONObject jo1 = ja1.getJSONObject(k);
                                String pic = jo1.getString("pic");
                                String title = jo1.getString("title");
                                if(title.length()>6){
                                    String s = title.substring(0,5);
                                    title = s+"...";
                                }
                                String price = jo1.getString("price");
                                String couponPrice = jo1.getString("couponPrice");
                                String numiid = jo1.getString("numIid");
                                String view = jo1.getString("view");
                                list.add(new RecommendList(pic,title,price,couponPrice,numiid,view));
                            }
                            RecommendListEntry entry = new RecommendListEntry(id);
                            entry.setFavorites_id(favorites_id)
                                    .setFavorites_title(favorites_title)
                                    .setType(type).setCreated(created)
                                    .setChanged(changed)
                                    .setRecommendList(list);
                            recommendListEntries1.add(entry);
                        }
                    }
                    break;
                case "home_ratation":
                    counter++;
                    Map<String, Object> data = GetJSON.getInstance().getJSON(bundle.getString("data"));
                    if (data.containsKey("seller")) {
                        menuButton_layout.setVisibility(View.VISIBLE);
                        JSONArray ja = (JSONArray) data.get("seller");

                        int index = ja.length();
                        for (int i = 0; i < index; i++) {
                            JSONObject jo = ja.getJSONObject(i);
                            Log.e("json _ "+i,jo.toString());
                            menuButtons[i].setImageViewSrc(jo.getString("pic"));
                            menuButtons[i].setTextViewText(jo.getString("title"));
                            menuButtons[i].setTextViewSize(25);
                            pagesUrlMap.put("menu_bar_" + i, jo.getString("url"));
                        }
                    } else {
                        menuButton_layout.setVisibility(View.GONE);
                    }
                    break;
            }
            if(counter==2){
                setListener();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


}
