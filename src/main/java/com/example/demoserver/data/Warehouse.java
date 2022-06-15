package com.example.demoserver.data;



public class Warehouse {

    int id;
    String name;
    int orgId;

    public Warehouse(int id, String name, int orgId) {
        this.id = id;
        this.name = name;
        this.orgId = orgId;
    }

    public Warehouse() {
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

    public int getOrgId() {
        return orgId;
    }

    public void setOrgId(int orgId) {
        this.orgId = orgId;
    }
}
