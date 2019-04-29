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
import com.ttyp.tiantao.assembly.myTextView.OldPricTextView;
import com.ttyp.tiantao.R;
import com.ttyp.tiantao.ttmb.entity.BrandListItem;

import java.util.ArrayList;
import java.util.List;

public class YouxBrandListAdapter extends BaseAdapter {
    private Context context;
    private int resourceId;
    BrandListItem model;
    OnclickerRun onclickerRun;
    private List<BrandListItem> list = new ArrayList<>();
    public YouxBrandListAdapter(@NonNull Context context, int resourceId, List<BrandListItem> list,OnclickerRun onclickerRun) {
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
    public BrandListItem getItem(int position) {
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
            viewHolder.title_image = view.findViewById(R.id.brand_title_logo);
            viewHolder.title_text = view.findViewById(R.id.brand_title_text);
            viewHolder.more_text = view.findViewById(R.id.brand_more_text);
            viewHolder.more_control = view.findViewById(R.id.brand_more_control);
            viewHolder.goods_1 = view.findViewById(R.id.brand_goods_1);
            viewHolder.goods_1_image = view.findViewById(R.id.brand_goods_1_image);
            viewHolder.goods_1_text = view.findViewById(R.id.brand_goods_1_text);
            viewHolder.goods_1_pric_now = view.findViewById(R.id.brand_goods_1_pric_now);
            viewHolder.goods_1_pric_old = view.findViewById(R.id.brand_goods_1_pric_old);
            viewHolder.goods_2 = view.findViewById(R.id.brand_goods_2);
            viewHolder.goods_2_image = view.findViewById(R.id.brand_goods_2_image);
            viewHolder.goods_2_text = view.findViewById(R.id.brand_goods_2_text);
            viewHolder.goods_2_pric_now = view.findViewById(R.id.brand_goods_2_pric_now);
            viewHolder.goods_2_pric_old = view.findViewById(R.id.brand_goods_2_pric_old);
            viewHolder.goods_3 = view.findViewById(R.id.brand_goods_3);
            viewHolder.goods_3_image = view.findViewById(R.id.brand_goods_3_image);
            viewHolder.goods_3_text = view.findViewById(R.id.brand_goods_3_text);
            viewHolder.goods_3_pric_now = view.findViewById(R.id.brand_goods_3_pric_now);
            viewHolder.goods_3_pric_old = view.findViewById(R.id.brand_goods_3_pric_old);
            view.setTag(viewHolder);
        }else {
            view = convertView;
            viewHolder = (ViewHolder)view.getTag();
        }
        viewHolder.title_text.setText(model.getTitleText());
        viewHolder.more_text.setText(model.getMoreText()+" >");

        viewHolder.goods_1_text.setText(model.getGoodsOneText());
        viewHolder.goods_1_pric_now.setText(model.getGoodsOnePricNow()+"");

        viewHolder.goods_2_text.setText(model.getGoodsTwoText());
        viewHolder.goods_2_pric_now.setText(model.getGoodsTwoPricNow()+"");

        viewHolder.goods_3_text.setText(model.getGoodsThreeText());
        viewHolder.goods_3_pric_now.setText(model.getGoodsThreePricNow()+"");

        //加载网络图片
        Glide.with(context).load(model.getTitleImage()).into(viewHolder.title_image);
        Glide.with(context).load(model.getGoodsOneImage()).into(viewHolder.goods_1_image);
        Glide.with(context).load(model.getGoodsTwoImage()).into(viewHolder.goods_2_image);
        Glide.with(context).load(model.getGoodsThreeImage()).into(viewHolder.goods_3_image);

        //设置原价
        if(model.getGoodsOnePricOld() == 0){
            viewHolder.goods_1_pric_old.setVisibility(View.INVISIBLE);
        }else {
            viewHolder.goods_1_pric_old.setText(model.getGoodsOnePricOld() + "");
        }
        if(model.getGoodsTwoPricOld() == 0) {
            viewHolder.goods_2_pric_old.setVisibility(View.INVISIBLE);
        }else{
            viewHolder.goods_2_pric_old.setText(model.getGoodsTwoPricOld() + "");
        }
        if(model.getGoodsThreePricOld() == 0){
            viewHolder.goods_3_pric_old.setVisibility(View.INVISIBLE);
        }else {
            viewHolder.goods_3_pric_old.setText(model.getGoodsThreePricOld() + "");
        }

        //设置监听事件
        viewHolder.more_control.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onclickerRun.moreOnclick(getItem(position).getId());
            }
        });

        viewHolder.goods_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onclickerRun.goodsOnclick(getItem(position).getGoodsOneID());
            }
        });

        viewHolder.goods_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onclickerRun.goodsOnclick(getItem(position).getGoodsTwoID());
            }
        });

        viewHolder.goods_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onclickerRun.goodsOnclick(getItem(position).getGoodsThreeID());
            }
        });

        return view;
    }

    public interface OnclickerRun{
        void goodsOnclick(int id);
        void moreOnclick(int id);
    }

    class ViewHolder{
        ImageView title_image;
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
