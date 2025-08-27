package com.shippingerp.model;

public class AvgProfitByShip {
    private String shipName;
    private double avgRevenue;
    private double avgCost;
    private double avgProfit;
    private double profitMarginPct;

    // Getters and setters
    public String getShipName() {
        return shipName;
    }

    public void setShipName(String shipName) {
        this.shipName = shipName;
    }

    public double getAvgRevenue() {
        return avgRevenue;
    }

    public void setAvgRevenue(double avgRevenue) {
        this.avgRevenue = avgRevenue;
    }

    public double getAvgCost() {
        return avgCost;
    }

    public void setAvgCost(double avgCost) {
        this.avgCost = avgCost;
    }

    public double getAvgProfit() {
        return avgProfit;
    }

    public void setAvgProfit(double avgProfit) {
        this.avgProfit = avgProfit;
    }

    public double getProfitMarginPct() {
        return profitMarginPct;
    }

    public void setProfitMarginPct(double profitMarginPct) {
        this.profitMarginPct = profitMarginPct;
    }
}
