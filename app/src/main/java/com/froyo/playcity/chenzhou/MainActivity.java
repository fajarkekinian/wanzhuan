package com.froyo.playcity.chenzhou;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;
import com.umeng.update.UmengUpdateAgent;

/**
 *
 * @author yechao
 *
 */
public class MainActivity extends android.support.v4.app.FragmentActivity {
    private Resources res = null;
    private FragmentTabHost mTabHost;
    private Context context;

    private LayoutInflater layoutInflater;


    private Class []fragClass = { FragmentHome.class,
            FragmentNews.class, FragmentLife.class, FragmentActivity.class,
            FragmentAbout.class };


    private int mImageViewArray[] = { R.drawable.main_tab_item_home,
            R.drawable.main_tab_item_news, R.drawable.main_tab_item_life,
            R.drawable.main_tab_item_activity, R.drawable.main_tab_item_user };


    private String mTextviewArray[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        res = this.getResources();
        context = this;
        mTextviewArray = new String[]{ res.getString(R.string.tab_home),
                res.getString(R.string.tab_news),
                res.getString(R.string.tab_life),
                res.getString(R.string.tab_activity),
                res.getString(R.string.tab_about) };
        addAutoUpdate();
        initView();
    }

    private void addAutoUpdate()
    {
        UmengUpdateAgent.setDefault();
        UmengUpdateAgent.update(this);
    }

    private void initView() {

        layoutInflater = LayoutInflater.from(this);

        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
        mTabHost.getTabWidget().setDividerDrawable(android.R.color.transparent);//remove divider

        final int count = fragClass.length;

        for (int i = 0; i < count; i++) {

            TabSpec tabSpec = mTabHost.newTabSpec(mTextviewArray[i])
                    .setIndicator(getTabItemView(i));
            Class frag = fragClass[i];
            mTabHost.addTab(tabSpec, frag, null);

        }


//        mTabHost.getTabWidget().getChildTabViewAt(count-1).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(false)
//                {
//                    mTabHost.setCurrentTab(count-1);
//                    mTabHost.getTabWidget().requestFocus(View.FOCUS_FORWARD);
//                }
//                else
//                {
//                    Toast.makeText(MainActivity.this, "点击了Button", Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent();
//                    intent.setClass(context,LoginActivity.class);
//                    startActivity(intent);
//                }
//            }
//        });
    }

    private View getTabItemView(int index) {
        View view = layoutInflater.inflate(R.layout.tab_item, null);

        ImageView imageView = (ImageView) view.findViewById(R.id.imageview);
        imageView.setImageResource(mImageViewArray[index]);

        TextView textView = (TextView) view.findViewById(R.id.textview);
        textView.setText(mTextviewArray[index]);

        return view;
    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);       //统计时长
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}
