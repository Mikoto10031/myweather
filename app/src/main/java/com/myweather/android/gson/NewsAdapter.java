package com.myweather.android.gson;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.myweather.android.News_WebView;
import com.myweather.android.R;

import java.util.List;



/**
 * 项目名称：MyWeather
 * 类描述：此类是新闻Recyclerview的适配器
 * 创建人：liang
 * 创建时间：2017/5/25 0025 20:54
 * 修改人：liang
 * 修改时间：2017/5/25 0025 20:54
 * 修改备注：
 */
public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder>{
    private List<News> newsList;
    ViewGroup parent1;

    /**
     * 创建Viewholder来hold住需要的控件
     */
    static class ViewHolder extends RecyclerView.ViewHolder{
        View newsView;
        TextView news_title;
        ImageView image;
        public ViewHolder(View view){
            super(view);
            newsView=view;
            news_title=(TextView) view.findViewById(R.id.news_title);
            image=(ImageView) view.findViewById(R.id.news_image);
        }
    }
    public NewsAdapter(List<News> list){
        newsList=list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        parent1=parent;
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item,parent,false);
        final ViewHolder holder=new ViewHolder(view);
        /**
         * 新闻点击事件，显示具体的新闻
         */
        holder.newsView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /**
                 * 跳转WebView,借助了parent1
                 */
                int position=holder.getAdapterPosition();
                News news=newsList.get(position);
                Intent intent=new Intent(parent1.getContext(), News_WebView.class);
                intent.putExtra("URL",news.url);
                parent1.getContext().startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        News news=newsList.get(position);
        holder.news_title.setText("\r\n"+news.title);
        if(!news.imgage.equals("")){
            Glide.with(parent1.getContext()).load(news.imgage).into(holder.image);
        }
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }
}
