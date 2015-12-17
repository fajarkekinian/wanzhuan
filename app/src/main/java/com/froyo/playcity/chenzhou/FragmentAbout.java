package com.froyo.playcity.chenzhou;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class FragmentAbout extends Fragment {
	private Context context;
	@Bind(R.id.app_version)
	TextView appVersion;


	@Override
public View onCreateView(LayoutInflater inflater, ViewGroup container,
		Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_about, container, false);
		context = this.getActivity();
	ButterKnife.bind(this,view);
		init();
	return view;
}

	public void init()
	{
		appVersion.setText(getVersionName());
	}


	private String getVersionName() {

		String versionName = BuildConfig.VERSION_NAME;
		return versionName;

	}


}
