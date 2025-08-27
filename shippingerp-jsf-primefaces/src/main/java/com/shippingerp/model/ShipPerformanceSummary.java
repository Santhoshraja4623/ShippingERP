package com.shippingerp.model;

import java.math.BigDecimal;

public class ShipPerformanceSummary {
    private String shipName;
    private int voyageCount;
    private BigDecimal totalRevenue;
    private BigDecimal totalCost;
    private BigDecimal totalProfit;

    public ShipPerformanceSummary(String shipName, int voyageCount, BigDecimal totalRevenue, BigDecimal totalCost, BigDecimal totalProfit) {
        this.shipName = shipName;
        this.voyageCount = voyageCount;
        this.totalRevenue = totalRevenue;
        this.totalCost = totalCost;
        this.totalProfit = totalProfit;
    }
    public String getShipName() { return shipName; }
    public int getVoyageCount() { return voyageCount; }
    public BigDecimal getTotalRevenue() { return totalRevenue; }
    public BigDecimal getTotalCost() { return totalCost; }
    public BigDecimal getTotalProfit() { return totalProfit; }
}
