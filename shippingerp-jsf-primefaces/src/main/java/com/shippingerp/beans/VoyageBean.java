package com.shippingerp.beans;

import com.shippingerp.model.AddVoyages;
import com.shippingerp.model.VoyageProfit;
import com.shippingerp.util.DBConnection;

import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.model.SelectItem;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;

import java.io.Serializable;
import java.sql.*;
import java.util.*;

@Named
@ViewScoped
public class VoyageBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private AddVoyages newVoyage = new AddVoyages();
    private List<SelectItem> shipItems;

    public AddVoyages getNewVoyage() {
        return newVoyage;
    }

    public List<SelectItem> getShipItems() {
        return shipItems;
    }

    @PostConstruct
    public void init() {
        loadShips();
    }

    private void loadShips() {
        shipItems = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT ShipID, Name FROM Ships ORDER BY Name");
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("ShipID");
                String name = rs.getString("Name");
                shipItems.add(new SelectItem(id, name));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Validation: existing ship or complete new ship info
    public boolean validateShipSelection() {
        boolean hasExistingShip = newVoyage.getShipID() != 0;
        boolean hasNewShip = newVoyage.getNewShipName() != null && !newVoyage.getNewShipName().trim().isEmpty()
                && newVoyage.getNewShipCapacity() != null
                && newVoyage.getNewShipFuelEfficiency() != null;

        if (!hasExistingShip && !hasNewShip) {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "Validation Error",
                        "Please select an existing ship or enter all new ship details."));
            return false;
        }
        return true;
    }

    private int getCostTypeID(Connection conn, String costTypeName) throws SQLException {
        String sql = "SELECT CostTypeID FROM CostTypes WHERE Name = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, costTypeName);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("CostTypeID");
                } else {
                    throw new SQLException("CostType not found: " + costTypeName);
                }
            }
        }
    }

    private int getRevenueTypeID(Connection conn, String revenueDesc) throws SQLException {
        String sql = "SELECT RevenueTypeID FROM RevenueTypes WHERE Description = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, revenueDesc);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("RevenueTypeID");
                } else {
                    throw new SQLException("RevenueType not found: " + revenueDesc);
                }
            }
        }
    }

    public void saveVoyage() {
        FacesContext context = FacesContext.getCurrentInstance();

        if (!validateShipSelection()) {
            return; // stop if validation fails
        }

        if (newVoyage.getStartDate() == null || newVoyage.getEndDate() == null) {
            context.addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Validation Error", "Start Date and End Date are required"));
            return;
        }

        if (newVoyage.getEndDate().isBefore(newVoyage.getStartDate())) {
            context.addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Validation Error", "End Date cannot be before Start Date"));
            return;
        }

        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false);

            // Insert new ship if needed
            if (newVoyage.getNewShipName() != null && !newVoyage.getNewShipName().trim().isEmpty()) {
                String insertShipSQL = "INSERT INTO Ships (Name, Capacity, FuelEfficiency) VALUES (?, ?, ?)";
                try (PreparedStatement psShip = conn.prepareStatement(insertShipSQL, Statement.RETURN_GENERATED_KEYS)) {
                    psShip.setString(1, newVoyage.getNewShipName());
                    psShip.setInt(2, newVoyage.getNewShipCapacity());
                    psShip.setDouble(3, newVoyage.getNewShipFuelEfficiency());
                    psShip.executeUpdate();

                    try (ResultSet keys = psShip.getGeneratedKeys()) {
                        if (keys.next()) {
                            int newShipId = keys.getInt(1);
                            newVoyage.setShipID(newShipId);
                        } else {
                            throw new SQLException("Failed to get new ShipID");
                        }
                    }
                }
            }

            // Insert voyage
            String insertVoyageSQL = "INSERT INTO Voyages (ShipID, StartDate, EndDate, Route, Distance) VALUES (?, ?, ?, ?, ?)";
            int newVoyageId;
            try (PreparedStatement psVoyage = conn.prepareStatement(insertVoyageSQL, Statement.RETURN_GENERATED_KEYS)) {
                psVoyage.setInt(1, newVoyage.getShipID());
                psVoyage.setDate(2, java.sql.Date.valueOf(newVoyage.getStartDate()));
                psVoyage.setDate(3, java.sql.Date.valueOf(newVoyage.getEndDate()));
                psVoyage.setString(4, newVoyage.getRoute());
                psVoyage.setInt(5, newVoyage.getDistance());
                psVoyage.executeUpdate();

                try (ResultSet keys = psVoyage.getGeneratedKeys()) {
                    if (keys.next()) {
                        newVoyageId = keys.getInt(1);
                    } else {
                        throw new SQLException("Failed to get new VoyageID");
                    }
                }
            }

            // Insert initial zero costs
            String[] costTypes = {"Fuel", "PortFee", "CrewWages", "Maintenance"};
            String insertCostSQL = "INSERT INTO Costs (VoyageID, CostTypeID, Amount, CostDate) VALUES (?, ?, ?, ?)";
            try (PreparedStatement psCost = conn.prepareStatement(insertCostSQL)) {
                for (String costType : costTypes) {
                    int costTypeID = getCostTypeID(conn, costType);
                    psCost.setInt(1, newVoyageId);
                    psCost.setInt(2, costTypeID);
                    psCost.setBigDecimal(3, new java.math.BigDecimal("0.00"));
                    psCost.setDate(4, new java.sql.Date(System.currentTimeMillis()));
                    psCost.addBatch();
                }
                psCost.executeBatch();
            }

            // Insert initial zero revenue
            int revenueTypeID = getRevenueTypeID(conn, "Initial Revenue");
            String insertRevenueSQL = "INSERT INTO Revenues (VoyageID, RevenueTypeID, Amount, RevenueDate) VALUES (?, ?, ?, ?)";
            try (PreparedStatement psRevenue = conn.prepareStatement(insertRevenueSQL)) {
                psRevenue.setInt(1, newVoyageId);
                psRevenue.setInt(2, revenueTypeID);
                psRevenue.setBigDecimal(3, new java.math.BigDecimal("0.00"));
                psRevenue.setDate(4, new java.sql.Date(System.currentTimeMillis()));
                psRevenue.executeUpdate();
            }

            conn.commit();

            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Voyage added successfully"));

            // Reset form and reload ships dropdown
            newVoyage = new AddVoyages();
            loadShips();

        } catch (SQLException e) {
            e.printStackTrace();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Database Error", "Failed to add voyage"));
        }
    }

    public List<VoyageProfit> getVoyages() {
        List<VoyageProfit> result = new ArrayList<>();
        String sql = "SELECT VoyageID, ShipName, Route, StartDate, EndDate, Distance, TotalRevenue, TotalCost, Profit FROM vw_VoyageProfitability ORDER BY VoyageID";

        try (Connection conn = DBConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                result.add(new VoyageProfit(
                        rs.getInt("VoyageID"),
                        rs.getString("ShipName"),
                        rs.getString("Route"),
                        rs.getDate("StartDate").toLocalDate(),
                        rs.getDate("EndDate").toLocalDate(),
                        rs.getInt("Distance"),
                        rs.getBigDecimal("TotalRevenue"),
                        rs.getBigDecimal("TotalCost"),
                        rs.getBigDecimal("Profit")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }
}
