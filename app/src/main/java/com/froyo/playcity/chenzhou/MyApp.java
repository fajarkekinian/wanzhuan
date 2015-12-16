package com.froyo.playcity.chenzhou;

import android.app.Application;

import com.froyo.apicloud.ApiCloud;

/**
 * Created by Administrator on 2015/12/2.
 */
public class MyApp extends Application {
    private static final MyApp instance = new MyApp();
    private ApiCloud apiCloud;
    public String _city = "chenzhou";

    /**
     * 此方法方便在那些没有context对象的类中使用
     * @return MyApp实例
     */
    public static MyApp getApplicationInstance()
    {
        return instance;
    }

    public ApiCloud getApiCloud()
    {
        if( apiCloud == null )
        {
            apiCloud = new ApiCloud("A6966833672975","2E50D9D3-9063-E9B5-F3B9-065418CF2432");
        }
        return apiCloud;
    }
}
