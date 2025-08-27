package com.shippingerp.model;



import java.util.Date;

public class VoyageDetail {
    private int voyageId;
    private int shipId;
    private String shipName;
    private String route;
    private Date startDate;
    private Date endDate;
    private int distance;

    public VoyageDetail() {
    }

    public VoyageDetail(int voyageId, int shipId, String shipName, String route, Date startDate, Date endDate, int distance) {
        this.voyageId = voyageId;
        this.shipId = shipId;
        this.shipName = shipName;
        this.route = route;
        this.startDate = startDate;
        this.endDate = endDate;
        this.distance = distance;
    }

    public int getVoyageId() {
        return voyageId;
    }

    public void setVoyageId(int voyageId) {
        this.voyageId = voyageId;
    }

    public int getShipId() {
        return shipId;
    }

    public void setShipId(int shipId) {
        this.shipId = shipId;
    }

    public String getShipName() {
        return shipName;
    }

    public void setShipName(String shipName) {
        this.shipName = shipName;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }
}
