package com.ttyp.tiantao.assembly.myGrid;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ttyp.tiantao.R;
import com.ttyp.tiantao.ttmb.template.URLValue;

import java.util.List;

public class MenuPictureGrid extends GridLayout {
    GridLayout gridLayout;
    ImageView[] imageViews = null;
    ImageView imageView1 = null;
    ImageView imageView2 = null;
    TextView titleView = null;

    Context context;

    public MenuPictureGrid(Context context, int grid_type, List<String> imageResources,String title,String titleImage) {
        super(context);
        this.context = context;
        try {
            View view = null;
            imageViews = new ImageView[grid_type];
            if(grid_type==1) {
                view = LayoutInflater.from(context).inflate(R.layout.assembly_menu_picture_grid1, this, true);
                imageViews[0] = view.findViewById(R.id.picgrid_image1);
            }else if (grid_type == 2) {
                view = LayoutInflater.from(context).inflate(R.layout.assembly_menu_picture_grid2, this, true);
                imageViews[0] = view.findViewById(R.id.picgrid_image1);
                imageViews[1] = view.findViewById(R.id.picgrid_image2);
            }else if (grid_type == 3) {
                view = LayoutInflater.from(context).inflate(R.layout.assembly_menu_picture_grid3, this, true);
                imageViews[0] = view.findViewById(R.id.picgrid_image1);
                imageViews[1] = view.findViewById(R.id.picgrid_image2);
                imageViews[2] = view.findViewById(R.id.picgrid_image3);
            }else if (grid_type == 4) {
                view = LayoutInflater.from(context).inflate(R.layout.assembly_menu_picture_grid4, this, true);
                imageViews[0] = view.findViewById(R.id.picgrid_image1);
                imageViews[1] = view.findViewById(R.id.picgrid_image2);
                imageViews[2] = view.findViewById(R.id.picgrid_image3);
                imageViews[3] = view.findViewById(R.id.picgrid_image4);
            }
            if(view==null){
                return;
            }
            imageView1 = view.findViewById(R.id.grid1_image_left);
            imageView2 = view.findViewById(R.id.grid1_image_right);
            titleView = view.findViewById(R.id.grid1_title);
            Glide.with(context).asBitmap().load(URLValue.DEF_URL+titleImage).into(imageView1);
            Glide.with(context).asBitmap().load(URLValue.DEF_URL+titleImage).into(imageView2);
            titleView.setText(title);
            for(int i=0;i<imageViews.length;i++){
                Glide.with(context).asBitmap().load(URLValue.DEF_URL+imageResources.get(i)).into(imageViews[i]);
            }

        }catch (Exception e){
            e.printStackTrace();
            Log.e("picGrid",e.toString());

        }
    }

    public ImageView getImageViewByIndex(int index){
        if(imageViews != null && imageViews.length > index) {
            return imageViews[index];
        }else{
            return null;
        }
    }


//    public void setImageViewByIndex()

    public boolean setOnClickListenerByIndex(OnClickListener listener,int index){
        if(imageViews != null && imageViews.length > index) {
            imageViews[index].setOnClickListener(listener);
            return true;
        }else{
            return false;
        }

    }

    public boolean setOnClickListenerByIndex(OnClickListener[] listeners){
        if(imageViews != null && imageViews.length > listeners.length - 1) {
            for(int i = 0; i < listeners.length;i++){
                imageViews[i].setOnClickListener(listeners[i]);
            }
            return true;
        }else{
            return false;
        }

    }
}
