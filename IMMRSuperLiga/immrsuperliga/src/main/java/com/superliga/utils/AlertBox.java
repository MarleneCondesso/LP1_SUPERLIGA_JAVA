/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.superliga.utils;

import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
/**
 *
 * @author 35191
 */
public class AlertBox {

    Alert alert;
    Optional<ButtonType> button;

    public AlertBox(Alert.AlertType type, String title, String message) {

        alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);

        button = alert.showAndWait();
    }

    public Alert getAlert() {
        return alert;
    }

    public Optional<ButtonType> getButton() {
        return button;
    }

}