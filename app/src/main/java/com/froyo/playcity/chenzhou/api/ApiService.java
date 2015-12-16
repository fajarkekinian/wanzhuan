package com.froyo.playcity.chenzhou.api;

import com.froyo.playcity.chenzhou.MyApp;
import com.froyo.playcity.chenzhou.bean.Act;
import com.froyo.playcity.chenzhou.bean.Banner;
import com.froyo.playcity.chenzhou.bean.News;
import com.froyo.playcity.chenzhou.bean.Pio;

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
    Call<List<Act>> Actlist(@Header("X-APICloud-AppId") String appId,@Header("X-APICloud-AppKey") String appKey,@Query(value="filter",encoded = true ) String filter);

    @GET("/mcm/api/news")
    Call<List<News>> Newslist(@Header("X-APICloud-AppId") String appId,@Header("X-APICloud-AppKey") String appKey,@Query(value="filter",encoded = true ) String filter);

    @GET("/mcm/api/news/{id}")
    Call<News> getNews(@Header("X-APICloud-AppId") String appId,@Header("X-APICloud-AppKey") String appKey,@Path("id") String id);

    @GET("/mcm/api/activity/{id}")
    Call<Act> getAct(@Header("X-APICloud-AppId") String appId,@Header("X-APICloud-AppKey") String appKey,@Path("id") String id);

    @GET("/mcm/api/banner")
    Call<List<Banner>> getBanner(@Header("X-APICloud-AppId") String appId,@Header("X-APICloud-AppKey") String appKey,@Query(value="filter",encoded = true ) String filter);
    @GET("/mcm/api/local_server")
    Call<List<Pio>> getPios(@Header("X-APICloud-AppId") String appId,@Header("X-APICloud-AppKey") String appKey, @Query(value="filter",encoded = true ) String filter);
}
