package com.rc.framework.adapter;

import android.content.Context;

import com.rc.framework.ui.pulltorefresh.PullToRefreshBase;

import java.util.List;

/**
 * Description:
 * User: Caizemingg(Email:Caizemingg@163.com)
 * Date: 2015-02-10
 * Time: 09:16
 * ModifyDescri:
 * ModifyDate:
 */
public abstract class PageListAdapter<T> extends BaseListAdapter<T> {

    private PullToRefreshBase view;

    /**
     * 默认十条
     */
    private static final int DEFAULT_REQUEST_ITEM_COUNT = 10;

    /**
     * 每次请求的条数
     */
    private int requestItemCount = DEFAULT_REQUEST_ITEM_COUNT;

    private int currentPage = 0;

    public PageListAdapter(Context context, PullToRefreshBase view) {
        super(context);
//        if (view == null) {
//            throw new IllegalArgumentException("PullToRefreshBase can not be null.");
//        }
        this.view = view;
    }

    @Override
    public void setList(List<T> list) {
        super.setList(list);
        if (view == null) {
            return;
        }
        view.onRefreshComplete();
        if (list != null && list.size() < requestItemCount) {
            view.setMode(PullToRefreshBase.Mode.DISABLED);
        } else {
            view.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
        }
    }

    public void clear() {
        list = null;
    }

    public int getRequestItemCount() {
        return requestItemCount;
    }

    public void setRequestItemCount(int requestItemCount) {
        this.requestItemCount = requestItemCount;
    }

    public int getCurrentPage() {
        return getCount() / requestItemCount + 1;
    }
}
