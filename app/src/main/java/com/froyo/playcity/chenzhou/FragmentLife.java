package com.froyo.playcity.chenzhou;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;

public class FragmentLife extends Fragment {
	private View view;
	private Context context;
	@Bind(R.id.tea)
	LinearLayout tea;//茶楼

	@Bind(R.id.movie)
	LinearLayout movie;//影院

	@Bind(R.id.cosmetology)
	LinearLayout cosmetology;//美容

	@Bind(R.id.hotel)
	LinearLayout hotel;//酒店

	@Bind(R.id.agritainmen)
	LinearLayout agritainmen;//农家乐

	@Bind(R.id.ktv)
	LinearLayout ktv;//ktv

	@Bind(R.id.restaurants)
	LinearLayout restaurants;//餐厅

	@Bind(R.id.shop)
	LinearLayout shop;//便利店


	@Bind(R.id.hospital)
	LinearLayout hospital;//医院

	@Bind(R.id.photostudio)
	LinearLayout photostudio;//医院

	@Bind(R.id.pedicure)
	LinearLayout pedicure;

@Override
public View onCreateView(LayoutInflater inflater, ViewGroup container,
		Bundle savedInstanceState) {
		context = getActivity();
	if(view == null) {
		view = inflater.inflate(R.layout.fragment_life, container, false);

		ButterKnife.bind(this, view);
		init();
	}
	return view;
}

	private void init()
	{
		movie.setOnClickListener(pioClick);
		tea.setOnClickListener(pioClick);
		cosmetology.setOnClickListener(pioClick);
		hotel.setOnClickListener(pioClick);
		agritainmen.setOnClickListener(pioClick);
		ktv.setOnClickListener(pioClick);
		restaurants.setOnClickListener(pioClick);
		photostudio.setOnClickListener(pioClick);
		shop.setOnClickListener(pioClick);
		hospital.setOnClickListener(pioClick);
		pedicure.setOnClickListener(pioClick);


	}

	private View.OnClickListener pioClick = new View.OnClickListener(){
		@Override
		public void onClick(View view) {
			Intent intent = new Intent();
			intent.setClass(context, LocalPoiActivity.class);
			intent.putExtra("type", view.getTag().toString());
			startActivity(intent);
		}
	};
}
