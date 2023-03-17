package com.example.medialab_minesweeper_el19948;

import javafx.scene.control.Alert;

public class InvalidValueException extends RuntimeException {
    public InvalidValueException(String errorMessage) {
        super(errorMessage);
        Alert errorAlert = new Alert(Alert.AlertType.ERROR, "Invalid value.");
        errorAlert.show();
    }
}