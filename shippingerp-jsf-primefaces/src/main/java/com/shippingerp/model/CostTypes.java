package com.shippingerp.model;


public class CostTypes {
    private int costTypeId;
    private String name;

    public CostTypes() {
    }

    public CostTypes(int costTypeId, String name) {
        this.costTypeId = costTypeId;
        this.name = name;
    }

    public int getCostTypeId() {
        return costTypeId;
    }

    public void setCostTypeId(int costTypeId) {
        this.costTypeId = costTypeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
