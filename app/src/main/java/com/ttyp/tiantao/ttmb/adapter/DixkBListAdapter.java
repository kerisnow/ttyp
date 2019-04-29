package com.ttyp.tiantao.ttmb.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jaychan.view.MultipleTextView;
import com.ttyp.tiantao.R;
import com.ttyp.tiantao.ttmb.entity.DIXKBList;

import java.util.ArrayList;
import java.util.List;

public class DixkBListAdapter extends BaseAdapter {
    private Context context;
    private int resourceId;
    DIXKBList model;
    private List<DIXKBList> list = new ArrayList<>();
    public DixkBListAdapter(@NonNull Context context,int resourceId,List<DIXKBList> list) {
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
    public DIXKBList getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        model = getItem(position);
        View view;
        final ViewHolder viewHolder;
        if(convertView == null){
            view = LayoutInflater.from(context).inflate(resourceId,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.textView1 = view.findViewById(R.id.item_dixk_b_text1);
            viewHolder.textView2 = view.findViewById(R.id.item_dixk_b_text2);
            viewHolder.multipleTextView = view.findViewById(R.id.item_dixk_b_mtext1);
            view.setTag(viewHolder);
        }else {
            view = convertView;
            viewHolder = (ViewHolder)view.getTag();
        }
        viewHolder.textView1.setText(model.getTypeName());
        viewHolder.textView2.setText(model.getCreateTime());
        viewHolder.multipleTextView.setContentText(model.getPice());

        return view;
    }

    class ViewHolder{
        TextView textView1;
        TextView textView2;
        MultipleTextView multipleTextView;
    }
}
