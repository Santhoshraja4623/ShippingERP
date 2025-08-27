package com.shippingerp.beans;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.shippingerp.util.DBConnection;
import com.shippingerp.model.Ship;
import com.shippingerp.model.ShipPerformanceSummary;

@Named
@SessionScoped
public class ShipBean implements Serializable {
    private Integer selectedShipId;
    private ShipPerformanceSummary summary;

    public Integer getSelectedShipId() { return selectedShipId; }
    public void setSelectedShipId(Integer id) { this.selectedShipId = id; }
    public ShipPerformanceSummary getSummary() { return summary; }

    public List<Ship> getAllShips() {
        List<Ship> list = new ArrayList<>();
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement("SELECT ShipID, Name FROM Ships ORDER BY Name");
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(new Ship(rs.getInt(1), rs.getString(2)));
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    public void loadSummary() {
        if (selectedShipId == null) return;
        String sql = """
            SELECT 
              s.Name AS ShipName,
              COUNT(DISTINCT v.VoyageID) AS VoyageCount,
              ISNULL(SUM(r.Amount), 0) AS TotalRevenue,
              ISNULL(SUM(c.Amount), 0) AS TotalCost,
              ISNULL(SUM(r.Amount), 0) - ISNULL(SUM(c.Amount), 0) AS TotalProfit
            FROM Ships s
            LEFT JOIN Voyages v ON s.ShipID = v.ShipID
            LEFT JOIN Revenues r ON v.VoyageID = r.VoyageID
            LEFT JOIN Costs c ON v.VoyageID = c.VoyageID
            WHERE s.ShipID = ?
            GROUP BY s.Name
        """;
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, selectedShipId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    summary = new ShipPerformanceSummary(
                        rs.getString("ShipName"),
                        rs.getInt("VoyageCount"),
                        rs.getBigDecimal("TotalRevenue"),
                        rs.getBigDecimal("TotalCost"),
                        rs.getBigDecimal("TotalProfit")
                    );
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
    }
}
