package com.froyo.playcity.chenzhou;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.froyo.UmengActivity;
import com.froyo.playcity.chenzhou.api.Api;
import com.froyo.playcity.chenzhou.bean.Act;
import com.froyo.playcity.chenzhou.bean.News;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.drakeet.materialdialog.MaterialDialog;
import retrofit.Callback;
import retrofit.Response;

/**
 * Created by Administrator on 2015/12/3.
 */
public class ActActivity extends UmengActivity {
    @Bind(R.id.name)
    TextView name;

    @Bind(R.id.date)
    TextView date;
    @Bind(R.id.address)
    TextView address;

    @Bind(R.id.content)
    WebView content;

    @Bind(R.id.back)
    ImageView back;


    private Act act;

    private MaterialDialog mMaterialDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act);
        ButterKnife.bind(this);
        bindAction();

        getData();
    }

    private void bindAction()
    {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    private void getData(){
        showToast();
        Intent intent = this.getIntent();
        String id = intent.getStringExtra("id");
        Api api = new Api();
        api.geActItem(id, new Callback<Act>() {

            @Override
            public void onResponse(Response<Act> response) {
                act = response.body();
                setData();
                closeToast();
            }

            @Override
            public void onFailure(Throwable t) {
                closeToast();
            }
        });
    }

    private void setData(){
        name.setText(act.getTitle());


        final String mimeType = "text/html";
        final String encoding = "UTF-8";
        String text = "<body style=\"color:#515155;font-size:14px;background-color:transparent;line-height:2em;text-align:justify;\" >    <style type=\"text/css\">\n" +
                "        dd{\n" +
                "            margin-left:0.5em;\n" +
                "            padding: 0px;\n" +
                "        }\n" +
                "        ul{\n" +
                "            padding-left: 0px;\n" +
                "            margin-left:0px;\n" +
                "        }\n" +
                "        .detail-title,.dd-padding{\n" +
                "            margin-left: 0px;\n" +
                "            padding-left: 0px;\n" +
                "        }\n" +
                "        .detail-title dd,.dd-padding dd{\n" +
                "            margin-left: 0px;\n" +
                "            padding-left: 0px;\n" +
                "        }\n" +
                "    </style>"+act.getIntro()+"</body>";
        content.loadDataWithBaseURL("", text, mimeType, encoding, "");
        address.setText(act.getAddress());
    }

    private void showToast()
    {
        mMaterialDialog = new MaterialDialog(this);
        View view = LayoutInflater.from(this)
                .inflate(R.layout.progress_bar,
                        null);
        ((TextView)view.findViewById(R.id.toast_msg)).setText(getResources().getString(R.string.waite_to_load));
        mMaterialDialog.setView(view).show();
    }

    private void closeToast()
    {
        mMaterialDialog.dismiss();
    }
}
