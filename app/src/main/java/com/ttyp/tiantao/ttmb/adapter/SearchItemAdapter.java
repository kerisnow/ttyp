package com.ttyp.tiantao.ttmb.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ttyp.tiantao.R;
import com.ttyp.tiantao.ttmb.entity.SearchItem;

import java.util.List;

public class SearchItemAdapter extends BaseAdapter {
    Context context;
    private int resourceId;
    SearchItem model;
    List<SearchItem> list;

    public SearchItemAdapter(@NonNull Context context, int resourceId,  List<SearchItem> list) {
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
    public SearchItem getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        ViewHolder viewHolder;
        if(convertView == null){
            view = LayoutInflater.from(context).inflate(resourceId,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.associationText = view.findViewById(R.id.search_association_text);
            view.setTag(viewHolder);
        }else{
            view = convertView;
            viewHolder = (ViewHolder)view.getTag();
        }
        viewHolder.associationText.setText(model.getSearchAssociation());
        return view;
    }

    private class ViewHolder{
        TextView associationText;
    }
}
