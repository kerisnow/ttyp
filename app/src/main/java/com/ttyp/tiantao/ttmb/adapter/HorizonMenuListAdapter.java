package com.ttyp.tiantao.ttmb.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ttyp.tiantao.R;
import com.ttyp.tiantao.ttmb.entity.ClassifyMenuList;

import java.util.ArrayList;
import java.util.List;

public class HorizonMenuListAdapter extends BaseAdapter {
    private Context context;
    private int resourceId;
    ClassifyMenuList model;
    private List<ClassifyMenuList> list = new ArrayList<>();
    private int focus = 0;

    public HorizonMenuListAdapter(@NonNull Context context, int resourceId, List<ClassifyMenuList> list) {
        super();
        this.context = context;
        this.resourceId = resourceId;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public ClassifyMenuList getItem(int position) {
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
        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(resourceId, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.textView1 = view.findViewById(R.id.textbutton);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        if(position == focus){
            viewHolder.textView1.setTextColor(context.getResources().getColor(R.color.red));
        }else {
            viewHolder.textView1.setTextColor(context.getResources().getColor(R.color.black));
        }
        viewHolder.textView1.setText(model.getText());

        return view;
    }

    public void setFocus(int index){
        focus = index;
        notifyDataSetChanged();
    }

    class ViewHolder {
        TextView textView1;
    }
}
