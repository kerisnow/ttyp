package com.ttyp.tiantao.assembly.myTextView;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Layout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ttyp.tiantao.R;

public class GoodsListImageTextView extends LinearLayout {
    Context context;
    ImageView shop_type_logo;
    TextView goods_text1;
    TextView goods_text2;

    public GoodsListImageTextView(Context context,String imagepath,String text){
        super(context);
        this.context = context;
        LayoutInflater.from(context).inflate(R.layout.assembly_goods_text_image,this,true);
        shop_type_logo = (ImageView) findViewById(R.id.shop_type_logo);
        goods_text1 = (TextView) findViewById(R.id.goods_text_1);
        goods_text2 = (TextView) findViewById(R.id.goods_text_2);
        if(imagepath==null) {
            shop_type_logo.setVisibility(GONE);
        }else {
            Glide.with(context).asBitmap().load(imagepath).into(shop_type_logo);
        }
        setTextVIew(text);
    }

    public GoodsListImageTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        LayoutInflater.from(context).inflate(R.layout.assembly_goods_text_image,this,true);
        shop_type_logo = (ImageView) findViewById(R.id.shop_type_logo);
        goods_text1 = (TextView) findViewById(R.id.goods_text_1);
        goods_text2 = (TextView) findViewById(R.id.goods_text_2);

        TypedArray attributes = context.obtainStyledAttributes(attrs,R.styleable.GoodsListImageTextView);

        if(attributes!=null){
            int resource = attributes.getResourceId(R.styleable.GoodsListImageTextView_type_logo,-1);
            if(resource != -1){
                setShop_type_logo(null,resource);
            }

            String goodsText = attributes.getString(R.styleable.GoodsListImageTextView_goods_title_text);
            setTextVIew(goodsText);

            attributes.recycle();
        }

    }

    private void setTextVIew(final String goodsText){
        goods_text1.setText(goodsText);

        final ViewTreeObserver vto = goods_text1.getViewTreeObserver();
        vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                ViewTreeObserver vto2 = goods_text1.getViewTreeObserver();
                vto2.removeOnPreDrawListener(this);

                Layout layout = goods_text1.getLayout();

                int start = layout.getLineStart(0);
                int end = layout.getLineEnd(0);

                String firstString = goodsText.substring(start, end);

                String secondString = goodsText.replace(firstString, "");

                goods_text2.setText(secondString);

                return false;
            }

        });
    }

    public void setShop_type_logo(String url,Integer resource) {
        if(resource == -1){
            Glide.with(context).asBitmap().load(url).into(shop_type_logo);
        }else {
            Glide.with(context).asBitmap().load(resource).into(shop_type_logo);
        }
        this.shop_type_logo = shop_type_logo;
    }

    public void setGoodsText(String goodsText) {
        setTextVIew(goodsText);
    }

}
