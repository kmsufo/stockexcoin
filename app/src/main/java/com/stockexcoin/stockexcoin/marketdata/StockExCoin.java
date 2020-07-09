package com.stockexcoin.stockexcoin.marketdata;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StockExCoin {

    @SerializedName("data")
    @Expose
    private Data data;

    /**
     * No args constructor for use in serialization
     *
     */
    public StockExCoin() {
    }

    /**
     *
     * @param data
     */
    public StockExCoin(Data data) {
        super();
        this.data = data;
    }


    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return new StringBuilder().append(data).append("\n").toString();
    }

}
