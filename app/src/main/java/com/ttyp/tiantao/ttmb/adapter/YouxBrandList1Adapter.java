package com.ttyp.tiantao.ttmb.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ttyp.tiantao.R;
import com.ttyp.tiantao.assembly.myTextView.OldPricTextView;
import com.ttyp.tiantao.ttmb.entity.RecommendListEntry;

import java.util.ArrayList;
import java.util.List;

public class YouxBrandList1Adapter extends BaseAdapter {
    private Context context;
    private int resourceId;
    RecommendListEntry model;
    OnclickerRun onclickerRun;
    private List<RecommendListEntry> list = new ArrayList<>();
    public YouxBrandList1Adapter(@NonNull Context context, int resourceId, List<RecommendListEntry> list, OnclickerRun onclickerRun) {
        super();
        this.context = context;
        this.resourceId = resourceId;
        this.list = list;
        this.onclickerRun = onclickerRun;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public RecommendListEntry getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        model = getItem(position);
        View view;
        final ViewHolder viewHolder;
        if(convertView == null){
            view = LayoutInflater.from(context).inflate(resourceId,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.title_text = view.findViewById(R.id.brand_1_title_text);
            viewHolder.more_text = view.findViewById(R.id.brand_1_more_text);
            viewHolder.more_control = view.findViewById(R.id.brand_1_more_control);
            viewHolder.goods_1 = view.findViewById(R.id.brand_1_goods_1);
            viewHolder.goods_1_image = view.findViewById(R.id.brand_1_goods_1_image);
            viewHolder.goods_1_text = view.findViewById(R.id.brand_1_goods_1_text);
            viewHolder.goods_1_pric_now = view.findViewById(R.id.brand_1_goods_1_pric_now);
            viewHolder.goods_1_pric_old = view.findViewById(R.id.brand_1_goods_1_pric_old);
            viewHolder.goods_2 = view.findViewById(R.id.brand_1_goods_2);
            viewHolder.goods_2_image = view.findViewById(R.id.brand_1_goods_2_image);
            viewHolder.goods_2_text = view.findViewById(R.id.brand_1_goods_2_text);
            viewHolder.goods_2_pric_now = view.findViewById(R.id.brand_1_goods_2_pric_now);
            viewHolder.goods_2_pric_old = view.findViewById(R.id.brand_1_goods_2_pric_old);
            viewHolder.goods_3 = view.findViewById(R.id.brand_1_goods_3);
            viewHolder.goods_3_image = view.findViewById(R.id.brand_1_goods_3_image);
            viewHolder.goods_3_text = view.findViewById(R.id.brand_1_goods_3_text);
            viewHolder.goods_3_pric_now = view.findViewById(R.id.brand_1_goods_3_pric_now);
            viewHolder.goods_3_pric_old = view.findViewById(R.id.brand_1_goods_3_pric_old);
            view.setTag(viewHolder);
        }else {
            view = convertView;
            viewHolder = (ViewHolder)view.getTag();
        }
        viewHolder.title_text.setText(model.getFavorites_title());
        viewHolder.more_text.setText("更多商品 >");
        if(model.getRecommendList().size() == 3) {
            viewHolder.goods_1_text.setText(model.getRecommendList().get(0).getTitle());
            viewHolder.goods_1_pric_now.setText(model.getRecommendList().get(0).getCouponPrice() + "");

            viewHolder.goods_2_text.setText(model.getRecommendList().get(1).getTitle());
            viewHolder.goods_2_pric_now.setText(model.getRecommendList().get(1).getCouponPrice() + "");

            viewHolder.goods_3_text.setText(model.getRecommendList().get(2).getTitle());
            viewHolder.goods_3_pric_now.setText(model.getRecommendList().get(2).getCouponPrice() + "");
            //加载网络图片
            Glide.with(context).asBitmap().load(model.getRecommendList().get(0).getPic()).into(viewHolder.goods_1_image);
            Glide.with(context).load(model.getRecommendList().get(1).getPic()).into(viewHolder.goods_2_image);
            Glide.with(context).load(model.getRecommendList().get(2).getPic()).into(viewHolder.goods_3_image);

            //设置原价
            viewHolder.goods_1_pric_old.setText(model.getRecommendList().get(0).getPrice());
            viewHolder.goods_2_pric_old.setText(model.getRecommendList().get(0).getPrice());
            viewHolder.goods_3_pric_old.setText(model.getRecommendList().get(0).getPrice());
        }



        //设置监听事件
        viewHolder.more_control.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onclickerRun.moreOnclick(getItem(position).getId(),position);
            }
        });

        if(model.getRecommendList().size() == 3) {
            viewHolder.goods_1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onclickerRun.goodsOnclick(getItem(position).getRecommendList().get(0).getNumIid(),position);
                }
            });

            viewHolder.goods_2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onclickerRun.goodsOnclick(getItem(position).getRecommendList().get(1).getNumIid(),position);
                }
            });

            viewHolder.goods_3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onclickerRun.goodsOnclick(getItem(position).getRecommendList().get(2).getNumIid(),position);
                }
            });
        }

        return view;
    }

    public interface OnclickerRun{
        void goodsOnclick(String id,int index);
        void moreOnclick(int id,int index);
    }

    class ViewHolder{
        TextView title_text;
        TextView more_text;
        FrameLayout more_control;

        LinearLayout goods_1;
        ImageView goods_1_image;
        TextView goods_1_text;
        TextView goods_1_pric_now;
        OldPricTextView goods_1_pric_old;

        LinearLayout goods_2;
        ImageView goods_2_image;
        TextView goods_2_text;
        TextView goods_2_pric_now;
        OldPricTextView goods_2_pric_old;

        LinearLayout goods_3;
        ImageView goods_3_image;
        TextView goods_3_text;
        TextView goods_3_pric_now;
        OldPricTextView goods_3_pric_old;
    }
}
