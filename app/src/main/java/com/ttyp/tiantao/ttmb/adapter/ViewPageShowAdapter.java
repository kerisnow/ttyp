package com.ttyp.tiantao.ttmb.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.ttyp.tiantao.R;
import com.ttyp.tiantao.ttmb.activity.goods.GoodsDetailActivity;
import com.ttyp.tiantao.ttmb.extendsparent.MyFragment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ViewPageShowAdapter extends PagerAdapter {
    Context context;
    List<String> urlList;
    List<String> imageList;
    MyFragment.FragmentResultData fragmentResultData;
    public ViewPageShowAdapter(Context context, List<String> imageList, List<String> urlList, MyFragment.FragmentResultData fragmentResultData){
        this.context = context;
        this.imageList = imageList;
        this.urlList = urlList;
        this.fragmentResultData = fragmentResultData;
    }

    /**
     * 判断view和object的对应关系
     */
    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    /**
     * 获得页面的总数
     */
    @Override
    public int getCount() {
        return Integer.MAX_VALUE;      // 因为需要无限循环，所以这里就不写imageList.size()
    }

    /**
     * 获得相应位置上的view
     * container  view的容器，其实就是viewpager自身
     * position   相应的位置
     */
    @Override
    public Object instantiateItem(ViewGroup container,final int position) {
        // 给container添加内容
        int index = -1;
        if(imageList.size()>0){
            index = position%imageList.size();
            View view = LayoutInflater.from(context).inflate(R.layout.item_viewpage_image,null);
            ImageView imageView = view.findViewById(R.id.viewpage_image);
            Glide.with(context).asBitmap().load(imageList.get(index)).into(imageView);
            if(fragmentResultData!=null) {
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(urlList!=null) {
                            int i = position % imageList.size();
                            Map<String, String> params = new HashMap<>();
                            params.put("goodsid", urlList.get(i));
                            fragmentResultData.turnToActivity(GoodsDetailActivity.class, params);
                        }
                    }
                });
            }
            container.addView(view);
            return view;
        }else {
            return null;
        }
    }

    /**
     * 销毁对应位置上的object
     */
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
        object = null;
    }
}
