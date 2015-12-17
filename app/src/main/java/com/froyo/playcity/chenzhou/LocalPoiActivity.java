package com.froyo.playcity.chenzhou;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.froyo.playcity.chenzhou.api.Api;
import com.froyo.playcity.chenzhou.bean.News;
import com.froyo.playcity.chenzhou.bean.Pio;
import com.froyo.view.CommonAdapter;
import com.froyo.view.MyPullRefreshView;
import com.froyo.view.ViewHolder;
import com.yalantis.phoenix.PullToRefreshView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.drakeet.materialdialog.MaterialDialog;
import retrofit.Callback;
import retrofit.Response;


public class LocalPoiActivity extends Activity {
    @Bind(R.id.pio_title)
    TextView pioTitle;
    @Bind(R.id.pios)
    ListView pioList;
    @Bind(R.id.no_data) TextView emptyView;
    @Bind(R.id.back)
    ImageView back;
    private int netWork = 0;
    private MaterialDialog mMaterialDialog;
    private Api api;
    private CommonAdapter<Pio> mAdapter;
    private List<Pio> mDatas ;

    private String type;

    int page = 0;

    @Bind(R.id.refresh)
    MyPullRefreshView materialRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_poi);
        ButterKnife.bind(this);
        init();
    }

    private String getMyTitle()
    {
        Resources res = getResources();
        switch(type)
        {
            case "movie":
                return res.getString(R.string.cinema);
            case "tea":
                return res.getString(R.string.teahouse);
            case "cosmetology":
                return res.getString(R.string.beauty);
            case "hotel":
                return res.getString(R.string.pub);
            case "agritainmen":
                return res.getString(R.string.farmhouse);
            case "ktv":
                return res.getString(R.string.ktv);
            case "restaurants":
                return res.getString(R.string.restaurant);
            case "photostudio":
                return res.getString(R.string.studio);
            case "shop":
                return res.getString(R.string.shop);
            case "hospital":
                return res.getString(R.string.hospital);
            case "pedicure":
                return res.getString(R.string.pedicure);

        }
        return "";
    }
    private void init()
    {
        bindAction();
        Intent intent = getIntent();
        type = intent.getStringExtra("type");
        pioTitle.setText(getMyTitle());
        api = new Api();
        mDatas = new ArrayList<Pio>();
        mAdapter = new CommonAdapter<Pio>(this, mDatas, R.layout.pio_item){

            @Override
            public void convert(ViewHolder helper, Pio item) {
                helper.setText(R.id.name, item.getName());
                helper.setText(R.id.address, item.getAddress());

            }
        };
        pioList.setAdapter(mAdapter);
        pioList.setEmptyView(emptyView);
        showToast();
        setListData();
        setRefreshAndLoadMore();
    }

    protected  void setRefreshAndLoadMore()
    {
//添加上拉加载更多
        pioList.setOnScrollListener(new AbsListView.OnScrollListener() {
            //AbsListView view 这个view对象就是listview
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    if (view.getLastVisiblePosition() == view.getCount() - 1) {
                        setListData();
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
                        setListData();
                        Log.d("tag", "fresh data");

                    }
                }, 30);
            }
        });

    }
    private void bindAction()
    {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void showToast()
    {
        mMaterialDialog = new MaterialDialog(this);
        View view = LayoutInflater.from(this)
                .inflate(R.layout.progress_bar,
                        null);
        ((TextView)view.findViewById(R.id.toast_msg)).setText(getResources().getString(R.string.waite_to_load));
        mMaterialDialog.setView(view).show();
    }

    private void closeToast()
    {
        materialRefreshLayout.setRefreshing(false);
        materialRefreshLayout.setIsOnLoadingMore(false);
        if(netWork>0)
        {
            return;
        }

        mMaterialDialog.dismiss();
    }
    private void setListData()
    {
        netWork++;
        JSONObject where = new JSONObject();
        try {
            where.put("dataType",type);

        api.getPios(where, page, 20, new Callback<List<Pio>>() {

            @Override
            public void onResponse(Response<List<Pio>> response) {
                List<Pio> pio = response.body();
                if(pio == null)
                    return;
                if(page == 0)
                {
                    mDatas.clear();
                }
                mDatas.addAll(pio);
                mAdapter.notifyDataSetChanged();
                netWork--;
                closeToast();
                page++;
            }

            @Override
            public void onFailure(Throwable t) {
                netWork--;
                t.printStackTrace();
                closeToast();
            }
        });
        } catch (JSONException e) {
            e.printStackTrace();
        }




    }
}
