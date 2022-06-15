package com.example.demoserver.data;



import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

@JsonPropertyOrder({"id","orgId","customerName","createdAt","discount","tax","totalPrice","withTaxDis","orders"})
public class Invoice {

    private int id;
    private String customerName;
    private Integer orgId;
    private String createdAt;
    private Float discount;
    private Float tax;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Order> orders;


    private Float totalPrice= (float) 0;

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


    public Float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public float applyTax(){
//        getTotalPrice();
        return  totalPrice+(totalPrice*tax)/100;
    }

    public float applyDiscount(){
//        getTotalPrice();
        return totalPrice-(totalPrice*discount)/100;
    }

    @JsonProperty("withTaxDis")
    public float totalPriceWithTaxAndDiscount(){

        float temp = totalPrice-(totalPrice*discount)/100;

        return (temp+(temp*tax)/100);
    }

    public Float getDiscount() {
        return discount;
    }

    public void setDiscount(Float discount) {
        this.discount = discount;
    }

    public Float getTax() {
        return tax;
    }

    public void setTax(Float tax) {
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

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
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
