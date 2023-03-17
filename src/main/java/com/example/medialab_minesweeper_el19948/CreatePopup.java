package com.example.medialab_minesweeper_el19948;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class CreatePopup extends Stage {
    public static int SuperMine;
    public CreatePopup() {

        VBox root = new VBox();
        root.setPadding(new Insets(12));

        GridPane gridPane = new GridPane();
        gridPane.setHgap(12);
        gridPane.setVgap(12);

        TextField scenarioIdField = new TextField();
        TextField difficultyField = new TextField();
        TextField minesField = new TextField();
        CheckBox SuperMineBox = new CheckBox("Super-mine");
        TextField timeField = new TextField();

        gridPane.add(new Label("Scenario ID:"), 0, 0);
        gridPane.add(scenarioIdField, 1, 0);
        gridPane.add(new Label("Difficulty:"), 0, 1);
        gridPane.add(difficultyField, 1, 1);
        gridPane.add(new Label("Mines:"), 0, 2);
        gridPane.add(minesField, 1, 2);
        gridPane.add(new Label("Super-mine:"), 0, 3);
        gridPane.add(SuperMineBox, 1, 3);
        gridPane.add(new Label("Max Time (sec):"), 0, 4);
        gridPane.add(timeField, 1, 4);

        Button createButton = new Button("Create");
        createButton.setOnAction(e -> {
            String scenarioId = scenarioIdField.getText();
            String difficulty = difficultyField.getText();
            String mines = minesField.getText();
            if(SuperMineBox.isSelected())
                SuperMine = 1;
            else SuperMine = 0;
            String time = timeField.getText();


            // Create the file chooser to select the directory
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save description file");

            String folderPath = System.getProperty("user.home") + "/medialab";
            File folder = new File(folderPath);
            if (!folder.exists()) {
                folder.mkdir();
            }


            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text files", "*.txt"));

            // Show the save dialog

            File file = new File(folder, scenarioId + ".txt");

            // Write the data to a text file
                try {
                    FileWriter fileWriter = new FileWriter(file);
                    BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                    bufferedWriter.write(difficulty + "\n");
                    bufferedWriter.write(mines + "\n");
                    bufferedWriter.write(time + "\n");
                    bufferedWriter.write(SuperMine + "\n");
                    bufferedWriter.close();
                } catch (IOException ex) {
                    System.out.println("Error writing to file.");
                }


            this.close();
            // Show a success message in a new popup window
            Alert successAlert = new Alert(Alert.AlertType.INFORMATION, "Description file saved successfully.");
            successAlert.showAndWait();
        });


        root.getChildren().addAll(gridPane, createButton);

        Scene scene = new Scene(root);
        this.setScene(scene);
        this.setTitle("Create new description");
    }
}

