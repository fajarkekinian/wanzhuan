package com.froyo.playcity.chenzhou;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;

import butterknife.Bind;
import butterknife.ButterKnife;

public class FragmentUser extends Fragment {

@Override
public View onCreateView(LayoutInflater inflater, ViewGroup container,
		Bundle savedInstanceState) {
View view = inflater.inflate(R.layout.fragment_user, container, false);


	return view;
}


}
