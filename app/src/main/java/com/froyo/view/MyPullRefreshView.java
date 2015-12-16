package com.froyo.view;

import android.content.Context;
import android.util.AttributeSet;


import com.yalantis.phoenix.PullToRefreshView;

/**
 * Created by Administrator on 2015/12/16.
 */
public class MyPullRefreshView extends PullToRefreshView {
    private OnLoadMoreListener mLoadListener;
    private boolean isLoadingMore = false;
    public MyPullRefreshView(Context context) {
        super(context);
    }

    public MyPullRefreshView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    public void onScroll() {
        // 用户设置了加载更多监听器，且到了最底部，并且是上拉操作，那么执行加载更多.
        if (mLoadListener != null && mLoadListener.isBottom() && isLoadingMore){
            isLoadingMore = true;
            mLoadListener.loadMore();
        }
    }

    public interface OnLoadMoreListener{
        public void loadMore();
        public boolean isBottom();
    }

    public void setOnLoadListener(OnLoadMoreListener onLoadListener)
    {
        this.mLoadListener = onLoadListener;
    }

    public void setIsOnLoadingMore(boolean isOnLoadingMore)
    {
        this.isLoadingMore = isLoadingMore;
    }


}
