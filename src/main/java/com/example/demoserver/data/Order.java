package com.example.demoserver.data;

import com.google.gson.annotations.SerializedName;

public class Order {

    @SerializedName("id")
    int id;

    @SerializedName("invoiceId")
    int invoiceId;

    @SerializedName("cname")
    String customerName;

    @SerializedName("itemId")
    int itemId;

    @SerializedName("orgId")
    int orgId;

    @SerializedName("price")
    int price;

    @SerializedName("createdAt")
    String createdAt;

    public Order(int id, int invoiceId, String customerName, int itemId, int orgId, int price, String createdAt) {
        this.id = id;
        this.invoiceId = invoiceId;
        this.customerName = customerName;
        this.itemId = itemId;
        this.orgId = orgId;
        this.price = price;
        this.createdAt = createdAt;
    }

    public Order() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(int invoiceId) {
        this.invoiceId = invoiceId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getOrgId() {
        return orgId;
    }

    public void setOrgId(int orgId) {
        this.orgId = orgId;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
