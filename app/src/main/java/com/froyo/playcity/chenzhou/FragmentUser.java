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
	@Bind(R.id.test)
	WebView testWeb;
	@Bind(R.id.testBut)
	Button testButton;
@Override
public View onCreateView(LayoutInflater inflater, ViewGroup container,
		Bundle savedInstanceState) {
View view = inflater.inflate(R.layout.fragment_user, container, false);
	ButterKnife.bind(this, view);
	init();
	return view;
}

	private void init(){
		testButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				testWeb.loadUrl("javascript:setText(1111);");
			}
		});

		testWeb.getSettings().setJavaScriptEnabled(true);
		final String mimeType = "text/html";
		final String encoding = "UTF-8";
		String text = "<html>\n" +
				"<head>\n" +
				"<meta charset=\"utf-8\">\n" +
				"</head>\n" +
				"<body>\n" +
				"text:<div id=\"text\"></div>\n" +
				"<script type=\"text/javascript\">\n" +
				"var text = document.getElementById(\"text\");\n" +
				"function setText(str)\n" +
				"{\n" +
				"    text.innerHTML = str;\n" +
				"}\n" +
				"//webView.loadUrl(\"javascript:setContactInfo('\" + getJsonStr() + \"')\");\n" +
				"</script>\n" +
				"</body>\n" +
				"</html>\n";
		testWeb.loadDataWithBaseURL("", text, mimeType, encoding, "");
		new Thread(new Runnable() {
			public void run() {
				testWeb.loadUrl("javascript:setText('run');");
			}
		}).start();


	}
}
