package com.stockexcoin.stockexcoin.marketdata;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Exchange {
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("slug")
    @Expose
    private String slug;

    /**
     * No args constructor for use in serialization
     *
     */
    public Exchange() {
    }

    /**
     *
     * @param name
     * @param id
     * @param slug
     */
    public Exchange(int id, String name, String slug) {
        super();
        this.id = id; //470
        this.name = name; //EtherFlyer
        this.slug = slug; //etherflyer
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

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("id: ").append(id).append("\n")
                .append("name: ").append(name).append("\n")
                .append("slug: ").append(slug).append("\n").toString();
    }

}
