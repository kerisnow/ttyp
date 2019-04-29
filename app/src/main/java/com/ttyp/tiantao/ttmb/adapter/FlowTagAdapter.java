package com.ttyp.tiantao.ttmb.adapter;

import android.database.DataSetObservable;
import android.database.DataSetObserver;
import android.support.annotation.Nullable;

import com.volley.library.flowtag.adaper.BaseFlowAdapter;
import com.volley.library.flowtag.adaper.BaseTagHolder;

import java.util.List;

public class FlowTagAdapter extends BaseFlowAdapter {
    private final DataSetObservable mDataSetObservable = new DataSetObservable();
    public void registerDataSetObserver(DataSetObserver observer) {
        mDataSetObservable.registerObserver(observer);
    }

    public void unregisterDataSetObserver(DataSetObserver observer) {
        mDataSetObservable.unregisterObserver(observer);
    }

    /**
     * Notifies the attached observers that the underlying data has been changed
     * and any View reflecting the data set should refresh itself.
     */
    /**
     * Notifies the attached observers that the underlying data is no longer valid
     * or available. Once invoked this adapter is no longer valid and should
     * not report further data set changes.
     */
    public void notifyDataSetInvalidated() {
        mDataSetObservable.notifyInvalidated();
    }

    public FlowTagAdapter(int layoutResId, @Nullable List data) {
        super(layoutResId, data);
    }

    public FlowTagAdapter(@Nullable List data) {
        super(data);
    }

    public FlowTagAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseTagHolder baseTagHolder, Object o) {

    }


}
