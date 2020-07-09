package com.stockexcoin.stockexcoin.marketdata;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class ExchangeReported {

    @SerializedName("price")
    @Expose
    private double price; // 5186.234
    @SerializedName("volume_24h_base")
    @Expose
    private double volume24hBase; // 1171726.18958875
    @SerializedName("volume_24h_quote")
    @Expose
    private double volume24hQuote; // 6067758462.69409
    @SerializedName("last_updated")
    @Expose
    private Date lastUpdated; // 2020-03-13T13:30:34.000Z

    /**
     * No args constructor for use in serialization
     *
     */
    public ExchangeReported() {
    }

    /**
     *
     * @param lastUpdated
     * @param volume24hBase
     * @param price
     * @param volume24hQuote
     */
    public ExchangeReported(double price, double volume24hBase, double volume24hQuote, Date lastUpdated) {
        super();
        this.price = price;
        this.volume24hBase = volume24hBase;
        this.volume24hQuote = volume24hQuote;
        this.lastUpdated = lastUpdated;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getVolume24hBase() {
        return volume24hBase;
    }

    public void setVolume24hBase(double volume24hBase) {
        this.volume24hBase = volume24hBase;
    }

    public double getVolume24hQuote() {
        return volume24hQuote;
    }

    public void setVolume24hQuote(double volume24hQuote) {
        this.volume24hQuote = volume24hQuote;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("price: ").append(price).append("\n")
                .append("volume24hBase: ").append(volume24hBase).append("\n")
                .append("volume24hQuote: ").append(volume24hQuote).append("\n")
                .append("lastUpdated: ").append(lastUpdated).append("\n").toString();
    }
}
