package com.example.demoserver.data;

import com.google.gson.annotations.SerializedName;

public class Stock {

    @SerializedName("id")
    int id;

    @SerializedName("warId")
    int warId;

    @SerializedName("itemId")
    int itemId;

    @SerializedName("count")
    Integer count;

    public Stock(int id, int warId, int itemId, int count) {
        this.id = id;
        this.warId = warId;
        this.itemId = itemId;
        this.count = count;
    }

    public Stock() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getWarId() {
        return warId;
    }

    public void setWarId(int warId) {
        this.warId = warId;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public Integer getCount() {
        return count;
    }

    public void  setCount(Integer count) {
        this.count = count;
    }
}
