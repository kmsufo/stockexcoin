package com.stockexcoin.stockexcoin.coindata;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class USD {

    @SerializedName("price")
    @Expose
    private double price;
    @SerializedName("volume_24h")
    @Expose
    private double volume24h;
    @SerializedName("percent_change_1h")
    @Expose
    private double percentChange1h;
    @SerializedName("percent_change_24h")
    @Expose
    private double percentChange24h;
    @SerializedName("percent_change_7d")
    @Expose
    private double percentChange7d;
    @SerializedName("market_cap")
    @Expose
    private double marketCap;
    @SerializedName("last_updated")
    @Expose
    private String lastUpdated;

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getVolume24h() {
        return volume24h;
    }

    public void setVolume24h(double volume24h) {
        this.volume24h = volume24h;
    }

    public double getPercentChange1h() {
        return percentChange1h;
    }

    public void setPercentChange1h(double percentChange1h) {
        this.percentChange1h = percentChange1h;
    }

    public double getPercentChange24h() {
        return percentChange24h;
    }

    public void setPercentChange24h(double percentChange24h) {
        this.percentChange24h = percentChange24h;
    }

    public double getPercentChange7d() {
        return percentChange7d;
    }

    public void setPercentChange7d(double percentChange7d) {
        this.percentChange7d = percentChange7d;
    }

    public double getMarketCap() {
        return marketCap;
    }

    public void setMarketCap(double marketCap) {
        this.marketCap = marketCap;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    @Override
    public String toString() {
        return new StringBuilder().append("price"+ price).append("volume24h"+ volume24h).append("percentChange1h"+ percentChange1h).append("percentChange24h"+ percentChange24h).append("percentChange7d"+ percentChange7d).append("marketCap"+ marketCap).append("lastUpdated"+ lastUpdated).toString();
    }

}