package com.froyo.playcity.chenzhou;


import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.froyo.UmengActivity;
import com.froyo.playcity.chenzhou.api.Api;
import com.froyo.playcity.chenzhou.bean.Act;
import com.squareup.picasso.Picasso;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

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

    @Bind(R.id.share)
    TextView share;
    @Bind(R.id.summery)
    TextView summery;

    @Bind(R.id.img)
    ImageView img;


    private Act act;

    private Context context;

    private MaterialDialog mMaterialDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act);
        ButterKnife.bind(this);
        context = this;
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

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showShare();
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

    private void showShare()
    {
//        PlatformConfig.setWeixin("wx967daebe835fbeac", "5bb696d9ccd75a38c8a0bfe0675559b3");
        PlatformConfig.setSinaWeibo("3581140788", "12f186f2a656a4b284d3e78be982755e");
        PlatformConfig.setQQZone("100424468", "c7394704798a158208a74ab60104f0ba");
        if(act == null)
        {
            Toast.makeText(context,"没有数据",Toast.LENGTH_LONG).show();
        }
        else {
            final SHARE_MEDIA[] displaylist = new SHARE_MEDIA[]
                    {
//                            SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE,
                            SHARE_MEDIA.SINA,
                            SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE, SHARE_MEDIA.EMAIL
                    };
            String title = act.getAddress() + "-" + act.getTitle() + "正在进行" + act.getSummary() + "活动--" + getResources().getString(R.string.share_str);
            ShareAction shareAction =  new ShareAction(this).setDisplayList(displaylist)
                    .withText(title)
                    .setCallback(shareListener);
            if(act.getImg() != null)
            {
                String img = act.getImg();
                UMImage umimg = new UMImage(context, img);
                shareAction.withMedia(umimg);
            }
            else
            {
                UMImage umicon = new UMImage(context, R.mipmap.icon_app);
                shareAction.withMedia(umicon);
            }

            shareAction.open();


        }
    }

    private UMShareListener shareListener = new UMShareListener() {
        @Override
        public void onResult(SHARE_MEDIA share_media) {

        }

        @Override
        public void onError(SHARE_MEDIA share_media, Throwable throwable) {

        }

        @Override
        public void onCancel(SHARE_MEDIA share_media) {

        }
    };
    private void setData(){
        name.setText(act.getTitle());
        if(act.getImg() !=null)
        {
            Picasso.with(this).load(act.getImg()).into(img);
        }

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
        summery.setText(act.getTitle()+"-"+act.getSummary());
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
