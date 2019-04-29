package com.ttyp.tiantao.ttmb.activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;

import com.ttyp.tiantao.R;
import com.ttyp.tiantao.assembly.myBar.BottomBar;
import com.ttyp.tiantao.ttmb.activity.home.HomePageFragment;
import com.ttyp.tiantao.ttmb.activity.me.MeFragment;
import com.ttyp.tiantao.ttmb.activity.rechargecard.RechargeCardFragment;
import com.ttyp.tiantao.ttmb.activity.recommend.RecommendFragment;
import com.ttyp.tiantao.ttmb.entity.UserEntity;
import com.ttyp.tiantao.ttmb.template.FinalValue;
import com.ttyp.tiantao.ttmb.template.StaticValue;
import com.ttyp.tiantao.ttmb.util.BaseUtil;
import com.ttyp.tiantao.ttmb.util.GetJSON;

import java.util.Map;

public class MainActivity extends MyBaseActivity {
    FrameLayout frameLayout;
    BottomBar bottomBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initData();
        setListener();
//        loadWeb();
    }

    private void initView(){
        bottomBar = findViewById(R.id.main_bottomBar);
        frameLayout = findViewById(R.id.main_frameLayout);
    }

    private void initData(){
        StaticValue.MainButtionHeight = bottomBar.getLayoutParams().height;
        String path = getFilesDir().getAbsolutePath();
        String fileString = BaseUtil.ReadFile(path, FinalValue.LOCAL_FILE_NAME);
        if(fileString == null || "".equals(fileString)){
            StaticValue.USER = null;
        }else {
            Map<String, Object> userData = GetJSON.getInstance().getJSON(fileString);
            StaticValue.USER = new UserEntity(StaticValue.UID);
            StaticValue.USER.setPhone((String) userData.get("phone"));
            String name = (String) userData.get("nickname");
            if(name==null||"".equals(name)){
                StaticValue.USER.setPickName("用户"+(String) userData.get("phone"));
            }else {
                StaticValue.USER.setPickName(name);

            }
            StaticValue.USER.setHeadimage((String) userData.get("logo"));
            StaticValue.USER.setRechargeMoney((String) userData.get("money"));
            StaticValue.USER.setIntegral((int) userData.get("score"));
            StaticValue.USER.setIdentification((int) userData.get("type"));
        }
    }

    private void setListener(){
        try{

            bottomBar.setContainer(R.id.main_frameLayout)
                    .setTitleBeforeAndAfterColor("#afafaf", "#e43b3b")
                    .addItem(HomePageFragment.class,
                            "首页",
                            R.drawable.home,
                            R.drawable.home_o)
                    .addItem(RecommendFragment.class,
                            "优选",
                            R.drawable.shopping,
                            R.drawable.shopping_o)
                    .addItem(RechargeCardFragment.class,
                            "抵现卡",
                            R.drawable.card,
                            R.drawable.card_o)
                    .addItem(MeFragment.class,
                            "我的",
                            R.drawable.me,
                            R.drawable.me_o)
                    .setFirstChecked(0)
                    .build();

        }catch (Exception e){
            Log.e("mainActivity",e.toString());
        }
    }


}
