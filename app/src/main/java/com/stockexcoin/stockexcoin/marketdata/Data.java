package com.stockexcoin.stockexcoin.marketdata;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("symbol")
    @Expose
    private String symbol;
    @SerializedName("market_pairs")
    @Expose
    private List<MarketPair> marketPairs = null;

    /**
     * No args constructor for use in serialization
     *
     */
    public Data() {
    }

    /**
     *
     * @param symbol
     * @param name
     * @param marketPairs
     * @param id
     */
    public Data(int id, String name, String symbol, List<MarketPair> marketPairs) {
        super();
        this.id = id; // 1
        this.name = name; // Bitcoin
        this.symbol = symbol; //BTC
        this.marketPairs = marketPairs; // List
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public List<MarketPair> getMarketPairs() {
        return marketPairs;
    }

    public void setMarketPairs(List<MarketPair> marketPairs) {
        this.marketPairs = marketPairs;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("Name: ").append(name).append("\n")
                .append("Symbol: ").append(symbol).append("\n")
                .append("MarketPairs: ").append(marketPairs).append("\n").toString();
    }

}
