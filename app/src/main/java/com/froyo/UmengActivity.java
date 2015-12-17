package com.froyo;

import android.app.Activity;

import com.umeng.analytics.MobclickAgent;

/**
 * Created by Administrator on 2015/12/17.
 */
public class UmengActivity extends Activity {
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}
