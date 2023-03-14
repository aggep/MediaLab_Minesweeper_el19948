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
    /*basic game parameters
    initialize game with default values */
    public static int TIME = 120; //this will determine the time of the game
    public static int GRIDSIZE = 9;
    public static int MINECOUNT = 10;

    /* for the Rounds functionality */
    public static String[] WINNER = new String[6];
    public static int GAMES = 0;
    public static int[] TOTALGAMETIME = new int[6] ;
    public static int[] ATTEMPTS = new int[6] ; //θεωρώ ότι οι προσπάθειες είναι πόσα "πατήματα" (clicks) κάνει ο χρήστης σε κάθε παιχνίδι
    static Stage window;
    BorderPane layout;
    static Label infoLabel; //for the display of information s.a. Total Mines, Marked Mines

    private static String title;
    public static int MAX_TIME = TIME;// Maximum time in seconds
    public static int remainingTime = MAX_TIME; // Remaining time in seconds
    private Label timeLabel; // for the timer display

    public static boolean gameStarted = false; //this is for the start button, we set it to false first -> it is used for the condition

    GridPane gridPane;
    MenuBar menuBar = new MenuBar();
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
            try {
                resetGame();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            /*
            if(gameStarted) {
                // Stop the current game and start a new one
                try {
                    resetGame();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            } */
            GAMES++;
            gameCounter(GAMES); //everytime we start a game, we increment the GAMES variable
            System.out.println(GAMES);
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


        MenuItem ROUNDS = new MenuItem(("Rounds"));
        ROUNDS.setOnAction((e-> {
            RoundsPopup popup2 = new RoundsPopup();
            popup2.show();
        }));
        Menu2.getItems().add(ROUNDS);

        MenuItem SOLUTION = new MenuItem("Solution");
        SOLUTION.setOnAction( e->{
            timer.cancel();
            TOTALGAMETIME[GAMES] = MAX_TIME - remainingTime; //total time = max_time- remaining_time (this is considered an ended game)
            GUI.WINNER[GUI.GAMES] = "computer"; //the game is lost, thus the computer has one | 1: Player is the winner - 0: Computer is the winner
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
        menuBar.getMenus().addAll(Menu1, Menu2);

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
        infoLabel = new Label("   Total Mines: " + MINECOUNT + "   Marked Mines: 0");
    }
    public static void update(int flagged){
        if(infoLabel!=null) {
            infoLabel.setText("   Total Mines: " + MINECOUNT + "   Marked Mines: " + flagged);
        }
    }
    public void RanOutofTime(){ //the function is called when the timer runs out before the player finished the game
        TOTALGAMETIME[GAMES] = MAX_TIME;
        GUI.WINNER[GUI.GAMES] = "computer";
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
        timer.cancel();
        remainingTime = MAX_TIME;
        timeLabel.setText("  Time Remaining: " + remainingTime);
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                Platform.runLater(() -> {
                    remainingTime--;
                    if (remainingTime >= 0) {
                        timeLabel.setText("  Time Remaining: " + remainingTime);
                    } else {
                        // Game over, ran out of time
                        RanOutofTime();
                        timer.cancel();
                    }
                });
            }
        }, 0, 1000);
    }
    private void resetGame() throws IOException { //reset the game - new scene, new borderpane, new grid
        // Stop the timer
        timer.purge();
        remainingTime = MAX_TIME;
        timeLabel.setText("  Time Remaining: " + remainingTime);
        gameStarted = false;
        gridPane.getChildren().clear();

        createInfoLabel();

        HBox newBox = new HBox();
        newBox.getChildren().addAll(menuBar, infoLabel, timeLabel);

        BorderPane layout1 = new BorderPane();
        layout1.setTop(newBox);

        window.setTitle(GUI.title);

        Grid.cellGrid.clear();
        Grid.mines.clear();

        Handler handler = new Handler();
        gridPane = new Grid(handler);

        for (Node node : gridPane.getChildren()) {
            Button button = (Button) node;
            button.setPrefSize(60, 60);
        }

        layout1.setCenter(gridPane);

        Scene scene1 = new Scene(layout1);
        window.setScene(scene1);
        window.show();
    }

    public static void updateValues(int time, int gridSize, int mineCount) {
        TIME = time;
        GRIDSIZE = gridSize;
        MINECOUNT = mineCount;
        Grid.bound = GRIDSIZE *  GRIDSIZE;
        MAX_TIME = remainingTime = TIME;
    }

    public static void gameCounter(int games){
        if(games == 6){
            GAMES = 0;
        }
    }


}
