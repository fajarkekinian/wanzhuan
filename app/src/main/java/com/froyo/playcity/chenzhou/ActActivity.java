package com.froyo.playcity.chenzhou;


import android.app.Activity;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import android.os.Environment;
import android.os.Parcelable;
import android.text.Html;
import android.text.TextUtils;
import android.view.Gravity;
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


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

        String subj = act.getAddress() + "-" + act.getTitle() + "正在进行" + act.getSummary() + "活动--" + getResources().getString(R.string.share_str)+"\n"+"http://www.aiaiaini.com/fronted/post/view?id=4";




        Intent it = new Intent(Intent.ACTION_SEND);
        it.setType("image/*");
        Uri imageUri = getLocalBitmapUri(img);
        List<ResolveInfo> resInfo = getPackageManager().queryIntentActivities(it, 0);
        String[] packages = new String[]{"com.sina.weibo"};
        if (!resInfo.isEmpty()) {
            List<Intent> targetedShareIntents = new ArrayList<Intent>();
            for (String pk : packages) {
                Intent targeted = new Intent(Intent.ACTION_SEND);
                targeted.setType("image/*");
                targeted.putExtra("Kdescription", subj);
                targeted.putExtra(Intent.EXTRA_STREAM, imageUri);
                targeted.putExtra(Intent.EXTRA_TEXT, subj);
                targeted.putExtra("url","http://www.aiaiaini.com/fronted/post/view?id=4");
                targeted.setPackage(pk);
                targetedShareIntents.add(targeted);
            }
            Intent chooserIntent = Intent.createChooser(targetedShareIntents.remove(0), "Select app to share");
            if (chooserIntent == null) {
                return;
            }
            // A Parcelable[] of Intent or LabeledIntent objects as set with
            // putExtra(String, Parcelable[]) of additional activities to place
            // a the front of the list of choices, when shown to the user with a
            // ACTION_CHOOSER.
            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, targetedShareIntents.toArray(new Parcelable[] {}));
            try {
                startActivity(chooserIntent);
            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(this, "Can't find share component to share", Toast.LENGTH_SHORT).show();
            }
        }

    }



    public Uri getLocalBitmapUri(ImageView imageView) {
        // Extract Bitmap from ImageView drawable
        Drawable drawable = imageView.getDrawable();
        Bitmap bmp = null;
        if (drawable instanceof BitmapDrawable){
            bmp = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        } else {
            return null;
        }
        // Store image to default external storage directory
        Uri bmpUri = null;
        try {
            File file =  new File(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DOWNLOADS), "share_image_" + System.currentTimeMillis() + ".png");
            file.getParentFile().mkdirs();
            FileOutputStream out = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.close();
            bmpUri = Uri.fromFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bmpUri;
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
                "        }\n";
                if(act.getIntro()!=null)
                text +="    </style>"+act.getIntro()+"</body>";
        content.loadDataWithBaseURL("", text, mimeType, encoding, "");
        address.setText(act.getAddress());
        summery.setText(act.getTitle()+"\n"+act.getSummary());
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
