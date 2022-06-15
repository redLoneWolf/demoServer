package com.example.demoserver.data;



public class Order {


    int id;
    Integer invoiceId;
    String customerName;
    Integer itemId;
    Integer orgId;
    Integer price;
    Integer quantity;
    String createdAt;

    public Order(int id, int invoiceId, String customerName, int itemId, int orgId, int price,Integer quantity, String createdAt) {
        this.id = id;
        this.invoiceId = invoiceId;
        this.customerName = customerName;
        this.itemId = itemId;
        this.orgId = orgId;
        this.price = price;
        this.createdAt = createdAt;
        this.quantity=quantity;
    }

    public Order() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Integer invoiceId) {
        this.invoiceId = invoiceId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
