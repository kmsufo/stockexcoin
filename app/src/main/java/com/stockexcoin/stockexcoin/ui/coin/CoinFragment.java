package com.stockexcoin.stockexcoin.ui.coin;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.elyeproj.loaderviewlibrary.LoaderTextView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.stockexcoin.stockexcoin.R;
import com.stockexcoin.stockexcoin.marketdata.MarketPair;
import com.stockexcoin.stockexcoin.marketdata.StockExCoin;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class CoinFragment extends Fragment {

    final private String WEB_PAGE_1="https://web-api.coinmarketcap.com/v1/cryptocurrency/market-pairs/latest?aux=market_url,effective_liquidity,price_quote&convert=USD&symbol=";
    final private String WEB_PAGE_2="&limit=5000&sort=cmc_rank&start=1";
    private String mCoinSymbol, mWebPage;
    private static String mSearch;
    private long mVolumeLimit;
    private static boolean mSorted = true;
    private CoinFragment.DownloadJSON mDownloadJSON;
    private static StockExCoin stockExCoin;
    private RecyclerView mRecyclerView;
    private TextView mDownloadInfo;
    private ImageButton mRefreshButton;
    private LoaderTextView mLoaderTextView;
    private static DataAdapter adapter;
    private View root;





    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        Log.d("OnCreateView", "Created");
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(Objects.requireNonNull(getContext()));
        mCoinSymbol = settings.getString("coin_selected", "BTC").toUpperCase();
        mVolumeLimit = Long.parseLong((
                settings.getString("volume_limit","1000000").equals(""))?
                "0":
                settings.getString("volume_limit","1000000").substring(0,(settings.getString("volume_limit","1000000").length()<18)?settings.getString("volume_limit","1000000").length():18));
        mWebPage = WEB_PAGE_1 + mCoinSymbol + WEB_PAGE_2;
        root = inflater.inflate(R.layout.fragment_coin, container, false);

        mDownloadInfo = root.findViewById(R.id.downloadInfo);
        mRefreshButton = root.findViewById(R.id.refreshButton);
        mRefreshButton.bringToFront();
        mRefreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDownloadJSON = new DownloadJSON();
                mDownloadJSON.execute();
            }
        });
        mRecyclerView = root.findViewById(R.id.marketlist);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));


        return root;
    }

    @Override
    public void onDestroy() {
        Log.d("DESTROY ", "DESTROY");
        mDownloadJSON.cancel(true);
        super.onDestroy();
    }
    @Override
    public void onPause() {
        Log.d("PAUSE ", "PAUSE");
        mDownloadJSON.cancel(true);
        super.onPause();
    }

    @Override
    public void onResume() {
        if(mRecyclerView.getAdapter() == null || mLoaderTextView.getVisibility()==View.VISIBLE){
            mDownloadJSON = new DownloadJSON();
            mDownloadJSON.execute();
        }
        super.onResume();
    }

    public static void doSearch(String search) {
        mSearch = search;
        if (adapter != null) {
            adapter.getFilter().filter(mSearch);
        }
    }
    public static void doSort() {
        if (adapter != null) {
            if (!mSorted){
                mSorted=true;
                Collections.sort(stockExCoin.getData().getMarketPairs());
                adapter.notifyDataSetChanged();
            }else {
                mSorted=false;
                Collections.sort(stockExCoin.getData().getMarketPairs(),Collections.reverseOrder());
                adapter.notifyDataSetChanged();
            }
        }
    }
    private void loadingView(){
        if (mDownloadJSON.getStatus()== AsyncTask.Status.FINISHED || mRecyclerView.getVisibility()==View.VISIBLE){
            for(int i = 1; i<=36; i++){
                mLoaderTextView = root.findViewById(Objects.requireNonNull(getContext()).getResources().getIdentifier("loaderTextView"+i,"id",getContext().getPackageName()));
                mLoaderTextView.setVisibility(View.INVISIBLE);
                mLoaderTextView.resetLoader();
            }
        } else {
            for(int i = 1; i<=36; i++){
                mLoaderTextView = root.findViewById(Objects.requireNonNull(getContext()).getResources().getIdentifier("loaderTextView"+i,"id",getContext().getPackageName()));
                mLoaderTextView.setVisibility(View.VISIBLE);
                mLoaderTextView.resetLoader();
            }
        }
    }



    class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> implements Filterable {

        private LayoutInflater inflater;
        private List<MarketPair> marketPairs;
        private List<MarketPair> marketPairsFull;


        DataAdapter(Context context, List<MarketPair> marketPairs) {
            this.marketPairs = marketPairs;
            marketPairsFull = new ArrayList<>(marketPairs);
            this.inflater = LayoutInflater.from(context);
        }
        @Override
        @NonNull
        public DataAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            View view = inflater.inflate(R.layout.coin_list_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(DataAdapter.ViewHolder holder, int position) {
            final MarketPair marketPair = marketPairs.get(position);
            holder.mId.setText(String.valueOf(position+1));
            holder.mMarket.setText(marketPair.getExchange().getName());
            holder.mMarketPair.setText(marketPair.getMarketPair());
            holder.mMarketPair.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse(marketPair.getMarketUrl())));
                }
            });
            if (marketPair.getQuote().getUSD().getPrice() > 2)
                holder.mPrice.setText(String.format(Locale.ENGLISH,"%.2f $", marketPair.getQuote().getUSD().getPrice()));
            else
                holder.mPrice.setText(String.format(Locale.ENGLISH,"%.3f $", marketPair.getQuote().getUSD().getPrice()));
            holder.mVolume.setText(String.format(Locale.ENGLISH,"24h vol. %d $", marketPair.getQuote().getUSD().getVolume24h()));
        }

        @Override
        public int getItemCount() {
            return marketPairs.size();
        }

        @Override
        public Filter getFilter(){
            return marketPairFilter;
        }
        private Filter marketPairFilter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                List<MarketPair> filteredList = new ArrayList<>();

                if(constraint == null || constraint.length() == 0 ){
                    filteredList.addAll(marketPairsFull);
                }else{
                    String filterPattern = constraint.toString().toLowerCase().trim();

                    for(MarketPair marketPair: marketPairsFull){
                        if(marketPair.getExchange().getName().toLowerCase().contains(filterPattern) || marketPair.getMarketPair().toLowerCase().contains(filterPattern)){
                            filteredList.add(marketPair);
                        }
                    }
                }
                FilterResults results = new FilterResults();
                results.values = filteredList;
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                marketPairs.clear();
                marketPairs.addAll((List)results.values);
                notifyDataSetChanged();
            }
        };

        class ViewHolder extends RecyclerView.ViewHolder {
            final private TextView mMarket, mPrice, mVolume, mId;
            final private Button mMarketPair;
            ViewHolder(View view){
                super(view);
                mId = view.findViewById(R.id.idNumber);
                mMarket = view.findViewById(R.id.market);
                mMarketPair = view.findViewById(R.id.marketPair);
                mPrice = view.findViewById(R.id.price);
                mVolume = view.findViewById(R.id.volume);
            }
        }
    }

    class DownloadJSON extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mDownloadInfo.setVisibility(View.INVISIBLE);
            mRefreshButton.setVisibility(View.INVISIBLE);
            mRecyclerView.setVisibility(View.INVISIBLE);
            loadingView();
            stockExCoin = null;
            adapter = null;
        }

        @Override
        protected Void doInBackground(Void... params) {
            try (InputStream is = new URL(mWebPage).openStream();
                 Reader reader = new InputStreamReader(is, StandardCharsets.UTF_8)) {

                GsonBuilder gsonBuilder = new GsonBuilder();
                gsonBuilder.setDateFormat("M/d/yy hh:mm a");
                Gson gson = gsonBuilder.create();

                stockExCoin = gson.fromJson(reader, StockExCoin.class);

                Log.d("ListSize ", Integer.toString(stockExCoin.getData().getMarketPairs().size()));


                List<MarketPair> toDelete = new ArrayList<>();
                for(MarketPair marketPair: stockExCoin.getData().getMarketPairs()){
                    if(marketPair.getQuote().getUSD().getVolume24h()<mVolumeLimit){
                        toDelete.add(marketPair);
                    }
                }
                stockExCoin.getData().getMarketPairs().removeAll(toDelete);

                Log.d("ListSize 2 ", Integer.toString(stockExCoin.getData().getMarketPairs().size()));


                for(MarketPair marketPair: stockExCoin.getData().getMarketPairs()){
                  if (!marketPair.getMarketPairBase().getCurrencySymbol().equals(mCoinSymbol)){
                      marketPair.getQuote().getUSD().setPrice(marketPair.getQuote().getUSD().getPriceQuote());
                  }
                }

                Collections.sort(stockExCoin.getData().getMarketPairs());

            }
            catch (Exception e){
               Log.d("Exception ", e.toString());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            mRecyclerView.setVisibility(View.VISIBLE);
            loadingView();
            if(stockExCoin != null) {
                adapter = new DataAdapter(getContext(), stockExCoin.getData().getMarketPairs());
                mRecyclerView.setAdapter(adapter);
                doSearch(mSearch);
            }else{
                mDownloadInfo.setVisibility(View.VISIBLE);
                mRefreshButton.setVisibility(View.VISIBLE);
                mRecyclerView.setVisibility(View.INVISIBLE);
            }

        }
    }

}