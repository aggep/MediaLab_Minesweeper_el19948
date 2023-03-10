package com.example.medialab_minesweeper_el19948;

import javafx.geometry.Insets;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Optional;
import java.util.Scanner;

public class LoadPopup extends Stage {

    public int Diff;
    public int NumberOfMines;
    public int Time;
    public int SuperMine;
    public int[] array;

    public LoadPopup() {
        VBox root = new VBox();
        root.setPadding(new Insets(12));

        GridPane gridPane = new GridPane();
        gridPane.setHgap(12);
        gridPane.setVgap(12);

        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Load Scenario");
        dialog.setHeaderText(null);
        dialog.setContentText("Please enter scenario ID:");
        Optional<String> result = dialog.showAndWait();


        result.ifPresent(scenarioId -> {
            String filePath = System.getProperty("user.home") + "/medialab/" + scenarioId + ".txt";
            String fileContent = "";

            File file = new File(filePath);
            Scanner scan ;
            try {
                scan = new Scanner(file);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            array = new int[4]; // initialize the array
            while (scan.hasNextLine()) {
                fileContent = fileContent.concat(scan.nextLine() + "\n");
            }
            scan.close();

            String[] values = fileContent.trim().split("\\s+");
                try {
                    if (values.length == 4) {
                        int[] array = new int[4];
                        for (int i = 0; i < values.length; i++) {
                            array[i] = Integer.parseInt(values[i]);
                        }
                        Diff = array[0]; //level of difficulty
                        NumberOfMines = array[1];
                        Time = array[2];
                        SuperMine = array[3];

                        if (Diff != 1 && Diff != 2) { //εξασφαλίζονται οι περιπτώσεις για τις οποίες η δυσκολία δεν είναι ούτε 1 ούτε 2.
                            throw new InvalidValueException("wrong input values!");
                        } else {
                            if (Diff == 1) {
                                if ((NumberOfMines < 9 || NumberOfMines > 11) ||
                                        (Time < 120 || Time > 180) ||
                                        (SuperMine != 0 && SuperMine != 1))
                                    throw new InvalidValueException("wrong description!");
                                else{
                                    GUI.updateValues(Time,9, NumberOfMines);
                                   /* GUI.GRIDSIZE = 9;
                                    GUI.MINECOUNT = NumberOfMines;
                                    GUI.TIME = Time; */
                                }


                            } else { //if difficulty = 2 -- είναι πάντα αληθής η συνθήκη αυτή αν η δυσκολία δεν είναι ίση με 1
                                if ((NumberOfMines < 35 || NumberOfMines > 45) ||
                                        (Time < 240 || Time > 360) || (SuperMine != 0 && SuperMine != 1))
                                    throw new InvalidValueException("wrong description!");
                               else {
                                    GUI.updateValues(Time,16, NumberOfMines);
                                   /* GUI.GRIDSIZE = 16;
                                    GUI.MINECOUNT = NumberOfMines;
                                    GUI.TIME = Time; */
                                }
                            }
                        }

                    }
                } catch (InvalidDescriptionException e) {
                    throw new InvalidDescriptionException("incorrect number of lines: ");
                }

        });
    }
}
