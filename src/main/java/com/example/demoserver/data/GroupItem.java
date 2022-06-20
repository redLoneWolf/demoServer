package com.example.demoserver.data;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;
import java.util.List;

public class GroupItem {
    private Integer id;
    private String name;
    private Integer orgId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Item> items ;

    public GroupItem(Integer id, String name, List<Item> items, Integer orgId) {
        this.id = id;
        this.name = name;
        this.items = items;
        this.orgId = orgId;
    }

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    public GroupItem() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "GroupItem{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", orgId=" + orgId +
                ", items=" + items +
                '}';
    }
}
