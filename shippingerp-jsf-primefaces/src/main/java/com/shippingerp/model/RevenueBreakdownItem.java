package com.shippingerp.model;

import java.math.BigDecimal;

public class RevenueBreakdownItem {
    private String revenueType;
    private BigDecimal totalAmount;

    public RevenueBreakdownItem(String revenueType, BigDecimal totalAmount) {
        this.revenueType = revenueType;
        this.totalAmount = totalAmount;
    }

    public String getRevenueType() { return revenueType; }
    public BigDecimal getTotalAmount() { return totalAmount; }
}
