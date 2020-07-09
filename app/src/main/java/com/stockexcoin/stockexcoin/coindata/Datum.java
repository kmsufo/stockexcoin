package com.stockexcoin.stockexcoin.coindata;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("symbol")
    @Expose
    private String symbol;
    @SerializedName("slug")
    @Expose
    private String slug;
    @SerializedName("num_market_pairs")
    @Expose
    private int numMarketPairs;
    @SerializedName("date_added")
    @Expose
    private String dateAdded;
    @SerializedName("tags")
    @Expose
    private List<String> tags = null;
    @SerializedName("max_supply")
    @Expose
    private Object maxSupply;
    @SerializedName("circulating_supply")
    @Expose
    private double circulatingSupply;
    @SerializedName("total_supply")
    @Expose
    private double totalSupply;
    @SerializedName("platform")
    @Expose
    private Object platform;
    @SerializedName("cmc_rank")
    @Expose
    private int cmcRank;
    @SerializedName("last_updated")
    @Expose
    private String lastUpdated;
    @SerializedName("quote")
    @Expose
    private Quote quote;

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

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public int getNumMarketPairs() {
        return numMarketPairs;
    }

    public void setNumMarketPairs(int numMarketPairs) {
        this.numMarketPairs = numMarketPairs;
    }

    public String getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(String dateAdded) {
        this.dateAdded = dateAdded;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public Object getMaxSupply() {
        return maxSupply;
    }

    public void setMaxSupply(Object maxSupply) {
        this.maxSupply = maxSupply;
    }

    public double getCirculatingSupply() {
        return circulatingSupply;
    }

    public void setCirculatingSupply(double circulatingSupply) {
        this.circulatingSupply = circulatingSupply;
    }

    public double getTotalSupply() {
        return totalSupply;
    }

    public void setTotalSupply(double totalSupply) {
        this.totalSupply = totalSupply;
    }

    public Object getPlatform() {
        return platform;
    }

    public void setPlatform(Object platform) {
        this.platform = platform;
    }

    public int getCmcRank() {
        return cmcRank;
    }

    public void setCmcRank(int cmcRank) {
        this.cmcRank = cmcRank;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public Quote getQuote() {
        return quote;
    }

    public void setQuote(Quote quote) {
        this.quote = quote;
    }

    @Override
    public String toString() {
        return new StringBuilder().append("id"+ id).append("name"+ name).append("symbol"+ symbol).append("slug"+ slug).append("numMarketPairs"+ numMarketPairs).append("dateAdded"+ dateAdded).append("tags"+ tags).append("maxSupply"+ maxSupply).append("circulatingSupply"+ circulatingSupply).append("totalSupply"+ totalSupply).append("platform"+ platform).append("cmcRank"+ cmcRank).append("lastUpdated"+ lastUpdated).append("quote"+ quote).toString();
    }

}