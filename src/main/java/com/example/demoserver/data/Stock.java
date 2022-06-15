package com.example.demoserver.data;


public class Stock {


    int id;


    int warId;


    int itemId;


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
