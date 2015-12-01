package com.froyo.playcity.chenzhou;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import com.froyo.playcity.chenzhou.bean.Act;
import com.froyo.playcity.chenzhou.bean.News;
import com.froyo.view.CommonAdapter;
import com.froyo.view.CustomerViewPage;
import com.froyo.view.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class FragmentActivity extends Fragment {
    @Bind(R.id.act)
    ListView actList;
    private View view;
    private View activityHeader;
    private Context context;
    private List<View> slideData;
    CustomerViewPage viewPage;
    private CommonAdapter<Act> mAdapter;
    private List<Act> mDatas = new ArrayList<Act>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_activity, container, false);
        ButterKnife.bind(this, view);
        context = this.getActivity();
        activityHeader = LayoutInflater.from(context).inflate(R.layout.activity_header, null);
        viewPage = (CustomerViewPage) activityHeader.findViewById(R.id.adslide);
        init();
        return view;
    }

    private void init() {
        initSlideData();
        actList.addHeaderView(activityHeader);
        mDatas = new ArrayList<Act>();
        mAdapter = new CommonAdapter<Act>(context, mDatas, R.layout.hot_item) {

            @Override
            public void convert(ViewHolder helper, Act item) {
                helper.setText(R.id.hot_activity_name, item.getName());
                helper.setText(R.id.hot_activity_desc, item.getDesc());
				helper.setImageByUrl(R.id.hot_activity_img, item.getImg());
            }
        };
        actList.setAdapter(mAdapter);
        setListData();
    }

    private void initSlideData() {
        slideData = new ArrayList<>();
        ImageView imageView1 = new ImageView(context);
        ImageView imageView2 = new ImageView(context);
        ImageView imageView3 = new ImageView(context);
        ImageView imageView4 = new ImageView(context);
        ImageView imageView5 = new ImageView(context);
        imageView1.setBackgroundColor(Color.parseColor("#123456"));
        slideData.add(imageView1);
        imageView2.setBackgroundColor(Color.parseColor("#145826"));
        slideData.add(imageView2);
        imageView3.setBackgroundColor(Color.parseColor("#874592"));
        slideData.add(imageView3);
        imageView4.setBackgroundColor(Color.parseColor("#658415"));
        slideData.add(imageView4);
        imageView5.setBackgroundColor(Color.parseColor("#845163"));
        slideData.add(imageView5);
        viewPage.setViewPageViews(slideData);
    }

    private void setListData() {


        for (int i = 0; i < 6; i++) {
            Act act = new Act();
			act.setImg("http://pic.qiantucdn.com/58pic/16/73/95/63E58PICQh7_1024.jpg");
            act.setName("凤台棋牌室");
            act.setDesc("凤台棋牌室是比较大型的娱乐场所");
            mDatas.add(act);
        }
        mAdapter.notifyDataSetChanged();


    }

}
