package com.shippingerp.model;

public class FuelEfficiencyImpact {
    private int voyageID;
    private String shipName;
    private double fuelEfficiency;
    private double distance;
    private double estimatedGallons;
    private double fuelPricePerGallon;
    private double estimatedFuelCost;
    private double profit;
    private Double profitPerGallon;  // Nullable value

    // Getters and setters
    public int getVoyageID() {
        return voyageID;
    }

    public void setVoyageID(int voyageID) {
        this.voyageID = voyageID;
    }

    public String getShipName() {
        return shipName;
    }

    public void setShipName(String shipName) {
        this.shipName = shipName;
    }

    public double getFuelEfficiency() {
        return fuelEfficiency;
    }

    public void setFuelEfficiency(double fuelEfficiency) {
        this.fuelEfficiency = fuelEfficiency;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double getEstimatedGallons() {
        return estimatedGallons;
    }

    public void setEstimatedGallons(double estimatedGallons) {
        this.estimatedGallons = estimatedGallons;
    }

    public double getFuelPricePerGallon() {
        return fuelPricePerGallon;
    }

    public void setFuelPricePerGallon(double fuelPricePerGallon) {
        this.fuelPricePerGallon = fuelPricePerGallon;
    }

    public double getEstimatedFuelCost() {
        return estimatedFuelCost;
    }

    public void setEstimatedFuelCost(double estimatedFuelCost) {
        this.estimatedFuelCost = estimatedFuelCost;
    }

    public double getProfit() {
        return profit;
    }

    public void setProfit(double profit) {
        this.profit = profit;
    }

    public Double getProfitPerGallon() {
        return profitPerGallon;
    }

    public void setProfitPerGallon(Double profitPerGallon) {
        this.profitPerGallon = profitPerGallon;
    }
}
