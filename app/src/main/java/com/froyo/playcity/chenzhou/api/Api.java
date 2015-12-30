package com.froyo.playcity.chenzhou.api;

import com.froyo.playcity.chenzhou.MyApp;
import com.froyo.playcity.chenzhou.bean.Act;
import com.froyo.playcity.chenzhou.bean.Banner;
import com.froyo.playcity.chenzhou.bean.News;
import com.froyo.playcity.chenzhou.bean.Pio;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by Administrator on 2015/12/2.
 */
public class Api {
    private int pageSize = 5;
    private int page = 0;
    private String orderBy = "id DESC";

    private String  getFilter(JSONObject where)
    {
        JSONObject filter = new JSONObject();
        try {
            if(where == null)
            {
                where = new JSONObject();
            }
            where.put("city",MyApp.getApplicationInstance()._city);
            filter.put("where",where);
            filter.put("skip",pageSize*page);
            filter.put("limit",pageSize);
            filter.put("order",orderBy);
            try {
                return URLEncoder.encode(filter.toString(),"UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return null;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
    private ApiService apiService;
    private String[] header;
    public Api(){
        Retrofit retrofit = MyApp.getApplicationInstance().getApiCloud().getRetrofit();
        apiService =  retrofit.create(ApiService.class);
        header = MyApp.getApplicationInstance().getApiCloud().getHeader();
    }
    public void getModels(Callback<List<Act>> apiListern,int page, int pageSize)
    {
        this.page = page;
        this.pageSize = pageSize;

        Call<List<Act>> call  = apiService.Actlist(header[0], header[1],getFilter(null));
        call.enqueue(apiListern);
    }

    public void getNews(Callback<List<News>> apiListern,int page, int pageSize)
    {
        this.page = page;
        this.pageSize = pageSize;
        Call<List<News>> call  = apiService.Newslist(header[0], header[1], getFilter(null));
        call.enqueue(apiListern);
    }
    public void getNews(Callback<List<News>> apiListern,int page, int pageSize,String order)
    {
        this.page = page;
        this.pageSize = pageSize;
        this.orderBy = order;

        Call<List<News>> call  = apiService.Newslist(header[0], header[1],getFilter(null));
        call.enqueue(apiListern);
    }


    public void getNewsItem(String id, Callback<News> apiListern)
    {

        Call<News> call  = apiService.getNews(header[0], header[1], id);
        call.enqueue(apiListern);
    }
    public void geActItem(String id, Callback<Act> apiListern)
    {
        Call<Act> call  = apiService.getAct(header[0], header[1], id);
        call.enqueue(apiListern);
    }

    public void getBanners( Callback<List<Banner>> apiListern,int page, int pageSize)
    {
        this.orderBy = "id DESC";
        Call<List<Banner>> call  = apiService.getBanner(header[0], header[1], getFilter(null));
        call.enqueue(apiListern);
    }

    public void getBanners( Callback<List<Banner>> apiListern,String model,int page, int pageSize)
    {try {
        JSONObject where = new JSONObject();

            where.put("model", model);
            this.orderBy = "id DESC";
        Call<List<Banner>> call  = apiService.getBanner(header[0], header[1],getFilter(where));
        call.enqueue(apiListern);
    } catch (JSONException e) {
        e.printStackTrace();
    }
    }
    public void getPios( JSONObject where,int page,int pageSize, Callback<List<Pio>> apiListern)
    {
        this.page = page;
        this.pageSize = pageSize;
        String filterStr = getFilter(where);
        Call<List<Pio>> call  = apiService.getPios(header[0], header[1],filterStr );
            call.enqueue(apiListern);






    }
}
