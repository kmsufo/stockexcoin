package com.stockexcoin.stockexcoin;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.stockexcoin.stockexcoin.coindata.CoinPrice;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class StockExCoinService extends Service {
    private final String CHANNEL_ID = "CHANNEL №1";
    private final String WEB_PAGE = "https://web-api.coinmarketcap.com/v1/cryptocurrency/listings/latest?limit=2&start=1";
    private final double PRICE_SPIKE_LIMIT_BTC = 1;
    private final double PRICE_SPIKE_LIMIT_ETH = 1;
    private final int REFRESH_RATE = 1800000;
    private boolean mServiceState = false;
    private boolean mNotificationChannelCreated = false;
    DownloadData mDownloadData;

    public boolean isEnabled(){
        return mServiceState;
    }

    public void setState(boolean state){
        mServiceState = state;
    }



    public StockExCoinService() {
    }

    @Override
    public void onCreate() {
        Log.d("OnCreate", "Service");
        Toast.makeText(this, R.string.service_created,
                Toast.LENGTH_SHORT).show();
        if(!mNotificationChannelCreated){
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, getString(R.string.notification_name),
                    NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(getString(R.string.notification_description));
            channel.enableLights(true);
            channel.setLightColor(Color.RED);
            channel.enableVibration(true);
            assert notificationManager != null;
            notificationManager.createNotificationChannel(channel);
        }
        }
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("OnStart", "Service");
        if(!isEnabled()){
        mDownloadData = new DownloadData();
        mDownloadData.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);}
        setState(true);

        Toast.makeText(this, R.string.service_started,
                Toast.LENGTH_SHORT).show();

        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        Log.d("OnDestroy", "Service");
        mDownloadData.cancel(true);
        setState(false);
        Toast.makeText(this, R.string.service_stopped,
                Toast.LENGTH_SHORT).show();
        super.onDestroy();
    }


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    class DownloadData extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            Log.d("CoinPrice", "start");
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            while (mServiceState) {
                try(InputStream is = new URL(WEB_PAGE).openStream();
                    Reader reader = new InputStreamReader(is, StandardCharsets.UTF_8)){
                    GsonBuilder gsonBuilder = new GsonBuilder();
                    gsonBuilder.setDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                    NumberFormat formatter = new DecimalFormat("#0.00");
                    Gson gson = gsonBuilder.create();
                    CoinPrice mCoinPrice = gson.fromJson(reader, CoinPrice.class);
                    if(mCoinPrice.getData().get(0).getQuote().getUSD().getPercentChange1h()<=-PRICE_SPIKE_LIMIT_BTC ||
                            mCoinPrice.getData().get(0).getQuote().getUSD().getPercentChange1h()>=PRICE_SPIKE_LIMIT_BTC){
                        Intent resultIntent = new Intent(getBaseContext(), MainActivity.class);
                        PendingIntent resultPendingIntent = PendingIntent.getActivity(getBaseContext(), 0, resultIntent,
                                PendingIntent.FLAG_UPDATE_CURRENT);

                        NotificationCompat.Builder builder =
                                new NotificationCompat.Builder(getBaseContext(), CHANNEL_ID)
                                        .setSmallIcon(R.drawable.ic_launcher_foreground).setColor(getResources().getColor(R.color.colorPrimaryDark))
                                        .setContentTitle(mCoinPrice.getData().get(0).getName() +" "+ formatter.format(mCoinPrice.getData().get(0).getQuote().getUSD().getPrice())+"$")
                                        .setContentText(mCoinPrice.getData().get(0).getName()+
                                                        getString(R.string.notification_text1) +
                                                        formatter.format(mCoinPrice.getData().get(0).getQuote().getUSD().getPercentChange1h())+"%" +
                                                        getString(R.string.notification_text2))
                                        .setAutoCancel(true)
                                        .setContentIntent(resultPendingIntent);
                        Notification notification = builder.build();
                        NotificationManager notificationManager =
                                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                        notificationManager.notify(1, notification);
                    }
                    if(mCoinPrice.getData().get(1).getQuote().getUSD().getPercentChange1h()<=-PRICE_SPIKE_LIMIT_ETH ||
                            mCoinPrice.getData().get(1).getQuote().getUSD().getPercentChange1h()>=PRICE_SPIKE_LIMIT_ETH){
                        Intent resultIntent = new Intent(getBaseContext(), MainActivity.class);
                        PendingIntent resultPendingIntent = PendingIntent.getActivity(getBaseContext(), 0, resultIntent,
                                PendingIntent.FLAG_UPDATE_CURRENT);

                        NotificationCompat.Builder builder =
                                new NotificationCompat.Builder(getBaseContext(), CHANNEL_ID)
                                        .setSmallIcon(R.drawable.ic_launcher_foreground).setColor(getResources().getColor(R.color.colorPrimaryDark))
                                        .setContentTitle(mCoinPrice.getData().get(1).getName()+" "+formatter.format(mCoinPrice.getData().get(1).getQuote().getUSD().getPrice())+"$")
                                        .setContentText(mCoinPrice.getData().get(1).getName()+
                                                        getString(R.string.notification_text1) +
                                                        formatter.format(mCoinPrice.getData().get(1).getQuote().getUSD().getPercentChange1h())+"%" +
                                                        getString(R.string.notification_text2))
                                        .setAutoCancel(true)
                                        .setContentIntent(resultPendingIntent);
                        Notification notification = builder.build();
                        NotificationManager notificationManager =
                                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                        notificationManager.notify(2, notification);

                    }
                    Log.d("CoinPrice", Long.toString(System.currentTimeMillis()));

                    Thread.sleep(REFRESH_RATE);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            Log.d("CoinPrice", "stop");
            super.onPostExecute(result);
        }
    }

}
