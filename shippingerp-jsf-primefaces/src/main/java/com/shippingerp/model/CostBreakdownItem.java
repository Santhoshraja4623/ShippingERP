package com.shippingerp.model;

import java.math.BigDecimal;

public class CostBreakdownItem {
    private String costType;
    private BigDecimal totalAmount;

    public CostBreakdownItem(String costType, BigDecimal totalAmount) {
        this.costType = costType;
        this.totalAmount = totalAmount;
    }
    public String getCostType() { return costType; }
    public BigDecimal getTotalAmount() { return totalAmount; }
}
