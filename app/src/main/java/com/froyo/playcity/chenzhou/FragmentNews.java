package com.froyo.playcity.chenzhou;


import android.content.Intent;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.froyo.playcity.chenzhou.bean.Banner;
import com.froyo.playcity.chenzhou.bean.News;
import com.froyo.view.CommonAdapter;
import com.froyo.view.CustomerViewPage;

import com.froyo.view.ViewHolder;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;
import java.util.List;


import retrofit.Callback;
import retrofit.Response;

public class FragmentNews extends MyBaseFragment {



	@Override
	View getLayoutView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_news, container, false);
	}

	@Override
	void prepare() {
		mDatas = new ArrayList<News>();
		viewHeader = LayoutInflater.from(context).inflate(R.layout.news_header, null);
		viewPage = (CustomerViewPage) viewHeader.findViewById(R.id.adslide);
		bindAction();
	}

	@Override
	void initAdapter() {
		mAdapter = new CommonAdapter<News>(context, mDatas, R.layout.news_item){

			@Override
			public void convert(ViewHolder helper, News item) {
				helper.setText(R.id.name, item.getTitle());
				helper.setText(R.id.desc, item.getSummary());
				helper.setImageByUrl(R.id.img, item.getImg(),150,150);
			}
		};
	}




	private void bindAction()
	{
		myList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
				News news = (News) mDatas.get(i - 1);
				Intent intent = new Intent();
				intent.setClass(context, NewsActivity.class);
				intent.putExtra("id", news.getId());
				context.startActivity(intent);
			}
		});
	}

	protected void getSlideData() {
		beforeNetwork();
		api.getBanners(new Callback<List<Banner>>() {
			@Override
			public void onResponse(Response<List<Banner>> response) {
				slideViews = new ArrayList<>();
				if (response.body() == null)
					return;

				for (Banner banner : response.body()) {
					RelativeLayout bannerLine = (RelativeLayout) LayoutInflater.from(context).inflate(R.layout.banner_slides, null);
					if(banner.getImg() != null) {
						Picasso.with(context).load(banner.getImg()).into((ImageView) bannerLine.findViewById(R.id.img));
					}
					((TextView) bannerLine.findViewById(R.id.name)).setText(banner.getName());
					slideDatas.add(banner);
					bannerLine.setTag(slideViews.size());

					bannerLine.setOnClickListener(slideClick);
					slideViews.add(bannerLine);
				}
				viewPage.setViewPageViews(slideViews);
				afterNetwork();
			}

			@Override
			public void onFailure(Throwable t) {

				slideViews = new ArrayList<>();
				LinearLayout bannerLine = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.no_data, null);
				slideViews.add(bannerLine);
				viewPage.setViewPageViews(slideViews);
				afterNetwork();
			}
		},"news",0,5);

	}

	protected void getListData()
	{
		beforeNetwork();
		api.getNews(new Callback<List<News>>() {
			@Override
			public void onResponse(Response<List<News>> response) {
				List<News> news = response.body();
				if(news == null)
				{
					return;
				}
				if(page == 0)
				{
					mDatas.clear();
				}
				mDatas.addAll(news);
				mAdapter.notifyDataSetChanged();
				page++;
				afterNetwork();
			}

			@Override
			public void onFailure(Throwable t) {

				mAdapter.notifyDataSetChanged();
				t.printStackTrace();
				afterNetwork();
			}
		},page,20,"id DESC");
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
}
