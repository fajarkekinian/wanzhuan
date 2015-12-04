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
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.froyo.playcity.chenzhou.api.Api;
import com.froyo.playcity.chenzhou.bean.Act;
import com.froyo.playcity.chenzhou.bean.Banner;
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


public class FragmentHome extends Fragment{
	CustomerViewPage viewPage;
	@Bind(R.id.hot_act) ListView hotList;
	@Bind(R.id.no_data) TextView emptyView;


	private List<View> slideData;
	private List<Act> mDatas = new ArrayList<Act>();
	private View view;
	private Context context;
	private CommonAdapter<Act> mAdapter;
	private View indexHeader;
	private Api api;
	private MaterialDialog mMaterialDialog;

	private int netWork = 0;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if(view == null)
		{
			view = inflater.inflate(R.layout.fragment_home, container, false);
			ButterKnife.bind(this, view);
			context = this.getActivity();
			indexHeader = LayoutInflater.from(context).inflate(R.layout.news_header, null);
			viewPage = (CustomerViewPage)indexHeader.findViewById(R.id.adslide);

			init();
		}
		return view;
	}

	private void init()
	{
		api = new Api();

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
		hotList.setEmptyView(emptyView);

		showToast();
		bindAction();
		initSlideData();
		setData();
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

	private void setData()
	{
		netWork++;
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


}
