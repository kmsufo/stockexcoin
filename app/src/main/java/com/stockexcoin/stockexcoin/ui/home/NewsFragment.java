package com.stockexcoin.stockexcoin.ui.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import io.cryptocontrol.cryptonewsapi.*;
import io.cryptocontrol.cryptonewsapi.models.Article;
import io.cryptocontrol.cryptonewsapi.models.Feed;
import io.cryptocontrol.cryptonewsapi.models.Language;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.elyeproj.loaderviewlibrary.LoaderImageView;
import com.elyeproj.loaderviewlibrary.LoaderTextView;
import com.stockexcoin.stockexcoin.R;

import org.ocpsoft.prettytime.PrettyTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class NewsFragment extends Fragment {

    private DownloadNews mDownloadNews;
    private int newsType;
    private TextView mDownloadInfo;
    private ImageButton mRefreshButton;
    private LoaderTextView mLoaderTextView;
    private LoaderImageView mLoaderImageView;
    private RecyclerView mRecyclerView;
    private List<Article> mNews;
    private List<Feed> mFeed;
    private Language mLanguage;
    private View root;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_home, container, false);
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(Objects.requireNonNull(getContext()));
        if(settings.getString("language_selected", "English").equals("English")) mLanguage = Language.ENGLISH;
        else mLanguage = Language.RUSSIAN;
        Log.d("Language",mLanguage.toString());
        newsType = 0;
        mRefreshButton = root.findViewById(R.id.refreshButton);
        mRefreshButton.bringToFront();
        mRefreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Refresh","Clicked ");
                mDownloadNews = new DownloadNews();
                mDownloadNews.execute();
            }
        });

        mDownloadInfo = root.findViewById(R.id.downloadInfo);
        mRecyclerView = root.findViewById(R.id.newslist);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));

        Log.d("News ", "Downloading");
        return root;
    }
    @Override
    public void onDestroy() {
        Log.d("DESTROY ", "DESTROY");
        if(mDownloadNews != null)
        mDownloadNews.cancel(true);
        super.onDestroy();
    }

    @Override
    public void onPause() {
        Log.d("PAUSE ", "PAUSE");
        mDownloadNews.cancel(true);
        super.onPause();
    }

    @Override
    public void onResume() {
        Log.d("RESUME ", "RESUME");
        if(mRecyclerView.getAdapter() == null || mLoaderTextView.getVisibility()==View.VISIBLE){
        mDownloadNews = new DownloadNews();
        mDownloadNews.execute();
        }

        super.onResume();
    }

    private void loadingView(){
        if (mDownloadNews.getStatus() == AsyncTask.Status.FINISHED || mRecyclerView.getVisibility()==View.VISIBLE){
            for(int i = 1; i<=19; i++){
                mLoaderTextView = root.findViewById(Objects.requireNonNull(getContext()).getResources().getIdentifier("loaderTextView"+i,"id",getContext().getPackageName()));
                mLoaderTextView.setVisibility(View.INVISIBLE);
                mLoaderTextView.resetLoader();
            }
            for(int i = 1; i<=6; i++){
                mLoaderImageView = root.findViewById(getContext().getResources().getIdentifier("loaderImageView"+i,"id",getContext().getPackageName()));
                mLoaderImageView.setVisibility(View.INVISIBLE);
                mLoaderImageView.resetLoader();
            }
        } else {
            for(int i = 1; i<=19; i++){
                mLoaderTextView = root.findViewById(Objects.requireNonNull(getContext()).getResources().getIdentifier("loaderTextView"+i,"id",getContext().getPackageName()));
                mLoaderTextView.setVisibility(View.VISIBLE);
                mLoaderTextView.resetLoader();
            }
            for(int i = 1; i<=6; i++){
                mLoaderImageView = root.findViewById(getContext().getResources().getIdentifier("loaderImageView"+i,"id",getContext().getPackageName()));
                mLoaderImageView.setVisibility(View.VISIBLE);
                mLoaderImageView.resetLoader();
            }
        }
    }

    class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {

        SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        private LayoutInflater inflater;
        private Feed.FeedList feeds;
        private Article.ArticleList articles;
        private Context context;
        private PrettyTime pTime = new PrettyTime(new Locale((mLanguage == Language.ENGLISH)? "en":"ru"));

        View.OnClickListener oclBtnTop = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newsType = 0;
                mDownloadNews = new DownloadNews();
                if (mDownloadNews.getStatus() == AsyncTask.Status.PENDING && mDownloadNews.getStatus() != AsyncTask.Status.RUNNING){
                    Log.d("OnClickTop","Clicked");
                    mDownloadNews.execute();
                }
            }
        };

        View.OnClickListener oclBtnLast = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newsType = 1;
                mDownloadNews = new DownloadNews();
                if (mDownloadNews.getStatus() == AsyncTask.Status.PENDING && mDownloadNews.getStatus() != AsyncTask.Status.RUNNING){
                    Log.d("OnClickLast","Clicked + run");
                    mDownloadNews.execute();
                }
            }
        };

        View.OnClickListener oclBtnFeed = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("OnClickFeed","Clicked");
                newsType = 2;
                mDownloadNews = new DownloadNews();
                if (mDownloadNews.getStatus() == AsyncTask.Status.PENDING && mDownloadNews.getStatus() != AsyncTask.Status.RUNNING){
                    Log.d("OnClickFeed","Clicked + run");
                    mDownloadNews.execute();
                }
            }
        };

        DataAdapter(Context context, Article.ArticleList articles) {
            this.articles = articles;
            this.context = context;
            this.inflater = LayoutInflater.from(context);
        }

        DataAdapter(Context context, Feed.FeedList feeds) {
            this.feeds = feeds;
            this.context = context;
            this.inflater = LayoutInflater.from(context);
        }


        @Override
        @NonNull
        public DataAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            if (viewType == 1) {
                View view = inflater.inflate(R.layout.news_title_item, parent, false);
                return new DataAdapter.ViewHolder(view);
            } else {
                View view = inflater.inflate(R.layout.news_list_item, parent, false);
                return new DataAdapter.ViewHolder(view);
            }

        }

        @SuppressLint({"DefaultLocale", "SetTextI18n"})
        @Override
        public void onBindViewHolder(@NonNull DataAdapter.ViewHolder holder, int position) {
            if (feeds != null){
                final Feed feed = feeds.get(position);
                switch (feed.getType()){
                    case "article":
                        final Article article = feed.getArticle();
                        if(article.getTitle()!=null && holder.mTitle!=null)
                            holder.mTitle.setText(article.getTitle());
                        if(article.getDescription()!=null && holder.mDescription!=null)
                            holder.mDescription.setText(article.getDescription());
                        if(article.getSource().getName()!=null && holder.mSource!=null){
                            holder.mSource.setText(article.getSource().getName());
                            holder.mSource.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse(article.getUrl())));
                                }
                            });}
                        if(article.getPublishedAt()!=null  && holder.mDate!=null) {
                            try {
                                holder.mDate.setText(pTime.format(formatter.parse(article.getPublishedAt())));
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                        if(article.getThumbnail()!=null  && holder.mThumbnail!=null)
                            Glide.with(this.context)
                                    .load(article.getThumbnail())
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .into(holder.getImage());
                        else if(holder.mThumbnail!=null){
                            holder.mThumbnail.setVisibility(View.GONE);
                            holder.mThumbnailPlaceholder.setVisibility(View.GONE);
                        }
                        break;
                    case "tweet":
                        if(feed.getTweet().getUsername()!=null && holder.mTitle!=null)
                            holder.mTitle.setText(feed.getTweet().getUsername());
                        if(feed.getTweet().getText()!=null && holder.mDescription!=null)
                            holder.mDescription.setText(feed.getTweet().getText());
                        if(feed.getTweet().getUrl()!=null && holder.mSource!=null){
                            holder.mSource.setText("TWITTER");
                        holder.mSource.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse(feed.getTweet().getUrl())));
                            }
                        });}
                        if(feed.getTweet().getPublishedAt()!=null  && holder.mDate!=null) {
                            try {
                                holder.mDate.setText(pTime.format(formatter.parse(feed.getTweet().getPublishedAt())));
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                        if(holder.mThumbnail!=null){
                            Glide.with(this.context)
                                    .load(R.drawable.ic_twitter_black_24dp)
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .into(holder.getImage());
                        }
                        break;
                    case "reddit":
                        if(feed.getReddit().getTitle()!=null && holder.mTitle!=null)
                            holder.mTitle.setText(feed.getReddit().getTitle());
                        if(feed.getReddit().getDescription()!=null && holder.mDescription!=null)
                            holder.mDescription.setText(feed.getReddit().getDescription());
                        if(feed.getReddit().getUrl()!=null && holder.mSource!=null){
                            holder.mSource.setText("REDDIT");
                        holder.mSource.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse(feed.getReddit().getUrl())));
                            }
                        });}
                        if(feed.getReddit().getPublishedAt()!=null  && holder.mDate!=null) {
                            try {
                                holder.mDate.setText(pTime.format(formatter.parse(feed.getReddit().getPublishedAt())));
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                            if(holder.mThumbnail!=null){
                            Glide.with(this.context)
                                    .load(R.drawable.ic_reddit_logo)
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .into(holder.getImage());
                        }
                        break;
                }
            } else {

            final Article article = articles.get(position);
            if(article.getTitle()!=null && holder.mTitle!=null)
            holder.mTitle.setText(article.getTitle());
            if(article.getDescription()!=null && holder.mDescription!=null)
            holder.mDescription.setText(article.getDescription());
            if(article.getSource().getName()!=null && holder.mSource!=null){
            holder.mSource.setText(article.getSource().getName());
            holder.mSource.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse(article.getUrl())));
                    }
                });}
            if(article.getPublishedAt()!=null  && holder.mDate!=null) {
                try {
                    holder.mDate.setText(pTime.format(formatter.parse(article.getPublishedAt())));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            if(article.getThumbnail()!=null  && holder.mThumbnail!=null)
                Glide.with(this.context)
                        .load(article.getThumbnail())
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(holder.getImage());
            else if(holder.mThumbnail!=null){
                holder.mThumbnail.setVisibility(View.GONE);
                holder.mThumbnailPlaceholder.setVisibility(View.GONE);
            }
            }


        }

        @Override
        public int getItemCount() {
            if (articles != null)
                return articles.size();
            else
                return feeds.size();
        }

        @Override
        public int getItemViewType(int position) {
            if (position == 0) return 1;
            else return 2;
        }


        class ViewHolder extends RecyclerView.ViewHolder {

            final TextView mTitle, mDescription, mDate,mNewsTitle;
            final Button mSource,mTop,mLatest,mFeed;
            final ImageView mThumbnail;
            final CardView mThumbnailPlaceholder;
            ViewHolder(View view){
                super(view);

                mTitle = view.findViewById(R.id.newsTitle);
                mDescription = view.findViewById(R.id.newsDescription);
                mDate = view.findViewById(R.id.date);
                mSource = view.findViewById(R.id.source);
                mThumbnail = view.findViewById(R.id.thumbnail);
                mThumbnailPlaceholder = view.findViewById(R.id.cardView);

                mNewsTitle = view.findViewById(R.id.newsMainTitle);
                mTop = view.findViewById(R.id.topNews);
                mLatest = view.findViewById(R.id.latestNews);
                mFeed = view.findViewById(R.id.feedNews);

                if(mTop != null){
                    switch (newsType) {
                        case 0:
                            mTop.setEnabled(false);
                            mLatest.setEnabled(true);
                            mFeed.setEnabled(true);
                            mTop.getBackground().setTint(getResources().getColor(R.color.colorPrimaryDark));
                            mTop.setTextColor(Color.BLACK);
                            mLatest.setOnClickListener(oclBtnLast);
                            mFeed.setOnClickListener(oclBtnFeed);
                            break;
                        case 1:
                            mTop.setEnabled(true);
                            mLatest.setEnabled(false);
                            mFeed.setEnabled(true);
                            mLatest.getBackground().setTint(getResources().getColor(R.color.colorPrimaryDark));
                            mLatest.setTextColor(Color.BLACK);
                            mTop.setOnClickListener(oclBtnTop);
                            mFeed.setOnClickListener(oclBtnFeed);
                            break;
                        case 2:
                            mTop.setEnabled(true);
                            mLatest.setEnabled(true);
                            mFeed.setEnabled(false);
                            mFeed.getBackground().setTint(getResources().getColor(R.color.colorPrimaryDark));
                            mFeed.setTextColor(Color.BLACK);
                            mTop.setOnClickListener(oclBtnTop);
                            mLatest.setOnClickListener(oclBtnLast);
                            break;
                    }
                }
            }
            ImageView getImage(){ return this.mThumbnail;}
        }





    }

    class DownloadNews extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mRecyclerView.setVisibility(View.INVISIBLE);
            mDownloadInfo.setVisibility(View.INVISIBLE);
            mRefreshButton.setVisibility(View.INVISIBLE);
            loadingView();
        }

        @Override
        protected Void doInBackground(Void... params) {
//             4b274f17c2062f315c2d2ca129f0a0fc
            CryptoControlApi api = new CryptoControlApi("0b9b93555e2bbc23b9fecbf2bbe48d93");
            mNews = new Article.ArticleList();
            mFeed = new Feed.FeedList();
            switch(newsType){
                case 0:
                    api.getTopNews(mLanguage, new CryptoControlApi.OnResponseHandler<List<Article>>() {
                        public void onSuccess(List<Article> body) {
                            mNews.addAll(body);
                        }
                        public void onFailure(Exception e) {
                            Log.d("Error ", e.toString());
                        }
                    });
                    break;
                case 1:
                    api.getLatestNews(mLanguage, new CryptoControlApi.OnResponseHandler<List<Article>>() {
                        public void onSuccess(List<Article> body) {
                            mNews.addAll(body);
                        }
                        public void onFailure(Exception e) {
                            Log.d("Error ", e.toString());
                        }
                    });
                    break;
                case 2:
                    api.getLatestFeedByCoin(mLanguage,"bitcoin", new CryptoControlApi.OnResponseHandler<List<Feed>>() {
                        public void onSuccess(List<Feed> body) {
                            mFeed.addAll(body);
                            for(Feed feed:body){
                                Log.d("Type news ", feed.getType());
                            }
                        }
                        public void onFailure(Exception e) {
                            Log.d("Error ", e.toString());
                        }
                    });
                    api.getLatestFeedByCoin(mLanguage,"ethereum", new CryptoControlApi.OnResponseHandler<List<Feed>>() {
                        public void onSuccess(List<Feed> body) {
                            mFeed.addAll(body);
                            for(Feed feed:body){
                                Log.d("Type news ", feed.getType());
                            }
                        }
                        public void onFailure(Exception e) {
                            Log.d("Error ", e.toString());
                        }
                    });
                    Collections.shuffle(mFeed);
                    break;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            mRecyclerView.setVisibility(View.VISIBLE);
            loadingView();
            if(mNews.size() != 0) {
                DataAdapter adapter = new DataAdapter(getContext(), (Article.ArticleList) mNews);
                mRecyclerView.setAdapter(adapter);
            }else if (mFeed.size() != 0){
                DataAdapter adapter = new DataAdapter(getContext(), (Feed.FeedList) mFeed);
                mRecyclerView.setAdapter(adapter);
            }else
                {
                mRecyclerView.setVisibility(View.INVISIBLE);
                mDownloadInfo.setVisibility(View.VISIBLE);
                mRefreshButton.setVisibility(View.VISIBLE);
            }
        }
    }


}