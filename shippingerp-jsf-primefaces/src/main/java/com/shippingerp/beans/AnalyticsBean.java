package com.shippingerp.beans;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import com.shippingerp.dao.AnalyticsDAO;
import com.shippingerp.model.AvgProfitByShip;
import com.shippingerp.model.FuelEfficiencyImpact;

import java.sql.SQLException;
import java.util.List;

@Named
@RequestScoped
public class AnalyticsBean {

    @Inject
    private AnalyticsDAO analyticsDAO;

    private List<AvgProfitByShip> avgProfitByShipList;
    private List<FuelEfficiencyImpact> fuelEfficiencyImpactList;

    @PostConstruct
public void init() {
    try {
        avgProfitByShipList = analyticsDAO.getAvgProfitByShip();
        fuelEfficiencyImpactList = analyticsDAO.getFuelEfficiencyImpact();

        System.out.println("AvgProfitByShip rows: " + avgProfitByShipList.size());
        System.out.println("FuelEfficiencyImpact rows: " + fuelEfficiencyImpactList.size());

    } catch (SQLException e) {
        e.printStackTrace();
    }
}


    public List<AvgProfitByShip> getAvgProfitByShipList() {
        return avgProfitByShipList;
    }

    public List<FuelEfficiencyImpact> getFuelEfficiencyImpactList() {
        return fuelEfficiencyImpactList;
    }
}
