package com.example.demoserver.data;

import com.google.gson.annotations.SerializedName;

public class Stocks {

    @SerializedName("id")
    int id;

    @SerializedName("warId")
    int warId;

    @SerializedName("itemId")
    int itemId;

    @SerializedName("count")
    int count;

    public Stocks(int id, int warId, int itemId, int count) {
        this.id = id;
        this.warId = warId;
        this.itemId = itemId;
        this.count = count;
    }

    public Stocks() {
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

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
