package com.froyo.playcity.chenzhou;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.froyo.playcity.chenzhou.bean.Act;
import com.froyo.playcity.chenzhou.bean.Banner;
import com.froyo.view.CommonAdapter;
import com.froyo.view.CustomerViewPage;
import com.froyo.view.ViewHolder;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.Response;

public class FragmentHome extends MyBaseFragment{


    @Override
    View getLayoutView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    void prepare() {
        mDatas = new ArrayList<Act>();
        viewHeader = LayoutInflater.from(context).inflate(R.layout.news_header, null);
        viewPage = (CustomerViewPage) viewHeader.findViewById(R.id.adslide);
        bindAction();
    }

    @Override
    void initAdapter() {

        mDatas = new ArrayList<Act>();
        mAdapter = new CommonAdapter<Act>(context, mDatas, R.layout.hot_item) {
            @Override
            public void convert(ViewHolder helper, Act item) {
                helper.setText(R.id.hot_activity_name, item.getTitle());
                helper.setText(R.id.hot_activity_desc, item.getSummary());
                helper.setImageByUrl(R.id.hot_activity_img, item.getImg());
            }
        };
    }

    @Override
    void getListData() {
        beforeNetwork();
        api.getModels(new Callback<List<Act>>() {

            @Override
            public void onResponse(Response<List<Act>> response) {

                List<Act> acts = response.body();
                if (acts == null) {
                    return;
                }
                if (page == 0) {
                    mDatas.clear();
                }
                mDatas.addAll(acts);
                mAdapter.notifyDataSetChanged();
                afterNetwork();
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
                afterNetwork();
            }
        }, page, 20);
    }

    @Override
    void getSlideData() {
        beforeNetwork();
        api.getBanners(new Callback<List<Banner>>() {
            @Override
            public void onResponse(Response<List<Banner>> response) {
                slideViews = new ArrayList<>();
                for (Banner banner : response.body()) {

                    RelativeLayout bannerLine = (RelativeLayout) LayoutInflater.from(context).inflate(R.layout.banner_slides, null);
                    if(banner.getImg() != null)
                    {
                        Picasso.with(context).load(banner.getImg()).into((ImageView) bannerLine.findViewById(R.id.img));
                    }
                    slideDatas.add(banner);
                    bannerLine.setTag(slideViews.size());

                    bannerLine.setOnClickListener(slideClick);
                    ((TextView) bannerLine.findViewById(R.id.name)).setText(banner.getName());
                    slideViews.add(bannerLine);
                }
                viewPage.setViewPageViews(slideViews);
                afterNetwork();
            }

            @Override
            public void onFailure(Throwable t) {
                netWork--;
                slideViews = new ArrayList<>();
                LinearLayout bannerLine = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.no_data, null);
                slideViews.add(bannerLine);
                viewPage.setViewPageViews(slideViews);

            }
        },0,5);
    }
    View.OnClickListener slideClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Intent intent = new Intent();

            Banner banner = (Banner)slideDatas.get(Integer.parseInt(v.getTag().toString()));
            String model = banner.getModel();
            if(model.equals("news"))
            {
                intent.setClass(context, NewsActivity.class);
            }
            else
            {
                intent.setClass(context,ActActivity.class);
            }


            intent.putExtra("id",banner.getModel_id());
            context.startActivity(intent);
        }
    };
    protected void bindAction() {
        myList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Act act = (Act)mDatas.get(i - 1);
                Intent intent = new Intent();
                intent.setClass(context, ActActivity.class);
                intent.putExtra("id", act.getId());
                context.startActivity(intent);
            }
        });


    }
}