package com.shippingerp.dao;

import com.shippingerp.model.*;
import com.shippingerp.util.DBConnection;

import jakarta.enterprise.context.ApplicationScoped;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class DetailsDAO {

    // READ - List all ships
    public List<Ships> getAllShips() throws SQLException {
        List<Ships> ships = new ArrayList<>();
        String sql = "SELECT ShipID, Name, Capacity, FuelEfficiency FROM Ships ORDER BY Name";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Ships ship = new Ships();
                ship.setShipId(rs.getInt("ShipID"));
                ship.setName(rs.getString("Name"));
                ship.setCapacity(rs.getInt("Capacity"));
                ship.setFuelEfficiency(rs.getDouble("FuelEfficiency"));
                ships.add(ship);
            }
        }
        return ships;
    }

    // CREATE - Add new ship
    public void addShip(Ships ship) throws SQLException {
        String sql = "INSERT INTO Ships (Name, Capacity, FuelEfficiency) VALUES (?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, ship.getName());
            ps.setInt(2, ship.getCapacity());
            ps.setDouble(3, ship.getFuelEfficiency());
            ps.executeUpdate();
        }
    }

    // UPDATE - Modify existing ship
    public void updateShip(Ships ship) throws SQLException {
        String sql = "UPDATE Ships SET Name = ?, Capacity = ?, FuelEfficiency = ? WHERE ShipID = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, ship.getName());
            ps.setInt(2, ship.getCapacity());
            ps.setDouble(3, ship.getFuelEfficiency());
            ps.setInt(4, ship.getShipId());
            ps.executeUpdate();
        }
    }

    // DELETE - Remove ship
    public void deleteShip(int shipId) throws SQLException {
        String sql = "DELETE FROM Ships WHERE ShipID = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, shipId);
            ps.executeUpdate();
        }
    }

    // ----- Existing methods (unchanged) -----

    public List<VoyageDetail> getAllVoyages() throws SQLException {
        List<VoyageDetail> voyages = new ArrayList<>();
        String sql = "SELECT v.VoyageID, v.ShipID, s.Name AS ShipName, v.Route, v.StartDate, v.EndDate, v.Distance " +
                     "FROM Voyages v JOIN Ships s ON v.ShipID = s.ShipID ORDER BY v.VoyageID";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                VoyageDetail voyage = new VoyageDetail();
                voyage.setVoyageId(rs.getInt("VoyageID"));
                voyage.setShipId(rs.getInt("ShipID"));
                voyage.setShipName(rs.getString("ShipName"));
                voyage.setRoute(rs.getString("Route"));
                voyage.setStartDate(rs.getDate("StartDate"));
                voyage.setEndDate(rs.getDate("EndDate"));
                voyage.setDistance(rs.getInt("Distance"));
                voyages.add(voyage);
            }
        }
        return voyages;
    }

    public List<CostTypes> getAllCostTypes() throws SQLException {
        List<CostTypes> costTypes = new ArrayList<>();
        String sql = "SELECT CostTypeID, Name FROM CostTypes ORDER BY Name";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                CostTypes ct = new CostTypes();
                ct.setCostTypeId(rs.getInt("CostTypeID"));
                ct.setName(rs.getString("Name"));
                costTypes.add(ct);
            }
        }
        return costTypes;
    }

    public List<RevenueTypes> getAllRevenueTypes() throws SQLException {
        List<RevenueTypes> revenueTypes = new ArrayList<>();
        String sql = "SELECT RevenueTypeID, Description FROM RevenueTypes ORDER BY Description";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                RevenueTypes rt = new RevenueTypes();
                rt.setRevenueTypeId(rs.getInt("RevenueTypeID"));
                rt.setDescription(rs.getString("Description"));
                revenueTypes.add(rt);
            }
        }
        return revenueTypes;
    }

    public List<FactorName> getAllFactorNames() throws SQLException {
        List<FactorName> factorNames = new ArrayList<>();
        String sql = "SELECT FactorNameID, Name FROM FactorNames ORDER BY Name";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                FactorName fn = new FactorName();
                fn.setFactorNameId(rs.getInt("FactorNameID"));
                fn.setName(rs.getString("Name"));
                factorNames.add(fn);
            }
        }
        return factorNames;
    }
}
