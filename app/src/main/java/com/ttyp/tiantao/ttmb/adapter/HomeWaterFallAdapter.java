package com.ttyp.tiantao.ttmb.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ttyp.tiantao.R;
import com.ttyp.tiantao.assembly.myTextView.OldPricTextView;
import com.ttyp.tiantao.ttmb.entity.GoodsListItem;

import java.util.List;

public class HomeWaterFallAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private List<GoodsListItem> mData; //定义数据源
    private OnItemClickListener mItemClickListener;

    //定义构造方法，默认传入上下文和数据源
    public HomeWaterFallAdapter(Context context, List<GoodsListItem> data,OnItemClickListener listener) {
        mContext = context;
        mData = data;
        mItemClickListener = listener;
    }

    @Override  //将ItemView渲染进来，创建ViewHolder
    public MyViewHolder onCreateViewHolder(ViewGroup parent, final int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_fllayout, null);
        final MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override  //将数据源的数据绑定到相应控件上
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        MyViewHolder holder2 = (MyViewHolder) holder;
        GoodsListItem goodsListItem = mData.get(position);
        Glide.with(mContext).asBitmap().load(goodsListItem.getGoodsImage()).into(holder2.goodsImage);
        holder2.offset.setText(goodsListItem.getGoodsoffset());
        holder2.earn.setText(goodsListItem.getGoodsearn());
        holder2.price_n.setText("￥"+goodsListItem.getPrice_now());
        holder2.price_o.setText("￥"+goodsListItem.getPrice_old());
        holder2.sales.setText("销量"+goodsListItem.getPrice_old());
        holder2.shopsImage.setText(goodsListItem.getGoodsTitle());
        holder2.shopname.setText(goodsListItem.getShopname());
        holder2.shopadress.setText(goodsListItem.getShopaddress());
        if (mItemClickListener!=null) {
            holder2.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mItemClickListener.onItemClick(mData.get(position).getGoodsId());
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        if (mData != null) {
            return mData.size();
        }
        return 0;
    }

    public void setData(List<GoodsListItem> mData){
        this.mData = mData;
        notifyDataSetChanged();
    }



    //定义自己的ViewHolder，将View的控件引用在成员变量上
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView goodsImage;
        public TextView shopsImage;
        public LinearLayout fenxiao_control;
        public TextView offset;
        public TextView earn;
        public TextView price_n;
        public OldPricTextView price_o;
        public TextView sales;
        public TextView shopname;
        public TextView shopadress;

        public MyViewHolder(View itemView) {
            super(itemView);
            goodsImage = (ImageView) itemView.findViewById(R.id.goods_list_good_image);
            shopsImage = (TextView) itemView.findViewById(R.id.goods_list_title_text_image);
            fenxiao_control = (LinearLayout) itemView.findViewById(R.id.goods_list_fenxiao_control);
            offset = (TextView) itemView.findViewById(R.id.goods_list_fenxiao_offset);
            earn = (TextView) itemView.findViewById(R.id.goods_list_fenxiao_earn);
            price_n = (TextView) itemView.findViewById(R.id.goods_list_price_now);
            price_o = (OldPricTextView) itemView.findViewById(R.id.goods_list_price_old);
            sales = (TextView) itemView.findViewById(R.id.goods_list_sales);
            shopname = (TextView) itemView.findViewById(R.id.goods_list_shopname);
            shopadress = (TextView) itemView.findViewById(R.id.goods_list_address);
        }
    }

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }

    public interface OnItemClickListener{
        void onItemClick(long position);
    }
}

