package com.ttyp.tiantao.ttmb.util;

import android.view.View;

public abstract class OnMultClickListener implements View.OnClickListener {
//    private static final  int MIN_CLICK_DELAY_TIME = 500;
//    private static long lastClickTime ;
    public static Boolean isHaveClick = false;
    public abstract void onMultiClick(View v);

    @Override
    public void onClick(View v) {
        if(!isHaveClick){
            isHaveClick = true;
            onMultiClick(v);
            isHaveClick = false;
        }
    }
}
