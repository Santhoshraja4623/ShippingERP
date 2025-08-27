package com.shippingerp.model;



public class FactorName {
    private int factorNameId;
    private String name;

    public FactorName() {
    }

    public FactorName(int factorNameId, String name) {
        this.factorNameId = factorNameId;
        this.name = name;
    }

    public int getFactorNameId() {
        return factorNameId;
    }

    public void setFactorNameId(int factorNameId) {
        this.factorNameId = factorNameId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
