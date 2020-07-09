package com.stockexcoin.stockexcoin.marketdata;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MarketPair implements Comparable<MarketPair> {
    @SerializedName("exchange")
    @Expose
    private Exchange exchange;
    @SerializedName("outlier_detected")
    @Expose
    private int outlierDetected; // trash
    @SerializedName("exclusions")
    @Expose
    private Object exclusions; // "price" or null
    @SerializedName("market_pair_base")
    @Expose
    private MarketPairBase marketPairBase;
    @SerializedName("market_pair_quote")
    @Expose
    private MarketPairQuote marketPairQuote;
    @SerializedName("quote")
    @Expose
    private Quote quote;
    @SerializedName("market_id")
    @Expose
    private int marketId; // 44248
    @SerializedName("market_pair")
    @Expose
    private String marketPair; // BTC/USDT
    @SerializedName("market_url")
    @Expose
    private String marketUrl; // https://www.etherflyer.com/trade.html?pairs=BTC-USDT

    /**
     * No args constructor for use in serialization
     *
     */
    public MarketPair() {
    }

    /**
     *
     * @param quote
     * @param marketPair
     * @param marketUrl
     * @param marketPairBase
     * @param exchange
     * @param exclusions
     * @param outlierDetected
     * @param marketPairQuote
     * @param marketId
     */
    public MarketPair(Exchange exchange, int outlierDetected, Object exclusions, MarketPairBase marketPairBase, MarketPairQuote marketPairQuote, Quote quote, int marketId, String marketPair, String marketUrl) {
        super();
        this.exchange = exchange;
        this.outlierDetected = outlierDetected;
        this.exclusions = exclusions;
        this.marketPairBase = marketPairBase;
        this.marketPairQuote = marketPairQuote;
        this.quote = quote;
        this.marketId = marketId;
        this.marketPair = marketPair;
        this.marketUrl = marketUrl;
    }

    public Exchange getExchange() {
        return exchange;
    }

    public void setExchange(Exchange exchange) {
        this.exchange = exchange;
    }

    public int getOutlierDetected() {
        return outlierDetected;
    }

    public void setOutlierDetected(int outlierDetected) {
        this.outlierDetected = outlierDetected;
    }

    public Object getExclusions() {
        return exclusions;
    }

    public void setExclusions(Object exclusions) {
        this.exclusions = exclusions;
    }

    public MarketPairBase getMarketPairBase() {
        return marketPairBase;
    }

    public void setMarketPairBase(MarketPairBase marketPairBase) {
        this.marketPairBase = marketPairBase;
    }

    public MarketPairQuote getMarketPairQuote() {
        return marketPairQuote;
    }

    public void setMarketPairQuote(MarketPairQuote marketPairQuote) {
        this.marketPairQuote = marketPairQuote;
    }

    public Quote getQuote() {
        return quote;
    }

    public void setQuote(Quote quote) {
        this.quote = quote;
    }

    public int getMarketId() {
        return marketId;
    }

    public void setMarketId(int marketId) {
        this.marketId = marketId;
    }

    public String getMarketPair() {
        return marketPair;
    }

    public void setMarketPair(String marketPair) {
        this.marketPair = marketPair;
    }

    public String getMarketUrl() {
        return marketUrl;
    }

    public void setMarketUrl(String marketUrl) {
        this.marketUrl = marketUrl;
    }


    @Override
    public String toString() {
        return new StringBuilder()
                .append("exchange: ").append(exchange).append("\n")
                .append("outlierDetected: ").append(outlierDetected).append("\n")
                .append("exclusions: ").append(exclusions).append("\n")
                .append("marketPairBase: ").append(marketPairBase).append("\n")
                .append("marketPairQuote: ").append(marketPairQuote).append("\n")
                .append("quote: ").append(quote)
                .append("marketId: ").append(marketId).append("\n")
                .append("marketPair: ").append(marketPair).append("\n")
                .append("marketUrl: ").append(marketUrl).append("\n").append("\n").toString();
    }

    @Override
    public int compareTo(MarketPair o) {
        return Double.compare(this.getQuote().getUSD().getPrice(),o.getQuote().getUSD().getPrice());
    }
}
