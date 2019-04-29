package com.ttyp.tiantao.assembly.myTextView;

import android.content.Context;
import android.graphics.Paint;
import android.util.AttributeSet;

public class OldPricTextView extends android.support.v7.widget.AppCompatTextView {

    public OldPricTextView(Context context) {
        super(context);
        paint();
    }

    public OldPricTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint();
    }

    public OldPricTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint();
    }

    private void paint(){
        getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
    }
}
