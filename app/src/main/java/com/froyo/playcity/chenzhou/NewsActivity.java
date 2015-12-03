package com.froyo.playcity.chenzhou;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.froyo.playcity.chenzhou.api.Api;
import com.froyo.playcity.chenzhou.bean.News;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.Response;

/**
 * Created by Administrator on 2015/12/3.
 */
public class NewsActivity extends Activity {
    @Bind(R.id.title)
    TextView title;

    @Bind(R.id.from)
    TextView from;

    @Bind(R.id.date)
    TextView date;
    @Bind(R.id.content)
    WebView content;

    @Bind(R.id.back)
    ImageView back;
    private News news;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        ButterKnife.bind(this);
        bindAction();
        getData();
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
    private void getData(){
        Intent intent = this.getIntent();
        String id = intent.getStringExtra("id");
        Api api = new Api();
        api.getNewsItem(id,new Callback<News>(){

            @Override
            public void onResponse(Response<News> response) {
                news = response.body();
                setData();
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

    private void setData(){
        title.setText(news.getTitle());
        from.setText(news.getFrom());
        date.setText(news.getDate());
        final String mimeType = "text/html";
        final String encoding = "UTF-8";
        content.loadDataWithBaseURL("", news.getContent(), mimeType, encoding, "");
    }
}
