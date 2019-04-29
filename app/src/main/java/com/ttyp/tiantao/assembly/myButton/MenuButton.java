package com.ttyp.tiantao.assembly.myButton;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.annotation.Dimension;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ttyp.tiantao.R;
import com.ttyp.tiantao.ttmb.template.URLValue;

public class MenuButton extends LinearLayout {
    ImageView imageView;
    TextView textView;
    Context context;

    public MenuButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        LayoutInflater.from(context).inflate(R.layout.assembly_menu_button,this,true);
        imageView = (ImageView) findViewById(R.id.menu_button_image);
        textView = (TextView) findViewById(R.id.menu_button_text);

        TypedArray attributes = context.obtainStyledAttributes(attrs,R.styleable.MenuButton);

        if(attributes!=null){
            boolean buttonImageVisible = attributes.getBoolean(R.styleable.MenuButton_button_image_visible,true);
            if(buttonImageVisible){
                imageView.setVisibility(VISIBLE);
            }else{
                imageView.setVisibility(INVISIBLE);
            }

            String buttonText = attributes.getString(R.styleable.MenuButton_button_text);
            if(!TextUtils.isEmpty(buttonText)){
                textView.setText(buttonText);
                int buttonTextColor = attributes.getColor(R.styleable.MenuButton_button_text_color, Color.BLACK);
                textView.setTextColor(buttonTextColor);
            }

            int buttonImageDrawable = attributes.getResourceId(R.styleable.MenuButton_button_image_src,-1);
            if(buttonImageDrawable != -1){
                imageView.setImageResource(buttonImageDrawable);
            }
            float buttonTextSize = attributes.getDimension(R.styleable.MenuButton_button_text_size,0);
            if(buttonTextSize > 0) {
                textView.getPaint().setTextSize(buttonTextSize);
            }
            int a,b,c,d;
            a = attributes.getDimensionPixelSize(R.styleable.MenuButton_button_image_padding_left,0);
            b = attributes.getDimensionPixelSize(R.styleable.MenuButton_button_image_padding_top,0);
            c = attributes.getDimensionPixelSize(R.styleable.MenuButton_button_image_padding_right,0);
            d = attributes.getDimensionPixelSize(R.styleable.MenuButton_button_image_padding_bottom,0);
            if(a > 0 && c > 0 && d > 0) {
                imageView.setPadding(a,b,c,d);
            }
            attributes.recycle();
        }
    }

    public void setImageViewSrc(Integer resource){
        if(resource != null) {
            if(this.imageView==null){
                return;
            }
            Glide.with(context).asBitmap().load(resource).into(this.imageView);
        }
    }

    public void setImageViewSrc(String resource){
        if(resource != null) {
            if(this.imageView==null){
                return;
            }
            Glide.with(context).asBitmap().load(URLValue.DEF_URL + resource).into(this.imageView);
        }
    }

    public void setImageViewBitmap(Bitmap bitmap){
        if(bitmap!=null){
            this.imageView.setImageBitmap(bitmap);
        }
    }

    public void setImageViewVisible(Boolean isVisible){
        if(isVisible){
            this.imageView.setVisibility(VISIBLE);
        }else{
            this.imageView.setVisibility(INVISIBLE);
        }
    }

    public void setTextViewText(String text){
        this.textView.setText(text);
    }

    public void setTextViewSize(int index){

        this.textView.setTextSize(Dimension.DP,index);
    }

    public String getText(){
        return textView.getText().toString().trim();
    }

}
