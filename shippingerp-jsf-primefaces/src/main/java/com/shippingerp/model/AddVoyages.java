package com.shippingerp.model;

import java.time.LocalDate;

public class AddVoyages {
    private int shipID; // for existing ship selection
    private LocalDate startDate;
    private LocalDate endDate;
    private String route;
    private int distance;

    // New Ship fields for adding a new ship
    private String newShipName;
    private Integer newShipCapacity;
    private Double newShipFuelEfficiency;

    // Getters and Setters
    public int getShipID() { return shipID; }
    public void setShipID(int shipID) { this.shipID = shipID; }

    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }

    public String getRoute() { return route; }
    public void setRoute(String route) { this.route = route; }

    public int getDistance() { return distance; }
    public void setDistance(int distance) { this.distance = distance; }

    public String getNewShipName() { return newShipName; }
    public void setNewShipName(String newShipName) { this.newShipName = newShipName; }

    public Integer getNewShipCapacity() { return newShipCapacity; }
    public void setNewShipCapacity(Integer newShipCapacity) { this.newShipCapacity = newShipCapacity; }

    public Double getNewShipFuelEfficiency() { return newShipFuelEfficiency; }
    public void setNewShipFuelEfficiency(Double newShipFuelEfficiency) { this.newShipFuelEfficiency = newShipFuelEfficiency; }
}
