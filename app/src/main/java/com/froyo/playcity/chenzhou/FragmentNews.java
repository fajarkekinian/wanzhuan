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
import android.widget.ListView;
import android.widget.Toast;

import com.froyo.playcity.chenzhou.api.Api;
import com.froyo.playcity.chenzhou.bean.Act;
import com.froyo.playcity.chenzhou.bean.News;
import com.froyo.view.CommonAdapter;
import com.froyo.view.CustomerViewPage;
import com.froyo.view.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.Response;

public class FragmentNews extends Fragment {
	@Bind(R.id.news)
	ListView newsList;
	private View view;
	private View newsHeader;
	private Context context;
	private List<View> slideData;
	CustomerViewPage viewPage;
	private CommonAdapter<News> mAdapter;
	private List<News> mDatas = new ArrayList<News>();
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_news, container, false);
		ButterKnife.bind(this, view);
		context = this.getActivity();
		newsHeader = LayoutInflater.from(context).inflate(R.layout.news_header, null);
		viewPage = (CustomerViewPage)newsHeader.findViewById(R.id.adslide);
		init();
		return view;
	}

	private void init()
	{
		initSlideData();
		newsList.addHeaderView(newsHeader);
		mDatas = new ArrayList<News>();
		mAdapter = new CommonAdapter<News>(context, mDatas, R.layout.news_item){

			@Override
			public void convert(ViewHolder helper, News item) {
				helper.setText(R.id.name, item.getTitle());
				helper.setText(R.id.desc, item.getSummary());
				helper.setImageByUrl(R.id.img,item.getImg().getUrl());
			}
		};
		newsList.setAdapter(mAdapter);
		bindAction();
		setListData();
	}

	private void bindAction()
	{
		newsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
				News news = mDatas.get(i-1);
				Intent intent = new Intent();
				intent.setClass(context, NewsActivity.class);
				intent.putExtra("id",news.getId());
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
		api.getNews(new Callback<List<News>>() {

			@Override
			public void onResponse(Response<List<News>> response) {
				List<News> news = response.body();
				mDatas.addAll(news);
				Log.d("tag", news.toString());
				mAdapter.notifyDataSetChanged();
			}

			@Override
			public void onFailure(Throwable t) {
				t.printStackTrace();
			}
		});




	}

}
