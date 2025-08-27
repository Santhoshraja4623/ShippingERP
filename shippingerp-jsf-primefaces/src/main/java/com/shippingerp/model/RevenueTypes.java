package com.shippingerp.model;



public class RevenueTypes {
    private int revenueTypeId;
    private String description;

    public RevenueTypes() {
    }

    public RevenueTypes(int revenueTypeId, String description) {
        this.revenueTypeId = revenueTypeId;
        this.description = description;
    }

    public int getRevenueTypeId() {
        return revenueTypeId;
    }

    public void setRevenueTypeId(int revenueTypeId) {
        this.revenueTypeId = revenueTypeId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
