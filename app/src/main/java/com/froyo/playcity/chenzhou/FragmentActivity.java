package com.froyo.playcity.chenzhou;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.froyo.playcity.chenzhou.api.Api;
import com.froyo.playcity.chenzhou.bean.Act;
import com.froyo.playcity.chenzhou.bean.Banner;
import com.froyo.playcity.chenzhou.bean.News;
import com.froyo.view.CommonAdapter;
import com.froyo.view.CustomerViewPage;
import com.froyo.view.ViewHolder;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.drakeet.materialdialog.MaterialDialog;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class FragmentActivity extends Fragment {
    @Bind(R.id.act)
    ListView actList;
    @Bind(R.id.no_data) TextView emptyView;
    private View view;
    private View activityHeader;
    private Context context;
    private List<View> slideData;
    CustomerViewPage viewPage;
    private CommonAdapter<Act> mAdapter;
    private List<Act> mDatas = new ArrayList<Act>();
    private Api api;
    private MaterialDialog mMaterialDialog;

    private int netWork = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(view == null) {
            view = inflater.inflate(R.layout.fragment_activity, container, false);
            ButterKnife.bind(this, view);
            context = this.getActivity();
            activityHeader = LayoutInflater.from(context).inflate(R.layout.activity_header, null);
            viewPage = (CustomerViewPage) activityHeader.findViewById(R.id.adslide);
            init();
        }
        return view;
    }

    private void init() {
        api = new Api();

        actList.addHeaderView(activityHeader);
        mDatas = new ArrayList<Act>();
        mAdapter = new CommonAdapter<Act>(context, mDatas, R.layout.hot_item) {

            @Override
            public void convert(ViewHolder helper, Act item) {
                helper.setText(R.id.hot_activity_name, item.getTitle());
                helper.setText(R.id.hot_activity_desc, item.getSummary());
				helper.setImageByUrl(R.id.hot_activity_img, item.getImg().getUrl());
            }
        };
        actList.setAdapter(mAdapter);
        showToast();
        initSlideData();
        setListData();
    }


    private void initSlideData() {
        netWork++;
        api.getBanners(new Callback<List<Banner>>() {
            @Override
            public void onResponse(Response<List<Banner>> response) {
                slideData = new ArrayList<>();
                for(Banner banner:response.body()){
                    RelativeLayout bannerLine =  (RelativeLayout)LayoutInflater.from(context).inflate(R.layout.banner_slides, null);
                    Picasso.with(context).load(banner.getImg().getUrl()).into((ImageView)bannerLine.findViewById(R.id.img));
                    ((TextView)bannerLine.findViewById(R.id.name)).setText(banner.getName());
                    slideData.add(bannerLine);
                }
                viewPage.setViewPageViews(slideData);
                netWork--;

                closeToast();
            }

            @Override
            public void onFailure(Throwable t) {
                netWork--;
                slideData = new ArrayList<>();
                LinearLayout bannerLine =  (LinearLayout)LayoutInflater.from(context).inflate(R.layout.no_data, null);
                slideData.add(bannerLine);
                viewPage.setViewPageViews(slideData);
                closeToast();
            }
        });

    }

    private void setListData() {
        Api api = new Api();
        api.getModels(new Callback<List<Act>>() {

            @Override
            public void onResponse(Response<List<Act>> response) {

                List<Act> acts = response.body();
                mDatas.addAll(acts);
                Log.d("tag", acts.toString());
                mAdapter.notifyDataSetChanged();
                netWork--;

                closeToast();
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
                netWork--;
                closeToast();
            }
        });

    }

    private void showToast()
    {
        mMaterialDialog = new MaterialDialog(context);
        View view = LayoutInflater.from(context)
                .inflate(R.layout.progress_bar,
                        null);
        ((TextView)view.findViewById(R.id.toast_msg)).setText(context.getResources().getString(R.string.waite_to_load));
        mMaterialDialog.setView(view).show();
    }

    private void closeToast()
    {
        if(netWork>0)
        {
            return;
        }

        mMaterialDialog.dismiss();
    }

}
