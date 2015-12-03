package com.froyo.playcity.chenzhou.api;

import com.froyo.playcity.chenzhou.MyApp;
import com.froyo.playcity.chenzhou.bean.Act;
import com.froyo.playcity.chenzhou.bean.News;

import java.io.UnsupportedEncodingException;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by Administrator on 2015/12/2.
 */
public class Api {
    private ApiService apiService;
    private String[] header;
    public Api(){
        Retrofit retrofit = MyApp.getApplicationInstance().getApiCloud().getRetrofit();
        apiService =  retrofit.create(ApiService.class);
        header = MyApp.getApplicationInstance().getApiCloud().getHeader();
    }
    public void getModels(Callback<List<Act>> apiListern)
    {
        Call<List<Act>> call  = apiService.Actlist(header[0], header[1]);
        call.enqueue(apiListern);
    }

    public void getNews(Callback<List<News>> apiListern)
    {
        Call<List<News>> call  = apiService.Newslist(header[0], header[1]);
        call.enqueue(apiListern);
    }

    public void getNewsItem(String id, Callback<News> apiListern)
    {
        Call<News> call  = apiService.getNews(header[0], header[1],id);
        call.enqueue(apiListern);
    }
}
