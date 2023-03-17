package com.example.medialab_minesweeper_el19948;

import javafx.scene.control.Alert;

public class InvalidDescriptionException extends RuntimeException {
    public InvalidDescriptionException(String errorMsg){
        super(errorMsg);
        Alert errorAlert = new Alert(Alert.AlertType.ERROR, "Invalid game description.");
        errorAlert.show();
    }
}