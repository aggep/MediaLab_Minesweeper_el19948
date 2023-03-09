package com.example.medialab_minesweeper_el19948;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;


public class GUI extends Application {

    static Stage window;
    BorderPane layout;
    static Label infoLabel; //for the display of information s.a. Total Mines, Marked Mines

    private static String title;
    private static final int MAX_TIME = Game.TIME; // Maximum time in seconds
    private int remainingTime = MAX_TIME; // Remaining time in seconds
    private Label timeLabel; // for the timer display

    // private final int gridSize = Game.GRIDSIZE; //this should be determined after reading the input file

    public static boolean gameStarted = false; //this is for the start button, we set it to false first -> it is used for the condition

    GridPane gridPane;
    static Timer timer = new Timer();
    public static void main(String[] args) {
        launch(args);

    }
    @Override

    public void start(Stage primaryStage) throws IOException {

        GUI.title = "MediaLab Minesweeper";
        window = primaryStage;


        /* create the menu buttons */
        Menu Menu1 = new Menu("Application");
        MenuItem createItem = new MenuItem("Create");
        createItem.setOnAction(e -> {
            CreatePopup popup = new CreatePopup();
            popup.show();
        });
        Menu1.getItems().add(createItem);

        MenuItem loadItem = new MenuItem("Load");
        loadItem.setOnAction(e -> {
            LoadPopup popup1 = new LoadPopup();
            popup1.show();
        });
        Menu1.getItems().add(loadItem);

        MenuItem START = new MenuItem("Start");
        START.setOnAction(e ->{
            if(gameStarted) {
                // Stop the current game and start a new one
                try {
                    resetGame();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
            gameStarted = true;
            startGame();
        });
        Menu1.getItems().add(START);

        MenuItem EXIT = new MenuItem("Exit");
        EXIT.setOnAction(e ->{
                window.close(); //the application closes completely
                System.exit(0);
        });
        Menu1.getItems().add(EXIT);


        Menu Menu2 = new Menu("Details");

        Menu2.getItems().add(new MenuItem("Rounds"));

        MenuItem SOLUTION = new MenuItem("Solution");
        SOLUTION.setOnAction( e->{
            timer.cancel();
            for (int x = 0; x < Grid.cellGrid.size(); x++) {
                if (Grid.cellGrid.get(x).getType() == 1) {
                    Grid.cellGrid.get(x).setDisable(true); //disable the cell
                    Grid.cellGrid.get(x).setText("X"); // where ever there are mines, the symbol "X" will be revealed
                }else {
                    Grid.cellGrid.get(x).setDisable(true); //disable the cell
                    Grid.cellGrid.get(x).setText("?"); //if the cell is not a mine, the symbol "?" will appear
                }
            }
            show("You can now see the solution");
        });
        Menu2.getItems().add(SOLUTION);

        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(Menu1, Menu2);

        layout = new BorderPane();
        layout.setTop(menuBar);

        // create the label in a separate method
        createInfoLabel();

        /* timer implementation */
        timeLabel = new Label("  Time Remaining: " + remainingTime);


        HBox topBox = new HBox();
        topBox.getChildren().addAll(menuBar, infoLabel, timeLabel);

        layout = new BorderPane();
        layout.setTop(topBox);


        window.setTitle(GUI.title);

        /* create the grids - Game board */

        //create handler for mouse events
        Handler handler = new Handler();
        gridPane = new Grid(handler);


        for (Node node : gridPane.getChildren()) {
            Button button = (Button) node;
            button.setPrefSize(60, 60);
        }


        layout.setCenter(gridPane);


        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.show();

    }
    private void createInfoLabel() {

        // initialize the label with default values
        infoLabel = new Label("   Total Mines: " + Game.MINECOUNT + "   Marked Mines: 0");
    }
    public static void update(int flagged){
        if(infoLabel!=null) {
            infoLabel.setText("   Total Mines: " + Game.MINECOUNT + "   Marked Mines: " + flagged);
        }
    }
    public void RanOutofTime(){ //the function is called when the timer runs out before the player finished the game
        for (int x = 0; x < Grid.cellGrid.size(); x++) {
            if (Grid.cellGrid.get(x).getType() == 1) {
                Grid.cellGrid.get(x).setDisable(true); //disable the cell
                Grid.cellGrid.get(x).setText("X"); // where ever there are mines, the symbol "X" will be revealed
            }else {
                Grid.cellGrid.get(x).setDisable(true); //disable the cell
                Grid.cellGrid.get(x).setText("?"); //if the cell is not a mine, the symbol "?" will appear
            }
        }
        show("Ran Out of Time");
    }
    public static void show(String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Game Over");
        alert.setHeaderText(null);
        alert.setContentText(message);

        alert.showAndWait();
    }

    private void startGame(){
        // Start the timer
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                remainingTime--;
                Platform.runLater(() -> {
                    timeLabel.setText("  Time Remaining: " + remainingTime);
                    if (remainingTime == 0) {
                        timer.cancel();
                        timer.purge();
                        RanOutofTime();
                    }
                });
            }
        };
        timer.scheduleAtFixedRate(task, 1000, 1000);
    }
    private void resetGame() throws IOException {
        // Stop the timer
        timer.purge();
        remainingTime = MAX_TIME;
        timeLabel.setText("  Time Remaining: " + remainingTime);
        gameStarted = false;

        Handler.reset();
    }

}
