package com.example.demoserver.data;

import com.google.gson.annotations.SerializedName;

public class Warehouse {

    @SerializedName("id")
    int id;

    @SerializedName("name")
    String name;

    @SerializedName("orgId")
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
