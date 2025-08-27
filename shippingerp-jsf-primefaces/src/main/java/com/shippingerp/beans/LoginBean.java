package com.shippingerp.beans;

import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.context.Flash;
import jakarta.inject.Named;
import java.io.Serializable;

@Named
@SessionScoped
public class LoginBean implements Serializable {

    private String username;
    private String password;
    private boolean loggedIn = false;

    // Getters and setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    // Login method
    public String login() {
        if ("santhosh".equals(username) && "1234".equals(password)) {
            loggedIn = true;

            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Login Successful", "Welcome " + username + "!"));

            // Keep messages after redirect
            Flash flash = context.getExternalContext().getFlash();
            flash.setKeepMessages(true);

            return "home.xhtml?faces-redirect=true"; // Redirect to home page on success
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR,
                "Login Failed", "Invalid username or password"));
            return null; // Stay on login page if failed
        }
    }

    // Logout method
    public String logout() {
        loggedIn = false;
        username = null;
        password = null;
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "index.xhtml?faces-redirect=true"; // Redirect to login page
    }
}
