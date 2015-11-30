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
				helper.setText(R.id.name,item.getName());
				helper.setText(R.id.desc,item.getDesc());
//				helper.setImageByUrl(R.id.img,item.getImg());
			}
		};
		newsList.setAdapter(mAdapter);
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

	private void setListData()
	{


		for(int i=0;i<6;i++)
		{
			News news = new News();
//			news.setImg("http://pic.qiantucdn.com/58pic/16/73/95/63E58PICQh7_1024.jpg");
			news.setName("凤台棋牌室");
			news.setDesc("凤台棋牌室是比较大型的娱乐场所");
			mDatas.add(news);
		}
		mAdapter.notifyDataSetChanged();



	}

}
