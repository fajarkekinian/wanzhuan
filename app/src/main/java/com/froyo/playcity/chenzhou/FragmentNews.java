package com.froyo.playcity.chenzhou;

import android.content.Context;
import android.content.Intent;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.froyo.playcity.chenzhou.api.Api;

import com.froyo.playcity.chenzhou.bean.Banner;
import com.froyo.playcity.chenzhou.bean.News;
import com.froyo.view.CommonAdapter;
import com.froyo.view.CustomerViewPage;
import com.froyo.view.MyPullRefreshView;
import com.froyo.view.ViewHolder;
import com.squareup.picasso.Picasso;
import com.yalantis.phoenix.PullToRefreshView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.drakeet.materialdialog.MaterialDialog;
import retrofit.Callback;
import retrofit.Response;

public class FragmentNews extends Fragment {
	@Bind(R.id.news)
	ListView newsList;
	@Bind(R.id.no_data) TextView emptyView;
	@Bind(R.id.refresh)
	MyPullRefreshView materialRefreshLayout;

	private View view;
	private View newsHeader;
	private Context context;
	private List<View> slideData;
	CustomerViewPage viewPage;
	private CommonAdapter<News> mAdapter;
	private List<News> mDatas = new ArrayList<News>();
	private List<News> mGetDatas = new ArrayList<News>();
	private Api api;
	private MaterialDialog mMaterialDialog;
	private int netWork = 0;
	int page;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if(view == null) {
			view = inflater.inflate(R.layout.fragment_news, container, false);
			ButterKnife.bind(this, view);
			context = this.getActivity();
			newsHeader = LayoutInflater.from(context).inflate(R.layout.news_header, null);
			viewPage = (CustomerViewPage) newsHeader.findViewById(R.id.adslide);
			init();
		}
		return view;
	}

	private void init()
	{
		api = new Api();
		newsList.addHeaderView(newsHeader);
		mDatas = new ArrayList<News>();
		mGetDatas = new ArrayList<News>();
		mAdapter = new CommonAdapter<News>(context, mDatas, R.layout.news_item){

			@Override
			public void convert(ViewHolder helper, News item) {
				helper.setText(R.id.name, item.getTitle());
				helper.setText(R.id.desc, item.getSummary());
				helper.setImageByUrl(R.id.img, item.getImg(),150,150);
			}
		};
		newsList.setAdapter(mAdapter);
		newsList.setEmptyView(emptyView);
		bindAction();
		showToast();
		setListAction();
		getSlideData();
		getListData();
	}

	private void setListAction()
	{
		//添加上拉加载更多
		newsList.setOnScrollListener(new AbsListView.OnScrollListener() {
			//AbsListView view 这个view对象就是listview
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
					if (view.getLastVisiblePosition() == view.getCount() - 1) {
						getListData();
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
						mDatas = new ArrayList<News>();
						page = 0;
						getListData();
						Log.d("tag", "fresh data");

					}
				}, 30);
			}
		});

		

	}

	private void bindAction()
	{
		newsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
				News news = mDatas.get(i - 1);
				Intent intent = new Intent();
				intent.setClass(context, NewsActivity.class);
				intent.putExtra("id", news.getId());
				context.startActivity(intent);
			}
		});
	}

	private void getSlideData() {
		netWork++;
		api.getBanners(new Callback<List<Banner>>() {
			@Override
			public void onResponse(Response<List<Banner>> response) {
				slideData = new ArrayList<>();
				if(response.body() == null)
					return;

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
		materialRefreshLayout.setRefreshing(false);
		materialRefreshLayout.setIsOnLoadingMore(false);
		if(netWork>0)
		{
			return;
		}

		mMaterialDialog.dismiss();
	}
	private void getListData()
	{
		netWork++;
		api.getNews(new Callback<List<News>>() {

			@Override
			public void onResponse(Response<List<News>> response) {
				List<News> news = response.body();
				mDatas.addAll(news);
				mGetDatas.addAll(news);
				Log.d("tag","mget data");
				mAdapter.notifyDataSetChanged();
				netWork--;
				page++;
				closeToast();
			}

			@Override
			public void onFailure(Throwable t) {
				netWork--;
				 mDatas = mGetDatas;
				mAdapter.notifyDataSetChanged();
				t.printStackTrace();
				closeToast();
			}
		});




	}

}
