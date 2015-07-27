package com.rc.framework.adapter;

import android.content.Context;
import android.widget.BaseAdapter;

import com.nostra13.universalimageloader.core.DisplayImageOptions;

import java.util.List;

public abstract class BaseListAdapter<T> extends BaseAdapter {

    protected List<T> list;

    protected Context context;

    protected DisplayImageOptions options;

    public BaseListAdapter(Context context) {
//        list = new ArrayList<T>();
        if (context == null) {
            throw new IllegalArgumentException("Context can not be null.");
        }
        this.context = context;
        this.options = new DisplayImageOptions.Builder().cacheOnDisk(true).cacheInMemory(true)
                .build();
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        if (list != null) {
            return list.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        if (this.list != null && list != null) {
            this.list.addAll(list);
        } else {
            this.list = list;
        }
    }

}
