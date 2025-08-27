package com.shippingerp.beans;

import com.shippingerp.model.CostType;
import com.shippingerp.model.RevenueType;
import com.shippingerp.model.VoyageProfit;
import com.shippingerp.util.DBConnection;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.model.SelectItem;
import jakarta.inject.Named;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Named
@RequestScoped
public class CostRevenueBean {

    private List<SelectItem> costTypeItems;
    private List<SelectItem> revenueTypeItems;

    private int selectedVoyageID;  // the voyage to update cost/revenue for
    private int selectedCostTypeID;
    private BigDecimal costAmount;

    private int selectedRevenueTypeID;
    private BigDecimal revenueAmount;

    @PostConstruct
    public void init() {
        loadCostTypes();
        loadRevenueTypes();
    }

    public List<SelectItem> getCostTypeItems() { return costTypeItems; }
    public List<SelectItem> getRevenueTypeItems() { return revenueTypeItems; }

    public int getSelectedVoyageID() { return selectedVoyageID; }
    public void setSelectedVoyageID(int selectedVoyageID) { this.selectedVoyageID = selectedVoyageID; }

    public int getSelectedCostTypeID() { return selectedCostTypeID; }
    public void setSelectedCostTypeID(int selectedCostTypeID) { this.selectedCostTypeID = selectedCostTypeID; }

    public BigDecimal getCostAmount() { return costAmount; }
    public void setCostAmount(BigDecimal costAmount) { this.costAmount = costAmount; }

    public int getSelectedRevenueTypeID() { return selectedRevenueTypeID; }
    public void setSelectedRevenueTypeID(int selectedRevenueTypeID) { this.selectedRevenueTypeID = selectedRevenueTypeID; }

    public BigDecimal getRevenueAmount() { return revenueAmount; }
    public void setRevenueAmount(BigDecimal revenueAmount) { this.revenueAmount = revenueAmount; }

    private void loadCostTypes() {
        costTypeItems = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT CostTypeID, Name FROM CostTypes ORDER BY Name");
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                costTypeItems.add(new SelectItem(rs.getInt("CostTypeID"), rs.getString("Name")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadRevenueTypes() {
        revenueTypeItems = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT RevenueTypeID, Description FROM RevenueTypes ORDER BY Description");
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                revenueTypeItems.add(new SelectItem(rs.getInt("RevenueTypeID"), rs.getString("Description")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addOrUpdateCost() {
        FacesContext context = FacesContext.getCurrentInstance();

        if (selectedVoyageID == 0) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Please select a voyage."));
            return;
        }

        if (selectedCostTypeID == 0 || costAmount == null) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Please select cost type and amount."));
            return;
        }

        try (Connection conn = DBConnection.getConnection()) {
            // Check if a cost record exists for this voyage and cost type
            String checkSql = "SELECT CostID FROM Costs WHERE VoyageID = ? AND CostTypeID = ?";
            try (PreparedStatement psCheck = conn.prepareStatement(checkSql)) {
                psCheck.setInt(1, selectedVoyageID);
                psCheck.setInt(2, selectedCostTypeID);
                try (ResultSet rs = psCheck.executeQuery()) {
                    if (rs.next()) {
                        // Update existing
                        int costID = rs.getInt("CostID");
                        String updateSql = "UPDATE Costs SET Amount = ?, CostDate = ? WHERE CostID = ?";
                        try (PreparedStatement psUpdate = conn.prepareStatement(updateSql)) {
                            psUpdate.setBigDecimal(1, costAmount);
                            psUpdate.setDate(2, new java.sql.Date(System.currentTimeMillis()));
                            psUpdate.setInt(3, costID);
                            psUpdate.executeUpdate();
                        }
                    } else {
                        // Insert new
                        String insertSql = "INSERT INTO Costs (VoyageID, CostTypeID, Amount, CostDate) VALUES (?, ?, ?, ?)";
                        try (PreparedStatement psInsert = conn.prepareStatement(insertSql)) {
                            psInsert.setInt(1, selectedVoyageID);
                            psInsert.setInt(2, selectedCostTypeID);
                            psInsert.setBigDecimal(3, costAmount);
                            psInsert.setDate(4, new java.sql.Date(System.currentTimeMillis()));
                            psInsert.executeUpdate();
                        }
                    }
                }
            }
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Cost record saved."));
        } catch (SQLException e) {
            e.printStackTrace();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Database Error", "Failed to save cost record."));
        }
    }

    public void addOrUpdateRevenue() {
        FacesContext context = FacesContext.getCurrentInstance();

        if (selectedVoyageID == 0) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Please select a voyage."));
            return;
        }

        if (selectedRevenueTypeID == 0 || revenueAmount == null) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Please select revenue type and amount."));
            return;
        }

        try (Connection conn = DBConnection.getConnection()) {
            // Check if revenue record exists for this voyage and revenue type
            String checkSql = "SELECT RevenueID FROM Revenues WHERE VoyageID = ? AND RevenueTypeID = ?";
            try (PreparedStatement psCheck = conn.prepareStatement(checkSql)) {
                psCheck.setInt(1, selectedVoyageID);
                psCheck.setInt(2, selectedRevenueTypeID);
                try (ResultSet rs = psCheck.executeQuery()) {
                    if (rs.next()) {
                        // Update existing
                        int revenueID = rs.getInt("RevenueID");
                        String updateSql = "UPDATE Revenues SET Amount = ?, RevenueDate = ? WHERE RevenueID = ?";
                        try (PreparedStatement psUpdate = conn.prepareStatement(updateSql)) {
                            psUpdate.setBigDecimal(1, revenueAmount);
                            psUpdate.setDate(2, new java.sql.Date(System.currentTimeMillis()));
                            psUpdate.setInt(3, revenueID);
                            psUpdate.executeUpdate();
                        }
                    } else {
                        // Insert new
                        String insertSql = "INSERT INTO Revenues (VoyageID, RevenueTypeID, Amount, RevenueDate) VALUES (?, ?, ?, ?)";
                        try (PreparedStatement psInsert = conn.prepareStatement(insertSql)) {
                            psInsert.setInt(1, selectedVoyageID);
                            psInsert.setInt(2, selectedRevenueTypeID);
                            psInsert.setBigDecimal(3, revenueAmount);
                            psInsert.setDate(4, new java.sql.Date(System.currentTimeMillis()));
                            psInsert.executeUpdate();
                        }
                    }
                }
            }
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Revenue record saved."));
        } catch (SQLException e) {
            e.printStackTrace();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Database Error", "Failed to save revenue record."));
        }
    }

}
