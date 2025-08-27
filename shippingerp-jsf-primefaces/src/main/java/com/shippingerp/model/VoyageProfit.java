package com.shippingerp.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class VoyageProfit {
    private int voyageID;
    private String shipName;
    private String route;
    private LocalDate startDate;
    private LocalDate endDate;
    private int distance;
    private BigDecimal totalRevenue;
    private BigDecimal totalCost;
    private BigDecimal profit;

    // Static date formatter for consistent display
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    // Constructor
    public VoyageProfit(int voyageID, String shipName, String route, LocalDate startDate, LocalDate endDate,
                        int distance, BigDecimal totalRevenue, BigDecimal totalCost, BigDecimal profit) {
        this.voyageID = voyageID;
        this.shipName = shipName;
        this.route = route;
        this.startDate = startDate;
        this.endDate = endDate;
        this.distance = distance;
        this.totalRevenue = totalRevenue;
        this.totalCost = totalCost;
        this.profit = profit;
    }

    // Getters
    public int getVoyageID() {
        return voyageID;
    }

    public String getShipName() {
        return shipName;
    }

    public String getRoute() {
        return route;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public int getDistance() {
        return distance;
    }

    public BigDecimal getTotalRevenue() {
        return totalRevenue;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public BigDecimal getProfit() {
        return profit;
    }

    // Formatted getters for JSF display
    public String getStartDateFormatted() {
        return startDate != null ? startDate.format(FORMATTER) : "";
    }

    public String getEndDateFormatted() {
        return endDate != null ? endDate.format(FORMATTER) : "";
    }
}
