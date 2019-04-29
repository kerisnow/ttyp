package com.ttyp.tiantao.ttmb.activity.home;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ttyp.tiantao.R;
import com.ttyp.tiantao.assembly.myBar.MyScorllView;
import com.ttyp.tiantao.assembly.myButton.MenuButton;
import com.ttyp.tiantao.assembly.myGrid.MenuPictureGrid;
import com.ttyp.tiantao.assembly.myListView.HorizontalListView;
import com.ttyp.tiantao.ttmb.activity.connectweb.ConnectWebActivity;
import com.ttyp.tiantao.ttmb.activity.goods.GoodsListActivity;
import com.ttyp.tiantao.ttmb.activity.goods.GoodsListFragment;
import com.ttyp.tiantao.ttmb.activity.tabtools.SearchActivity;
import com.ttyp.tiantao.ttmb.adapter.HorizonMenuListAdapter;
import com.ttyp.tiantao.ttmb.adapter.ViewPageShowAdapter;
import com.ttyp.tiantao.ttmb.entity.ClassifyMenuList;
import com.ttyp.tiantao.ttmb.entity.HomePageSave;
import com.ttyp.tiantao.ttmb.extendsparent.MyFragment;
import com.ttyp.tiantao.ttmb.template.URLValue;
import com.ttyp.tiantao.ttmb.util.BaseUtil;
import com.ttyp.tiantao.ttmb.util.ConnectLinkService;
import com.ttyp.tiantao.ttmb.util.DBManger;
import com.ttyp.tiantao.ttmb.util.GetJSON;
import com.ttyp.tiantao.ttmb.util.OnMultClickListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomePageFragment extends MyFragment {

//    public WebView webView;
    //数据存放
    //是否开启模拟
    Boolean isDefalut = false;
    Map<String,Object> pagesUrlMap = new HashMap<>();

    //数据list集合
    List<String> vp_main_list2_1 = new ArrayList<>();
    List<String> vp_main_list2 = new ArrayList<>();
    List<String> vp_main_url_list = new ArrayList<>();
    List<String> vp_main_url_list1 = new ArrayList<>();
    List<String> menu_classify_list = new ArrayList<>();
    List<String> menu_classify_list1 = new ArrayList<>();
    List<String> menu_classify_url_list = new ArrayList<>();
    List<String> menu_classify_url_list1 = new ArrayList<>();
    List<ClassifyMenuList> classifyMenuLists = new ArrayList<>();
    List<ClassifyMenuList> classifyMenuLists1 = new ArrayList<>();

    //数据常量
    private int previousPosition = 0;
    private Boolean is_menu_grid_show = false;
    private int hmindex = 0;
    private Fragment nowFragment = null;

    //适配器
    //轮播图适配器
    ViewPageShowAdapter viewPageShowAdapter ;
    HorizonMenuListAdapter horizonMenuListAdapter;
    FragmentManager fragmentManager;

    MyScorllView scrollView;

    //搜素组件
    ConstraintLayout search_layout;
    ImageView search_search_pic;
    ImageView search_text_back;
    TextView search_search_text;
    ImageView search_msg_pic;

    //轮播图组件
    RelativeLayout vp_layout;
    ViewPager vp_main;
    LinearLayout ll_main;

    //菜单组件
    ConstraintLayout menuButton_layout;
    MenuButton[] menuButtons = new MenuButton[10];

    //广告位
    LinearLayout advertisement_layout;
    ImageView advertisement_pic;

    //推荐位
    LinearLayout goods_type_laout;

    //分类组件
    HorizontalListView menu_listView;

    //推荐商品列表
    FrameLayout recommendGoods;


    //继承类方法开始
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = null;
        try{
            view = inflater.inflate(R.layout.fragment_shouy,container,false);
            scrollView = (MyScorllView)view;
            initView(view);
            initData();
            getAllData();
        }catch (Exception e){
            e.printStackTrace();
            Log.e("home1",e.toString());
        }
        return view;
    }

    @Override
    public boolean canCallBack() {return false;}

    @Override
    public void callBack() {}

    @Override
    public void handlerW1(Bundle bundle) {
        super.handlerW1(bundle);
        try {
            String key = bundle.getString("key");
            if(key.equals("home_ratation")) {
                Map<String, Object> data = GetJSON.getInstance().getJSON(bundle.getString("data"));
                if (data.containsKey("category")) {
                    JSONArray ja = (JSONArray)data.get("category");
                    int index = ja.length();
                    for (int i = 0; i < index; i++) {
                        JSONObject jo = ja.getJSONObject(i);
                        menu_classify_list1.add(jo.getString("name"));
                        menu_classify_url_list1.add(jo.getString("id"));
                        classifyMenuLists1.add(new ClassifyMenuList(jo.getString("name"), jo.getString("id")));
                    }
                    setData("category");
                } else {

                }
                if (data.containsKey("adv")) {
                    vp_layout.setVisibility(View.VISIBLE);
                    JSONArray ja = (JSONArray)data.get("adv");
                    int index = ja.length();
                    ll_main.removeAllViews();
                    for (int i = 0; i < index; i++) {
                        JSONObject jo = ja.getJSONObject(i);
                        vp_main_list2.add(URLValue.DEF_URL + jo.getString("pic"));
                        vp_main_url_list1.add(jo.getString("links"));
                        // 添加指示点
                        ImageView point = new ImageView(getActivity());
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
                    setData("adv");
                } else {
                    vp_layout.setVisibility(View.GONE);
                }
                if (data.containsKey("seller")) {
                    menuButton_layout.setVisibility(View.VISIBLE);
                    JSONArray ja = (JSONArray)data.get("seller");
                    int index = ja.length();
                    for (int i = 0; i < index; i++) {
                        JSONObject jo = ja.getJSONObject(i);
                        menuButtons[i].setImageViewSrc( jo.getString("pic"));
                        menuButtons[i].setTextViewText( jo.getString("title"));
                        menuButtons[i].setTextViewSize(25);
                        pagesUrlMap.put("menu_bar_"+i,jo.getString("url"));
                    }
                } else {
                    menuButton_layout.setVisibility(View.GONE);
                }
                if (data.containsKey("cube")) {
                    JSONArray ja = (JSONArray)data.get("cube");
                    pagesUrlMap.put("cube",ja.length());
                    for(int i=0;i<ja.length();i++) {
                        JSONObject jo = ja.getJSONObject(i);
                        String title = jo.getString("title");
                        String pic = jo.getString("pic");
                        JSONArray ja1 = jo.getJSONArray("data");
                        pagesUrlMap.put("cube_"+i,ja1.length());
                        List<String> imagepath = new ArrayList<>();
                        for (int k = 0; k < ja1.length(); k++) {
                            JSONObject jo1 = ja1.getJSONObject(k);
                            String pic1 = jo1.getString("pic");
                            imagepath.add(pic1);
                        }
                        MenuPictureGrid view = new MenuPictureGrid(getActivity(),ja1.length(),imagepath,title,pic);
                        for (int k = 0; k < ja1.length(); k++) {
                            JSONObject jo1 = ja1.getJSONObject(k);
                            final String link1 = jo1.getString("link");
                            View.OnClickListener clickListeners = new OnMultClickListener() {
                                @Override
                                public void onMultiClick(View v) {
                                    if (BaseUtil.isAppInstalled(getActivity(), "com.taobao.taobao")) {
                                        Intent intent2 = new Intent();
                                        intent2.setAction("android.intent.action.VIEW");
                                        Uri uri = Uri.parse(link1);
                                        intent2.setData(uri);
                                        startActivity(intent2);
                                    }
                                }
                            };
                            view.setOnClickListenerByIndex(clickListeners,k);
                        }
                        goods_type_laout.addView(view);
                        goods_type_laout.setVisibility(View.VISIBLE);
                    }
                }

                setListener();
            }
        }catch (Exception e){
            e.printStackTrace();
            Log.e("home2",e.toString()+"_"+e.getLocalizedMessage());
        }
    }

    //继承类方法结束

    //自定义方法开始
    /**
     *  初始化控件
     */
    private void initView(View v){
        //搜索组件
        search_layout = v.findViewById(R.id.home_search_control);
        search_msg_pic = v.findViewById(R.id.home_search_msg_pic);
        search_text_back = v.findViewById(R.id.search_text_back);
        search_search_pic = v.findViewById(R.id.home_search_search_pic);
        search_search_text = v.findViewById(R.id.home_search_search_text);

        //轮播图组件
        vp_layout = v.findViewById(R.id.home_rotation_control);
        vp_main = v.findViewById(R.id.vp_main);
        ll_main = v.findViewById(R.id.ll_main);

        //菜单组件
        menuButton_layout = v.findViewById(R.id.home_menu_bar_control);
        menuButtons[0] = v.findViewById(R.id.menu_bar_button1);
        menuButtons[1] = v.findViewById(R.id.menu_bar_button2);
        menuButtons[2] = v.findViewById(R.id.menu_bar_button3);
        menuButtons[3] = v.findViewById(R.id.menu_bar_button4);
        menuButtons[4] = v.findViewById(R.id.menu_bar_button5);
        menuButtons[5] = v.findViewById(R.id.menu_bar_button6);
        menuButtons[6] = v.findViewById(R.id.menu_bar_button7);
        menuButtons[7] = v.findViewById(R.id.menu_bar_button8);
        menuButtons[8] = v.findViewById(R.id.menu_bar_button9);
        menuButtons[9] = v.findViewById(R.id.menu_bar_button10);

        //广告位
        advertisement_layout = v.findViewById(R.id.home_advertisement_control);
        advertisement_pic = v.findViewById(R.id.home_advertisement_pic);

        //好货精选组件
        goods_type_laout = v.findViewById(R.id.goods_type_laout);

        //分类组件
        menu_listView = v.findViewById(R.id.menu_list_menu);
        recommendGoods = v.findViewById(R.id.fl_container);
    }

    private void initData(){
        try{
            DBManger dbManger = DBManger.getInstance(getActivity());
            Map<Integer, HomePageSave> homeCache = dbManger.select();
            if(homeCache==null){
                return;
            }else {
                HomePageSave adv = null;
                HomePageSave seller = null;
                HomePageSave cube = null;
                HomePageSave category = null;
                if(homeCache.containsKey(1)){
                    adv = homeCache.get(1);
                }
                if(homeCache.containsKey(2)) {
                    seller = homeCache.get(2);
                }
                if(homeCache.containsKey(3)) {
                    cube = homeCache.get(3);
                }
                if(homeCache.containsKey(4)) {
                    category = homeCache.get(4);
                }
                if(adv!=null){

                }
                if(seller!=null){

                }
                if(cube!=null){

                }
                if(category!=null){

                }
            }
        }catch (Exception e){
            e.printStackTrace();
            Log.e("home",e.toString());
        }
    }

    /**
     * 绑定控件响应事件
     */
    private void setListener(){
        try {
            //查询框事件绑定
            search_msg_pic.setClickable(true);
            search_msg_pic.setOnClickListener(clickListener);
            search_search_pic.setClickable(true);
            search_search_pic.setOnClickListener(clickListener);
            search_text_back.setClickable(true);
            search_text_back.setOnClickListener(clickListener);
            search_search_text.setClickable(true);
            search_search_text.setOnClickListener(clickListener);

            //轮播图事件绑定
            viewPageShowAdapter = new ViewPageShowAdapter(getActivity(),vp_main_list2_1,vp_main_url_list,fragmentResultData);
            vp_main.setAdapter(viewPageShowAdapter);
            vp_main.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset,int positionOffsetPixels) {}

                @Override
                public void onPageSelected(final int position) {
                    if(vp_main_list2_1.size()>0) {
                        int newPostion = position % vp_main_list2_1.size();
                        //取出postion的位置小圆点设置为true
                        ll_main.getChildAt(newPostion).setEnabled(true);
                        //把一个小圆点设置为false
                        ll_main.getChildAt(previousPosition).setEnabled(false);
                        previousPosition = newPostion;
                    }
                }

                @Override
                public void onPageScrollStateChanged(int state) {}
            });

            //菜单组件事件绑定
            for(int i = 0; i < menuButtons.length; i++){
                menuButtons[i].setClickable(true);
                menuButtons[i].setOnClickListener(clickListener);
            }

            //广告位事件绑定
            advertisement_pic.setOnClickListener(clickListener);

            classifyMenuLists.clear();
            classifyMenuLists.addAll(classifyMenuLists1);
            //分类组件事件绑定
            horizonMenuListAdapter = new HorizonMenuListAdapter(getActivity(),R.layout.item_textbutton,classifyMenuLists);
            menu_listView.setAdapter(horizonMenuListAdapter);
            menu_listView.scrollTo(hmindex);
            menu_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    menu_listView.scrollTo(position);
                    changeFragment(nowFragment,position);
                }
            });

            scrollView.setScanScrollChangedListener(new MyScorllView.ISmartScrollChangedListener() {
                @Override
                public void onScrolledToBottom() {
//                    GoodsListFragment goodsListFragment = (GoodsListFragment)nowFragment;
//                    goodsListFragment.scrollBottom();
                }

                @Override
                public void onScrolledToTop() {

                }
            });
            fragmentManager = getChildFragmentManager();
            changeFragment(nowFragment,hmindex);

        }catch (Exception e){
            Log.e("home_",e.toString());
            e.printStackTrace();
        }
    }

    private void changeFragment(Fragment fromFragment, int index){
        try {
            FragmentTransaction ft = fragmentManager.beginTransaction();
            Boolean isNew = false;
            Fragment fragment = fragmentManager.findFragmentByTag(classifyMenuLists.get(index).getUrl());
            if (fragment == null) {
                fragment = GoodsListFragment.getInstance(classifyMenuLists.get(index).getUrl());
                isNew = true;
            }
            if (nowFragment != fragment) {
                nowFragment = fragment;
            }
            if(fromFragment!=null) {
                ft.hide(fromFragment);
            }
            if (isNew) {
                ft.add(R.id.fl_container, fragment, classifyMenuLists.get(index).getUrl()).commit();
            } else {
                ft.show(fragment).commit();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void setData(String key){
        try {
            switch (key) {
                case "adv":
                    vp_main_list2_1.clear();
                    vp_main_list2_1.addAll(vp_main_list2);
                    vp_main_url_list.clear();
                    vp_main_url_list.addAll(vp_main_url_list1);
                    int item = Integer.MAX_VALUE / 2 - (Integer.MAX_VALUE / 2 % vp_main_list2_1.size());
                    vp_main.setCurrentItem(item);
                    ll_main.getChildAt(previousPosition).setEnabled(true);
                    break;
                case "category":
                    menu_classify_list.clear();
                    menu_classify_list.addAll(menu_classify_list1);
                    menu_classify_url_list.clear();
                    menu_classify_url_list.addAll(menu_classify_url_list1);
                    classifyMenuLists.clear();
                    classifyMenuLists.addAll(classifyMenuLists1);
                    break;
            }
        }catch (Exception e){
            e.printStackTrace();
            Log.e("home3",e.toString());
        }
    }

    //监听点击事件方法
    private View.OnClickListener clickListener = new OnMultClickListener() {
        @Override
        public void onMultiClick(View v) {
            try {
                switch (v.getId()) {
                    case R.id.home_search_msg_pic:
                        msg_Onclick();
                        break;
                    case R.id.home_search_search_pic:
                        search_Onclick();
                        break;
                    case R.id.home_search_search_text:
                        search_Onclick();
                        break;
                    case R.id.search_text_back:
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
                Log.e("home_onclick",e.toString());
            }
        }
    };



    //查询点击事件
    private void search_Onclick(){
        fragmentResultData.turnToActivity(SearchActivity.class,null);
    }

    //信息点击事件
    private void msg_Onclick(){
        Map<String,String> params = new HashMap<>();
        params.put("title","我的消息");
        params.put("type","message");
        params.put("keywords","");
        fragmentResultData.turnToActivity(GoodsListActivity.class,params);
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
    }

    private void getAllData(){


        Thread th1 = new Thread(new Runnable() {
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
                    Log.e("home4",e.toString()+"_"+e.getLocalizedMessage());
                }
            }
        });
        th1.start();
    }

}
