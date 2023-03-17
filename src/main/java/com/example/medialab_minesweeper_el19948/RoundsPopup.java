package com.example.medialab_minesweeper_el19948;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class RoundsPopup extends Stage {

    private final ObservableList<Results> data =
            FXCollections.observableArrayList(new Results(1, GUI.ATTEMPTS[0], GUI.TOTALGAMETIME[0], GUI.WINNER[0]),
                    new Results(2, GUI.ATTEMPTS[1], GUI.TOTALGAMETIME[1], GUI.WINNER[1]),
                    new Results(3, GUI.ATTEMPTS[2], GUI.TOTALGAMETIME[2], GUI.WINNER[2]),
                    new Results(4, GUI.ATTEMPTS[3], GUI.TOTALGAMETIME[3], GUI.WINNER[3]),
                    new Results(5, GUI.ATTEMPTS[4], GUI.TOTALGAMETIME[4], GUI.WINNER[4]));
    public RoundsPopup() {
        this.initModality(Modality.APPLICATION_MODAL);
        this.setTitle("Games Results");

        TableView tableView = new TableView();

        TableColumn<Results, Integer> firstColumn = new TableColumn<>("Game");
        firstColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().firstColumn).asObject());

        TableColumn<Results, Integer> secondColumn = new TableColumn<>("Attempts");
        secondColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().secondColumn).asObject());

        TableColumn<Results, Integer> thirdColumn = new TableColumn<>("Total Time");
        thirdColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().thirdColumn).asObject());

        TableColumn<Results, String> fourthColumn = new TableColumn<>("Winner");
        fourthColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().fourthColumn));


        tableView.getColumns().addAll(firstColumn, secondColumn, thirdColumn, fourthColumn);
        tableView.setItems(data);

        VBox layout = new VBox(10);
        layout.getChildren().addAll(tableView);

        Scene scene = new Scene(layout, 400, 350);
        this.setScene(scene);
        this.show();
    }
    public static class Results {
        private final int firstColumn;
        private final int secondColumn;
        private final int thirdColumn;
        private final String fourthColumn;


            public Results(int firstColumn, int secondColumn, int thirdColumn, String fourthColumn) {
            this.firstColumn = firstColumn;
            this.secondColumn = secondColumn;
            this.thirdColumn = thirdColumn;
            this.fourthColumn = fourthColumn;
        }
        public int getFirstColumn() {
            return firstColumn;
        }

        public int getSecondColumn() {
            return secondColumn;
        }

        public int getThirdColumn() {
            return thirdColumn;
        }

        public String getFourthColumn() {
            return fourthColumn;
        }

    }

}


