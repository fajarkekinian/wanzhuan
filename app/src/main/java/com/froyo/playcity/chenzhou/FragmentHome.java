package com.froyo.playcity.chenzhou;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.froyo.playcity.chenzhou.bean.Act;
import com.froyo.view.CommonAdapter;
import com.froyo.view.CustomerViewPage;
import com.froyo.view.ViewHolder;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


public class FragmentHome extends Fragment{
	@Bind(R.id.root_line) LinearLayout rootLinearLayout;
	@Bind(R.id.adslide) CustomerViewPage viewPage;


	private List<View> slideData;
	private List<Act> mDatas = new ArrayList<Act>();
	private View view;
	private Context context;
	private CommonAdapter<Act> mAdapter;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_home, container, false);
		ButterKnife.bind(this, view);
		context = this.getActivity();

		initSlideData();
		setListData();
		return view;
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
		mDatas = new ArrayList<Act>();

		for(int i=0;i<6;i++)
		{



			LinearLayout linearLayout = (LinearLayout)LayoutInflater.from(context).inflate(R.layout.hot_item,null);
			((TextView)linearLayout.findViewById(R.id.hot_activity_desc)).setText("凤台棋牌室是比较大型的娱乐场所");
			((TextView)linearLayout.findViewById(R.id.hot_activity_name)).setText("凤台棋牌室");
			ImageView img = (ImageView)linearLayout.findViewById(R.id.hot_activity_img);
			Picasso.with(context).load("http://pic.qiantucdn.com/58pic/16/73/95/63E58PICQh7_1024.jpg").into(img);

			rootLinearLayout.addView(linearLayout);
		}

	}


}
