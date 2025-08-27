package com.shippingerp.dao;

import com.shippingerp.model.AvgProfitByShip;
import com.shippingerp.model.FuelEfficiencyImpact;
import com.shippingerp.util.DBConnection;

import jakarta.enterprise.context.ApplicationScoped;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class AnalyticsDAO {

    public List<AvgProfitByShip> getAvgProfitByShip() throws SQLException {
        List<AvgProfitByShip> list = new ArrayList<>();
        String sql = "SELECT ShipName, AvgRevenue, AvgCost, AvgProfit, ProfitMarginPct FROM vw_AvgProfitByShip ORDER BY ShipName";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                AvgProfitByShip aps = new AvgProfitByShip();
                aps.setShipName(rs.getString("ShipName"));
                aps.setAvgRevenue(rs.getDouble("AvgRevenue"));
                aps.setAvgCost(rs.getDouble("AvgCost"));
                aps.setAvgProfit(rs.getDouble("AvgProfit"));
                aps.setProfitMarginPct(rs.getDouble("ProfitMarginPct"));
                list.add(aps);
            }
        }
        return list;
    }

    public List<FuelEfficiencyImpact> getFuelEfficiencyImpact() throws SQLException {
        List<FuelEfficiencyImpact> list = new ArrayList<>();
        String sql = "SELECT VoyageID, ShipName, FuelEfficiency, Distance, EstimatedGallons, FuelPricePerGallon, EstimatedFuelCost, Profit, ProfitPerGallon FROM vw_FuelEfficiencyImpact ORDER BY VoyageID";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                FuelEfficiencyImpact fei = new FuelEfficiencyImpact();
                fei.setVoyageID(rs.getInt("VoyageID"));
                fei.setShipName(rs.getString("ShipName"));
                fei.setFuelEfficiency(rs.getDouble("FuelEfficiency"));
                fei.setDistance(rs.getDouble("Distance"));
                fei.setEstimatedGallons(rs.getDouble("EstimatedGallons"));
                fei.setFuelPricePerGallon(rs.getDouble("FuelPricePerGallon"));
                fei.setEstimatedFuelCost(rs.getDouble("EstimatedFuelCost"));
                fei.setProfit(rs.getDouble("Profit"));
                fei.setProfitPerGallon(rs.getDouble("ProfitPerGallon"));
                list.add(fei);
            }
        }
        return list;
    }
}
