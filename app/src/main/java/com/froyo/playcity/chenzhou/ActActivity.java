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

import com.froyo.UmengActivity;
import com.froyo.playcity.chenzhou.api.Api;
import com.froyo.playcity.chenzhou.bean.Act;
import com.froyo.playcity.chenzhou.bean.News;
import com.squareup.picasso.Picasso;

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
                if (act != null) {

                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    if (act.getImg() != null) {


                        String path= Environment.getExternalStorageDirectory().getAbsolutePath()+ File.separator+"share.jpg";
                        Uri uri=Uri.parse("file:///"+path);
                        intent.putExtra(Intent.EXTRA_STREAM, uri);  //传输图片或者文件 采用流的方式

                        intent.setType("image/*");   //分享图片
                    }

//                    intent.setType("text/plain"); // 分享发送的数据类型

                    String subj = getResources().getString(R.string.share_str) + "\n" +getResources().getString(R.string.tab_activity)+"--"+ act.getTitle();
                    intent.putExtra("Kdescription", subj);
                    intent.putExtra(Intent.EXTRA_TEXT, getResources().getString(R.string.app_name) + "--" + getResources().getString(R.string.tab_activity) + "--" + act.getSummary());   //附带的说明信息
                    intent.putExtra(Intent.EXTRA_SUBJECT,subj );
                    startActivity(Intent.createChooser(intent, getResources().getString(R.string.share)));
                }
            }
        });
    }

        public void saveBitmap(Bitmap bm) {
            File f = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+ File.separator, "share.jpg");
            if (f.exists()) {
                f.delete();
            }
            try {
                FileOutputStream out = null;
                try {
                    out = new FileOutputStream(f);
                    bm.compress(Bitmap.CompressFormat.JPEG, 90, out);
                    out.flush();
                    out.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
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
