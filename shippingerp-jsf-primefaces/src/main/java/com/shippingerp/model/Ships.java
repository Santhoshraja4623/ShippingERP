package com.shippingerp.model;

public class Ships {
    private int shipId;
    private String name;
    private int capacity;
    private double fuelEfficiency;

    // No-arg constructor (required for JSF)
    public Ships() {
    }

    // Parameterized constructor (optional convenience)
    public Ships(int shipId, String name, int capacity, double fuelEfficiency) {
        this.shipId = shipId;
        this.name = name;
        this.capacity = capacity;
        this.fuelEfficiency = fuelEfficiency;
    }

    // Getters and setters
    public int getShipId() {
        return shipId;
    }

    public void setShipId(int shipId) {
        this.shipId = shipId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public double getFuelEfficiency() {
        return fuelEfficiency;
    }

    public void setFuelEfficiency(double fuelEfficiency) {
        this.fuelEfficiency = fuelEfficiency;
    }

    // Override equals() and hashCode() for proper row selection in JSF
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Ships other = (Ships) obj;
        return shipId == other.shipId;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(shipId);
    }
}
