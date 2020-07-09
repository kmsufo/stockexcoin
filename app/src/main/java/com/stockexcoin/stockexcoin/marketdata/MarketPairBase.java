package com.stockexcoin.stockexcoin.marketdata;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MarketPairBase { //1st currency


    @SerializedName("exchange_symbol")
    @Expose
    private String exchangeSymbol; // BTC
    @SerializedName("currency_id")
    @Expose
    private int currencyId; // BTC = 1
    @SerializedName("currency_symbol")
    @Expose
    private String currencySymbol; // BTC
    @SerializedName("currency_type")
    @Expose
    private String currencyType; // Cryptocurrency

    /**
     * No args constructor for use in serialization
     *
     */
    public MarketPairBase() {
    }

    /**
     *
     * @param currencyType
     * @param exchangeSymbol
     * @param currencySymbol
     * @param currencyId
     */
    public MarketPairBase(String exchangeSymbol, int currencyId, String currencySymbol, String currencyType) {
        super();
        this.exchangeSymbol = exchangeSymbol;
        this.currencyId = currencyId;
        this.currencySymbol = currencySymbol;
        this.currencyType = currencyType;
    }

    public String getExchangeSymbol() {
        return exchangeSymbol;
    }

    public void setExchangeSymbol(String exchangeSymbol) {
        this.exchangeSymbol = exchangeSymbol;
    }

    public int getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(int currencyId) {
        this.currencyId = currencyId;
    }

    public String getCurrencySymbol() {
        return currencySymbol;
    }

    public void setCurrencySymbol(String currencySymbol) {
        this.currencySymbol = currencySymbol;
    }

    public String getCurrencyType() {
        return currencyType;
    }

    public void setCurrencyType(String currencyType) {
        this.currencyType = currencyType;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("exchangeSymbol: ").append(exchangeSymbol).append("\n")
                .append("currencyId: ").append(currencyId).append("\n")
                .append("currencySymbol: ").append(currencySymbol).append("\n")
                .append("currencyType: ").append(currencyType).append("\n").toString();
    }
}
