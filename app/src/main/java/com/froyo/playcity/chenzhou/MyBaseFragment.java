package com.froyo.playcity.chenzhou;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.froyo.playcity.chenzhou.api.Api;
import com.froyo.playcity.chenzhou.bean.Act;
import com.froyo.playcity.chenzhou.bean.Banner;
import com.froyo.view.CommonAdapter;
import com.froyo.view.CustomerViewPage;
import com.froyo.view.MyPullRefreshView;
import com.froyo.view.ViewHolder;
import com.squareup.picasso.Picasso;
import com.umeng.analytics.MobclickAgent;
import com.yalantis.phoenix.PullToRefreshView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.drakeet.materialdialog.MaterialDialog;
import retrofit.Callback;
import retrofit.Response;

/**
 * Created by Administrator on 2015/12/16.
 */
public abstract class MyBaseFragment extends android.support.v4.app.Fragment {
    CustomerViewPage viewPage;
    @Bind(R.id.mylist)
    ListView myList;

    @Bind(R.id.no_data)
    TextView emptyView;


    protected List<View> slideData;
    protected List mDatas;
    protected View view;
    protected Context context;
    protected CommonAdapter mAdapter;
    protected View viewHeader;
    protected Api api;
    protected MaterialDialog mMaterialDialog;
    int page = 0;

        @Bind(R.id.refresh)
        MyPullRefreshView materialRefreshLayout;

    protected int netWork = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view == null) {
            view = getLayoutView(inflater, container, savedInstanceState);
            ButterKnife.bind(this, view);
            context = this.getActivity();


            init();
        }
        return view;
    }
    abstract View getLayoutView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState);

    abstract void prepare();
    abstract void initAdapter();
    abstract void getListData();
    abstract void getSlideData();
    private void init() {
        prepare();
        api = new Api();

        myList.addHeaderView(viewHeader);
        mDatas = new ArrayList<Act>();
        initAdapter();
        myList.setAdapter(mAdapter);
        myList.setEmptyView(emptyView);


        setRefreshAndLoadMore();
        getSlideData();
        getListData();
    }
    protected void beforeNetwork()
    {
        showToast();
        netWork++;
    }

    protected  void afterNetwork()
    {
        netWork--;
        closeToast();
    }


    protected  void setRefreshAndLoadMore()
    {
//添加上拉加载更多
        myList.setOnScrollListener(new AbsListView.OnScrollListener() {
            //AbsListView view 这个view对象就是listview
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    if (view.getLastVisiblePosition() == view.getCount() - 1) {
                        getListData();
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
            }
        });

        //下拉刷新
        materialRefreshLayout.setOnRefreshListener(new PullToRefreshView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                materialRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        page = 0;
                        getListData();
                        Log.d("tag", "fresh data");

                    }
                }, 30);
            }
        });

    }

    private void showToast() {
        if(netWork == 0) {


            mMaterialDialog = new MaterialDialog(context);
            View view = LayoutInflater.from(context)
                    .inflate(R.layout.progress_bar,
                            null);
            ((TextView) view.findViewById(R.id.toast_msg)).setText(context.getResources().getString(R.string.waite_to_load));

            mMaterialDialog.setView(view).show();
        }
    }

    private void closeToast() {
        materialRefreshLayout.setRefreshing(false);
        materialRefreshLayout.setIsOnLoadingMore(false);
        if (netWork > 0) {
            return;
        }

        mMaterialDialog.dismiss();
    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(this.getClass().getName()); //统计页面
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(this.getClass().getName());
    }


}