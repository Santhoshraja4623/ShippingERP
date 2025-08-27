package com.shippingerp.model;

public class Ship {
    private int shipID;
    private String name;

    public Ship(int shipID, String name) {
        this.shipID = shipID;
        this.name = name;
    }
    public int getShipID() { return shipID; }
    public String getName() { return name; }
}
