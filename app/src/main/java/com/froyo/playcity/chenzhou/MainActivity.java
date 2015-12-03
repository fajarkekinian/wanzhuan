package com.froyo.playcity.chenzhou;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

/**
 *
 * @author yechao
 *
 */
public class MainActivity extends android.support.v4.app.FragmentActivity {
    private Resources res = null;
    private FragmentTabHost mTabHost;

    private LayoutInflater layoutInflater;


    private Class fragmentArray[] = { FragmentHome.class,
            FragmentNews.class, FragmentLife.class, FragmentActivity.class,
            FragmentUser.class };


    private int mImageViewArray[] = { R.drawable.main_tab_item_home,
            R.drawable.main_tab_item_news, R.drawable.main_tab_item_life,
            R.drawable.main_tab_item_activity, R.drawable.main_tab_item_user };


    private String mTextviewArray[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        res = this.getResources();

        mTextviewArray = new String[]{ res.getString(R.string.tab_home),
                res.getString(R.string.tab_news),
                res.getString(R.string.tab_life),
                res.getString(R.string.tab_activity),
                res.getString(R.string.tab_user) };
        initView();
    }

    private void initView() {

        layoutInflater = LayoutInflater.from(this);

        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
        mTabHost.getTabWidget().setDividerDrawable(android.R.color.transparent);//remove divider

        int count = fragmentArray.length;

        for (int i = 0; i < count; i++) {

            TabSpec tabSpec = mTabHost.newTabSpec(mTextviewArray[i])
                    .setIndicator(getTabItemView(i));
            Class frag = fragmentArray[i];
            mTabHost.addTab(tabSpec, frag, null);

        }
    }

    private View getTabItemView(int index) {
        View view = layoutInflater.inflate(R.layout.tab_item, null);

        ImageView imageView = (ImageView) view.findViewById(R.id.imageview);
        imageView.setImageResource(mImageViewArray[index]);

        TextView textView = (TextView) view.findViewById(R.id.textview);
        textView.setText(mTextviewArray[index]);

        return view;
    }
}
