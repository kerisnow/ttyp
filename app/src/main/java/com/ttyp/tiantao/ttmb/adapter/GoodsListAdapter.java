package com.ttyp.tiantao.ttmb.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ttyp.tiantao.R;
import com.ttyp.tiantao.ttmb.entity.GoodsListItem;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class GoodsListAdapter extends BaseAdapter {
    private Context context;
    private int resouceId;
    GoodsListItem model;
    List<GoodsListItem> list = new ArrayList();

    public GoodsListAdapter(@NonNull Context context, int resouceId, List<GoodsListItem> list) {
        this.context = context;
        this.resouceId = resouceId;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public GoodsListItem getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        model = getItem(position);
        View view ;
        final ViewHolder viewHolder;
        if(convertView == null){
            view = LayoutInflater.from(context).inflate(resouceId,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.goodsImage = view.findViewById(R.id.goods_list_good_image);
            viewHolder.goodstitle = view.findViewById(R.id.goods_list_title_text_image);
            viewHolder.distribution = view.findViewById(R.id.goods_list_fenxiao_control);
            viewHolder.offset = view.findViewById(R.id.goods_list_fenxiao_offset);
            viewHolder.earn = view.findViewById(R.id.goods_list_fenxiao_earn);
            viewHolder.price_now = view.findViewById(R.id.goods_list_price_now);
            viewHolder.price_old = view.findViewById(R.id.goods_list_price_old);
            viewHolder.shopname = view.findViewById(R.id.goods_list_shopname);
            viewHolder.shopaddress = view.findViewById(R.id.goods_list_address);
            viewHolder.addressImage=  view.findViewById(R.id.goods_list_address_image);
            view.setTag(viewHolder);
        }else{
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }

        Glide.with(context).asBitmap().load(model.getGoodsImage()).into(viewHolder.goodsImage);
        viewHolder.goodstitle.setText(model.getGoodsTitle());
        viewHolder.distribution.setVisibility((model.getIsGoodsoffset()||model.getIsGoodsearn()) ? View.VISIBLE:View.INVISIBLE);
        viewHolder.offset.setText(model.getGoodsoffset() + "元");
        viewHolder.offset.setVisibility(model.getIsGoodsoffset()?View.VISIBLE:View.INVISIBLE);
        viewHolder.earn.setText(model.getGoodsearn() + "元");
        viewHolder.earn.setVisibility(model.getIsGoodsearn()?View.VISIBLE:View.INVISIBLE);
        String price = model.getPrice_now();

        BigDecimal bg = new BigDecimal(price);
        double f1 = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

        int a = (int) (f1 * 100);
        int p_n = a / 100;
        int p_m = a % 100;
        viewHolder.price_now.setText(Html.fromHtml("￥"+"<big>"+p_n+"</big>"+"."+p_m));
        viewHolder.price_old.setText("￥"+model.getPrice_old());
        viewHolder.shopname.setText(model.getShopname());
        viewHolder.shopaddress.setText(model.getShopaddress());
        viewHolder.shopaddress.setVisibility(model.getIsShopaddress()?View.VISIBLE:View.INVISIBLE);
        viewHolder.addressImage.setVisibility(model.getIsShopaddress()?View.VISIBLE:View.INVISIBLE);
        return view;
    }

    private class ViewHolder {
        ImageView goodsImage;
        TextView goodstitle;
        LinearLayout distribution;
        TextView offset;
        TextView earn;
        TextView price_now;
        TextView price_old;
        TextView shopname;
        TextView shopaddress;
        ImageView addressImage;
    }
}
