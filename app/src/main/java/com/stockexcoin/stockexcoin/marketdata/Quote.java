package com.stockexcoin.stockexcoin.marketdata;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Quote {

    @SerializedName("exchange_reported")
    @Expose
    private ExchangeReported exchangeReported;
    @SerializedName("USD")
    @Expose
    private USD uSD;

    /**
     * No args constructor for use in serialization
     *
     */
    public Quote() {
    }

    /**
     *
     * @param uSD
     * @param exchangeReported
     */
    public Quote(ExchangeReported exchangeReported, USD uSD) {
        super();
        this.exchangeReported = exchangeReported;
        this.uSD = uSD;
    }

    public ExchangeReported getExchangeReported() {
        return exchangeReported;
    }

    public void setExchangeReported(ExchangeReported exchangeReported) {
        this.exchangeReported = exchangeReported;
    }

    public USD getUSD() {
        return uSD;
    }

    public void setUSD(USD uSD) {
        this.uSD = uSD;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("exchangeReported: ").append(exchangeReported).append("\n")
                .append("USD: ").append(uSD).append("\n").toString();
    }

}
