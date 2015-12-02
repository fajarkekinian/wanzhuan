package com.froyo.playcity.chenzhou;

import android.app.Application;

import com.froyo.apicloud.ApiCloud;

/**
 * Created by Administrator on 2015/12/2.
 */
public class MyApp extends Application {
    private static final MyApp instance = new MyApp();
    private ApiCloud apiCloud;

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
            apiCloud = new ApiCloud("","");
        }
        return apiCloud;
    }
}
