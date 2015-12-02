package com.froyo.playcity.chenzhou.api;

import com.froyo.playcity.chenzhou.MyApp;
import com.froyo.playcity.chenzhou.bean.Act;

import java.util.List;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Headers;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by Administrator on 2015/12/2.
 */
public interface ApiService {
    @GET("/mcm/api/activity")
    Call<List<Act>> Actlist(@Header("X-APICloud-AppId") String appId,@Header("X-APICloud-AppKey") String appKey);
}
