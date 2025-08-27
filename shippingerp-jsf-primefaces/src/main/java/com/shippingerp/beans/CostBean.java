package com.shippingerp.beans;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.shippingerp.util.DBConnection;
import com.shippingerp.model.CostBreakdownItem;
import com.shippingerp.model.RevenueBreakdownItem; // NEW

@Named
@SessionScoped
public class CostBean implements Serializable {

    private Integer selectedVoyageId;
    private List<CostBreakdownItem> breakdown = new ArrayList<>();
    private List<RevenueBreakdownItem> revenueBreakdown = new ArrayList<>(); // NEW

    public Integer getSelectedVoyageId() { return selectedVoyageId; }
    public void setSelectedVoyageId(Integer id) { this.selectedVoyageId = id; }

    public List<CostBreakdownItem> getBreakdown() { return breakdown; }
    public List<RevenueBreakdownItem> getRevenueBreakdown() { return revenueBreakdown; } // NEW

    public List<Integer> getAllVoyageIds() {
        List<Integer> ids = new ArrayList<>();
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement("SELECT VoyageID FROM Voyages ORDER BY VoyageID");
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) ids.add(rs.getInt(1));
        } catch (SQLException e) { e.printStackTrace(); }
        return ids;
    }

    public void loadBreakdown() {
        breakdown.clear();
        revenueBreakdown.clear(); // NEW
        if (selectedVoyageId == null) return;

        // Load cost breakdown
        String costSql = """
            SELECT ct.Name AS CostType, SUM(c.Amount) AS TotalAmount
            FROM Costs c
            JOIN CostTypes ct ON c.CostTypeID = ct.CostTypeID
            WHERE c.VoyageID = ?
            GROUP BY ct.Name
            ORDER BY ct.Name
        """;
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(costSql)) {
            ps.setInt(1, selectedVoyageId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    breakdown.add(new CostBreakdownItem(
                        rs.getString("CostType"),
                        rs.getBigDecimal("TotalAmount")
                    ));
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }

        // Load revenue breakdown — NEW
       String revenueSql = """
    SELECT rt.Description AS RevenueType, SUM(r.Amount) AS TotalAmount
    FROM Revenues r
    JOIN RevenueTypes rt ON r.RevenueTypeID = rt.RevenueTypeID
    WHERE r.VoyageID = ?
    GROUP BY rt.Description
    ORDER BY rt.Description
""";

        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(revenueSql)) {
            ps.setInt(1, selectedVoyageId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    revenueBreakdown.add(new RevenueBreakdownItem(
                        rs.getString("RevenueType"),
                        rs.getBigDecimal("TotalAmount")
                    ));
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
    }
}
