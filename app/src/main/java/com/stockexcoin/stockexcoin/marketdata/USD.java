package com.stockexcoin.stockexcoin.marketdata;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class USD {
    @SerializedName("price")
    @Expose
    private double price; // 5168.80309078765
    @SerializedName("price_quote")
    @Expose
    private double priceQuote; // 5168.80309078765
    @SerializedName("volume_24h")
    @Expose
    private double volume24h; // 6056421950.30316
    @SerializedName("effective_liquidity")
    @Expose
    private double effectiveLiquidity; // 747710.2032670834
    @SerializedName("last_updated")
    @Expose
    private Date lastUpdated; // 2020-03-13T13:30:34.000Z

    /**
     * No args constructor for use in serialization
     *
     */
    public USD() {
    }

    /**
     *
     * @param effectiveLiquidity
     * @param lastUpdated
     * @param price
     * @param volume24h
     * @param priceQuote
     */
    public USD(double price, int volume24h, double effectiveLiquidity, Date lastUpdated, double priceQuote) {
        super();
        this.price = price;
        this.volume24h = volume24h;
        this.effectiveLiquidity = effectiveLiquidity;
        this.lastUpdated = lastUpdated;
        this.priceQuote = priceQuote;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getPriceQuote() {
        return priceQuote;
    }

    public void setPriceQuote(double priceQuote) {
        this.priceQuote = priceQuote;
    }

    public int getVolume24h() {
        return (int)volume24h;
    }

    public void setVolume24h(int volume24h) {
        this.volume24h = volume24h;
    }

    public double getEffectiveLiquidity() {
        return effectiveLiquidity;
    }

    public void setEffectiveLiquidity(double effectiveLiquidity) {
        this.effectiveLiquidity = effectiveLiquidity;
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
                .append("volume24h: ").append(volume24h).append("\n")
                .append("effectiveLiquidity: ").append(effectiveLiquidity).append("\n")
                .append("lastUpdated: ").append(lastUpdated).append("\n")
                .append("priceQuote: ").append(priceQuote).append("\n").toString();
    }

}
