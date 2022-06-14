package com.example.demoserver.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Invoice {

    @SerializedName("id")
    int id;

    @SerializedName("cname")
    String customerName;

    @SerializedName("orgId")
    int orgId;

    @SerializedName("createdAt")
    String createdAt;

    @SerializedName("orders")
    List<Order> orders;

    @SerializedName("discount")
    float discount;

    @SerializedName("tax")
    float tax;

    float totalPrice=0;


    public Invoice(int id, String customerName, int orgId, String createdAt, List<Order> orders, float discount, float tax) {
        this.id = id;
        this.customerName = customerName;
        this.orgId = orgId;
        this.createdAt = createdAt;
        this.orders = orders;
        this.discount = discount;
        this.tax = tax;
    }

    public Invoice() {
    }

    public float getTotalPrice() {
        for (Order order:orders) {
            totalPrice = totalPrice + order.getPrice();
        }
        return totalPrice;
    }

    public float applyTax(){
        getTotalPrice();
        return  totalPrice+(totalPrice*tax)/100;
    }

    public float applyDiscount(){
        getTotalPrice();
        return totalPrice-(totalPrice*discount)/100;
    }

    public float getDiscount() {
        return discount;
    }

    public void setDiscount(float discount) {
        this.discount = discount;
    }

    public float getTax() {
        return tax;
    }

    public void setTax(float tax) {
        this.tax = tax;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public int getOrgId() {
        return orgId;
    }

    public void setOrgId(int orgId) {
        this.orgId = orgId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}
