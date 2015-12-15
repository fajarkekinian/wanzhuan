package com.froyo.playcity.chenzhou;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.froyo.playcity.chenzhou.api.Api;
import com.froyo.playcity.chenzhou.bean.News;
import com.froyo.playcity.chenzhou.bean.Pio;
import com.froyo.view.CommonAdapter;
import com.froyo.view.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.drakeet.materialdialog.MaterialDialog;
import retrofit.Callback;
import retrofit.Response;


public class LocalPoiActivity extends Activity {
    @Bind(R.id.pios)
    ListView pioList;
    @Bind(R.id.no_data) TextView emptyView;
    @Bind(R.id.back)
    ImageView back;
    private int netWork = 0;
    private MaterialDialog mMaterialDialog;
    private Api api;
    private CommonAdapter<Pio> mAdapter;
    private List<Pio> mDatas ;

    private String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_poi);
        ButterKnife.bind(this);
        init();
    }

    private void init()
    {
        bindAction();
        Intent intent = getIntent();
        type = intent.getStringExtra("type");

        api = new Api();
        mDatas = new ArrayList<Pio>();
        mAdapter = new CommonAdapter<Pio>(this, mDatas, R.layout.pio_item){

            @Override
            public void convert(ViewHolder helper, Pio item) {
                helper.setText(R.id.name, item.getName());
                helper.setText(R.id.address, item.getAddress());

            }
        };
        pioList.setAdapter(mAdapter);
        pioList.setEmptyView(emptyView);
        showToast();
        setListData();
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
        if(netWork>0)
        {
            return;
        }

        mMaterialDialog.dismiss();
    }
    private void setListData()
    {
        netWork++;
        api.getPios(type,new Callback<List<Pio>>() {

            @Override
            public void onResponse(Response<List<Pio>> response) {
                List<Pio> pio = response.body();
                mDatas.addAll(pio);
                mAdapter.notifyDataSetChanged();
                netWork--;
                closeToast();
            }

            @Override
            public void onFailure(Throwable t) {
                netWork--;
                t.printStackTrace();
                closeToast();
            }
        });




    }
}
