package com.froyo.playcity.chenzhou;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.froyo.playcity.chenzhou.api.Api;
import com.froyo.playcity.chenzhou.bean.Act;
import com.froyo.view.CommonAdapter;
import com.froyo.view.CustomerViewPage;
import com.froyo.view.ViewHolder;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.Response;


public class FragmentHome extends Fragment{
	CustomerViewPage viewPage;
	@Bind(R.id.hot_act) ListView hotList;


	private List<View> slideData;
	private List<Act> mDatas = new ArrayList<Act>();
	private View view;
	private Context context;
	private CommonAdapter<Act> mAdapter;
	private View indexHeader;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_home, container, false);

		ButterKnife.bind(this, view);
		context = this.getActivity();
		indexHeader = LayoutInflater.from(context).inflate(R.layout.news_header, null);
		viewPage = (CustomerViewPage)indexHeader.findViewById(R.id.adslide);

		init();

		return view;
	}

	private void init()
	{

		initSlideData();

		hotList.addHeaderView(indexHeader);
		mDatas = new ArrayList<Act>();
		mAdapter = new CommonAdapter<Act>(context, mDatas, R.layout.hot_item){
			@Override
			public void convert(ViewHolder helper, Act item) {
				helper.setText(R.id.hot_activity_name,item.getTitle());
				helper.setText(R.id.hot_activity_desc,item.getSummary());
				helper.setImageByUrl(R.id.hot_activity_img,item.getImg().getUrl());
			}
		};
		hotList.setAdapter(mAdapter);
		bindAction();
		setListData();
	}

	private void  bindAction()
	{
		hotList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
				Act act = mDatas.get(i-1);
				Intent intent = new Intent();
				intent.setClass(context,ActActivity.class);
				intent.putExtra("id",act.getId());
				context.startActivity(intent);
			}
		});
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

	private void setListData()
	{

		Api api = new Api();
		api.getModels(new Callback<List<Act>>() {

			@Override
			public void onResponse(Response<List<Act>> response) {

				List<Act> acts = response.body();
				mDatas.addAll(acts);
				Log.d("tag", acts.toString());
				mAdapter.notifyDataSetChanged();
			}

			@Override
			public void onFailure(Throwable t) {

				t.printStackTrace();
			}
		});



	}


}
