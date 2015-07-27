package com.rc.framework.adapter;

import android.widget.BaseExpandableListAdapter;

import com.rc.framework.ui.pulltorefresh.PullToRefreshBase;

import java.util.List;

/**
 * Description:
 * Author: Caizemingg(Email:Caizemingg@163.com)
 * Date: 2015-06-23 14:47
 */
public abstract class PageExpandListViewAdapter<T> extends BaseExpandableListAdapter {

    protected List<T> list;

    /**
     * 默认十条
     */
    private static final int DEFAULT_REQUEST_ITEM_COUNT = 10;


    private PullToRefreshBase pullToRefreshBase;

    /**
     * 每次请求的条数
     */
    private int requestItemCount = DEFAULT_REQUEST_ITEM_COUNT;

    private int currentPage;

    public PageExpandListViewAdapter(PullToRefreshBase pullToRefreshBase) {
        if (pullToRefreshBase == null) {
            throw new IllegalArgumentException("Param pullToRefreshBase can not be null!");
        }
        this.pullToRefreshBase = pullToRefreshBase;
    }

    @Override
    public int getGroupCount() {
        if (list != null) {
            return list.size();
        }
        return 0;
    }


    @Override
    public Object getGroup(int groupPosition) {
        if (list != null) {
            return list.get(groupPosition);
        }
        return null;
    }

    public void setList(List<T> list) {
        if (this.list != null && list != null) {
            this.list.addAll(list);
        } else {
            this.list = list;
        }

        if (pullToRefreshBase == null) {
            return;
        }
        pullToRefreshBase.onRefreshComplete();

        if (list == null) {
            pullToRefreshBase.setMode(PullToRefreshBase.Mode.DISABLED);
            return;
        }

        if (list != null && list.size() < requestItemCount) {
            pullToRefreshBase.setMode(PullToRefreshBase.Mode.DISABLED);
        } else {
            pullToRefreshBase.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
        }
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    public int getCurrentPage() {
        currentPage = getGroupCount() / requestItemCount + 1;
        return currentPage;
    }

    public int getRequestItemCount() {
        return requestItemCount;
    }
}
