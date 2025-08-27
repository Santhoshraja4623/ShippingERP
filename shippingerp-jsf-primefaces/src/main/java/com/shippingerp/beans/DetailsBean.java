package com.shippingerp.beans;

import com.shippingerp.dao.DetailsDAO;
import com.shippingerp.model.*;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Named("detailsBean")
@SessionScoped
public class DetailsBean implements Serializable {

    private static final Logger LOGGER = Logger.getLogger(DetailsBean.class.getName());

    @Inject
    private DetailsDAO detailsDAO;

    private List<Ships> ships = new ArrayList<>();
    private List<VoyageDetail> voyages = new ArrayList<>();
    private List<CostTypes> costTypes = new ArrayList<>();
    private List<RevenueTypes> revenueTypes = new ArrayList<>();
    private List<FactorName> factorNames = new ArrayList<>();

    private Ships newShip = new Ships();
    private Ships selectedShip;

    @PostConstruct
    public void init() {
        loadAllData();
    }

    public void loadAllData() {
        try {
            ships = detailsDAO.getAllShips();
            voyages = detailsDAO.getAllVoyages();
            costTypes = detailsDAO.getAllCostTypes();
            revenueTypes = detailsDAO.getAllRevenueTypes();
            factorNames = detailsDAO.getAllFactorNames();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Failed to load data", e);
            FacesContext.getCurrentInstance().addMessage("msgs",
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Failed to load data."));
        }
    }

    // Public method for manual data refresh
    public void refreshData() {
        loadAllData();
        FacesContext.getCurrentInstance().addMessage("msgs",
            new FacesMessage(FacesMessage.SEVERITY_INFO, "Data Refreshed", "All data reloaded successfully."));
    }

    // ========== CRUD Operations for Ships ==========

    public void addShip() {
        try {
            detailsDAO.addShip(newShip);
            // Reload ships to get updated list with DB-generated IDs
            ships = detailsDAO.getAllShips();

            FacesContext.getCurrentInstance().addMessage("msgs",
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Ship Added", "New ship added successfully."));
            newShip = new Ships();  // Clear form
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Failed to add ship", e);
            FacesContext.getCurrentInstance().addMessage("msgs",
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Failed to add ship."));
        }
    }

    public void updateShip() {
        if (selectedShip != null) {
            try {
                detailsDAO.updateShip(selectedShip);
                ships = detailsDAO.getAllShips();  // Refresh list after update

                FacesContext.getCurrentInstance().addMessage("msgs",
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Updated", "Ship updated successfully."));
            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, "Failed to update ship", e);
                FacesContext.getCurrentInstance().addMessage("msgs",
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Failed to update ship."));
            }
        }
    }

    public void deleteShip() {
        if (selectedShip != null) {
            try {
                detailsDAO.deleteShip(selectedShip.getShipId());
                ships = detailsDAO.getAllShips();  // Refresh after deletion
                selectedShip = null;

                FacesContext.getCurrentInstance().addMessage("msgs",
                    new FacesMessage(FacesMessage.SEVERITY_WARN, "Deleted", "Ship deleted successfully."));
            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, "Failed to delete ship", e);
                FacesContext.getCurrentInstance().addMessage("msgs",
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Failed to delete ship."));
            }
        }
    }

    // ========== Getters & Setters ==========

    public List<Ships> getShips() {
        return ships;
    }

    public List<VoyageDetail> getVoyages() {
        return voyages;
    }

    public List<CostTypes> getCostTypes() {
        return costTypes;
    }

    public List<RevenueTypes> getRevenueTypes() {
        return revenueTypes;
    }

    public List<FactorName> getFactorNames() {
        return factorNames;
    }

    public Ships getNewShip() {
        return newShip;
    }

    public void setNewShip(Ships newShip) {
        this.newShip = newShip;
    }

    public Ships getSelectedShip() {
        return selectedShip;
    }

    public void setSelectedShip(Ships selectedShip) {
        this.selectedShip = selectedShip;
    }
}
