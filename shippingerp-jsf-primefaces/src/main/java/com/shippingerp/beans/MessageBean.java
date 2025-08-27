package com.shippingerp.beans;



import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;

@Named("messageBean")
@RequestScoped
public class MessageBean {

    // Show a welcome info message in growl
    public void showWelcomeMessage() {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
            "Welcome",
            "Enjoy using Shipping ERP!");

        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    // Show an error message example (optional)
    public void showErrorMessage() {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
            "Error",
            "Something went wrong!");

        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
}
