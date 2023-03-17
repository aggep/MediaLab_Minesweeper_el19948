package com.example.medialab_minesweeper_el19948;

import javafx.scene.layout.Background;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.util.ArrayList;

public class Handler {
    private static final ArrayList<Cell> current = new ArrayList<>(); //create two arrays that will handle the cells differently, depending on their "states"
    private static final ArrayList<Cell> queue = new ArrayList<>();
    private static int flaggedCells = 0; //keeps track of the flagged cells

    public Handler() throws IOException {
    }

    /**
     * Implements the click function
     *
     * @param cell: the cell the user wants to click on
     */
    public static void click(Cell cell) {
        GUI.ATTEMPTS[GUI.GAMES]++; //everytime we click, the attempts of a game are being incremented by one
        int discoveredCells = 0; //how many cells are discovered during the game
        if (!cell.isFlagged()) { //we do this because we should not be able to click on cells that are flagged
            cell.setDisable(true);
            cell.setDiscovered();

            int position = cell.getPosition();
            if (cell.getType() == 0) { //if cell to determine the type of cell (e.g. type 0 : empty, type 1: mine, type 2: numbered)
                if (position < GUI.GRIDSIZE) { //if the position of the cell is between 0 and 9, in other words in the 1st row
                    if (position % GUI.GRIDSIZE == 0) { //topmost left-hand corner
                    /*we take all the adjacent cells of the cell we have clicked on
                    ,and we add them to our queue, which we are going to iterate through*/
                        queue.add(Grid.cellGrid.get((position + GUI.GRIDSIZE)));
                        queue.add(Grid.cellGrid.get((position + GUI.GRIDSIZE + 1)));
                        queue.add(Grid.cellGrid.get((position + 1)));
                    } else if (position % GUI.GRIDSIZE == GUI.GRIDSIZE - 1) { //if the cell is in the top-right corner
                        queue.add(Grid.cellGrid.get((position + GUI.GRIDSIZE)));
                        queue.add(Grid.cellGrid.get((position + GUI.GRIDSIZE - 1)));
                        queue.add(Grid.cellGrid.get((position - 1)));
                    } else { // cells in the top row but not in the corners
                        queue.add(Grid.cellGrid.get((position + GUI.GRIDSIZE)));
                        queue.add(Grid.cellGrid.get((position + GUI.GRIDSIZE + 1)));
                        queue.add(Grid.cellGrid.get((position + GUI.GRIDSIZE - 1)));
                        queue.add(Grid.cellGrid.get((position + 1)));
                        queue.add(Grid.cellGrid.get((position - 1)));
                    }
                } else if (position >= (GUI.GRIDSIZE * (GUI.GRIDSIZE - 1))) { //if the cell is in the last row
                    if (position % GUI.GRIDSIZE == 0) { //topmost left-hand corner
                    /*we take all the adjacent cells of the cell we have clicked on,
                    and we add them to our queue, which we are going to iterate through*/
                        queue.add(Grid.cellGrid.get((position - GUI.GRIDSIZE)));
                        queue.add(Grid.cellGrid.get((position - GUI.GRIDSIZE + 1)));
                        queue.add(Grid.cellGrid.get((position + 1)));
                    } else if (position % GUI.GRIDSIZE == GUI.GRIDSIZE - 1) { //if the cell is in the top-right corner
                        queue.add(Grid.cellGrid.get((position - GUI.GRIDSIZE)));
                        queue.add(Grid.cellGrid.get((position - GUI.GRIDSIZE - 1)));
                        queue.add(Grid.cellGrid.get((position - 1)));
                    } else { // cells in the top row but not in the corners
                        queue.add(Grid.cellGrid.get((position - GUI.GRIDSIZE)));
                        queue.add(Grid.cellGrid.get((position - GUI.GRIDSIZE + 1)));
                        queue.add(Grid.cellGrid.get((position - GUI.GRIDSIZE - 1)));
                        queue.add(Grid.cellGrid.get((position + 1)));
                        queue.add(Grid.cellGrid.get((position - 1)));
                    }
                } else if (position % GUI.GRIDSIZE == 0) { //being in the leftmost column
                    queue.add(Grid.cellGrid.get((position - GUI.GRIDSIZE)));
                    queue.add(Grid.cellGrid.get((position + GUI.GRIDSIZE)));
                    queue.add(Grid.cellGrid.get((position - GUI.GRIDSIZE + 1)));
                    queue.add(Grid.cellGrid.get((position + GUI.GRIDSIZE + 1)));
                    queue.add(Grid.cellGrid.get((position + 1)));
                } else if (position % GUI.GRIDSIZE == GUI.GRIDSIZE - 1) { //being in the rightmost column
                    queue.add(Grid.cellGrid.get((position - GUI.GRIDSIZE)));
                    queue.add(Grid.cellGrid.get((position + GUI.GRIDSIZE)));
                    queue.add(Grid.cellGrid.get((position - GUI.GRIDSIZE - 1)));
                    queue.add(Grid.cellGrid.get((position + GUI.GRIDSIZE - 1)));
                    queue.add(Grid.cellGrid.get((position - 1)));
                } else {
                    queue.add(Grid.cellGrid.get((position - GUI.GRIDSIZE)));
                    queue.add(Grid.cellGrid.get((position + GUI.GRIDSIZE)));
                    queue.add(Grid.cellGrid.get((position - GUI.GRIDSIZE - 1)));
                    queue.add(Grid.cellGrid.get((position + GUI.GRIDSIZE - 1)));
                    queue.add(Grid.cellGrid.get((position - GUI.GRIDSIZE + 1)));
                    queue.add(Grid.cellGrid.get((position + GUI.GRIDSIZE + 1)));
                    queue.add(Grid.cellGrid.get((position + 1)));
                    queue.add(Grid.cellGrid.get((position - 1)));
                }
            } else if (cell.getType() == 1) { //handle the event of clicking on a cell that has a mine underneath it
                for (int j = 0; j < Grid.cellGrid.size(); j++) { //disable all of our cells
                    Grid.cellGrid.get(j).setDisable(true);
                    Grid.cellGrid.get(j).setText(""); //no text inside any of the cells that are not mines
                    if (Grid.cellGrid.get(j).getType() == 1) { //if cell is a mine cell
                        Grid.cellGrid.get(j).setText("X"); // the cells that have mines hidden underneath them are revealed with an "X"
                        Grid.cellGrid.get(j).setBackground(Background.fill(Color.LIGHTCORAL));
                    }
                    else if (Grid.cellGrid.get(j).getType() == 3) { //if cell is a mine cell
                        Grid.cellGrid.get(j).setText("X"); // the cells that have mines hidden underneath them are revealed with an "X"
                        Grid.cellGrid.get(j).setBackground(Background.fill(Color.LIGHTGOLDENRODYELLOW));
                    }
                }
                cell.setTextFill(Color.RED); //the cell we have clicked on that made us lose the game is distinguished by an "*"
                GUI.timer.cancel();
                GUI.gameEnded = true;
                GUI.TOTALGAMETIME[GUI.GAMES] = (GUI.MAX_TIME - GUI.remainingTime);
                GUI.WINNER[GUI.GAMES] = "computer";
                if (GUI.GAMES == 5) {
                    GUI.gameCounter(GUI.GAMES);
                }
               /* System.out.println(GUI.WINNER[GUI.GAMES]);
                System.out.println(GUI.GAMES);
                System.out.println(GUI.TOTALGAMETIME[GUI.GAMES]); //used for debugging
                System.out.println(GUI.WINNER[GUI.GAMES] + " " + GUI.WINNER[4]);
                */
                GUI.show("Oops! You clicked on a mine!");
            } else if (cell.getType() == 2) {
                /*the functionality will be how we will determine the number*/
                int mineCount = 0;
                if (position < GUI.GRIDSIZE) {
                    if (position % GUI.GRIDSIZE == 0) { //check if this is top left corner
                        if (Grid.cellGrid.get((position + GUI.GRIDSIZE)).getType() == 1) mineCount++;
                        if (Grid.cellGrid.get((position + GUI.GRIDSIZE + 1)).getType() == 1) mineCount++;
                        if (Grid.cellGrid.get((position + 1)).getType() == 1) mineCount++;
                    } else if (position % GUI.GRIDSIZE == GUI.GRIDSIZE - 1) {     //check for top right corner
                        if (Grid.cellGrid.get((position + GUI.GRIDSIZE)).getType() == 1) mineCount++;
                        if (Grid.cellGrid.get((position + GUI.GRIDSIZE - 1)).getType() == 1) mineCount++;
                        if (Grid.cellGrid.get((position - 1)).getType() == 1) mineCount++;
                    } else { //fot the five cells that are adjacent to the top row
                        if (Grid.cellGrid.get((position + GUI.GRIDSIZE)).getType() == 1) mineCount++;
                        if (Grid.cellGrid.get((position + GUI.GRIDSIZE + 1)).getType() == 1) mineCount++;
                        if (Grid.cellGrid.get((position + GUI.GRIDSIZE - 1)).getType() == 1) mineCount++;
                        if (Grid.cellGrid.get((position + 1)).getType() == 1) mineCount++;
                        if (Grid.cellGrid.get((position - 1)).getType() == 1) mineCount++;
                    }
                } else if (position >= GUI.GRIDSIZE * (GUI.GRIDSIZE - 1)) {
                    if (position % GUI.GRIDSIZE == 0) { //check if this is top left corner
                        if (Grid.cellGrid.get((position - GUI.GRIDSIZE)).getType() == 1) mineCount++;
                        if (Grid.cellGrid.get((position - GUI.GRIDSIZE + 1)).getType() == 1) mineCount++;
                        if (Grid.cellGrid.get((position + 1)).getType() == 1) mineCount++;
                    } else if (position % GUI.GRIDSIZE == GUI.GRIDSIZE - 1) {     //check for top right corne
                        if (Grid.cellGrid.get((position - GUI.GRIDSIZE)).getType() == 1) mineCount++;
                        if (Grid.cellGrid.get((position - GUI.GRIDSIZE - 1)).getType() == 1) mineCount++;
                        if (Grid.cellGrid.get((position - 1)).getType() == 1) mineCount++;
                    } else { //fot the five cells that are adjacent to the top row
                        if (Grid.cellGrid.get((position - GUI.GRIDSIZE)).getType() == 1) mineCount++;
                        if (Grid.cellGrid.get((position - GUI.GRIDSIZE + 1)).getType() == 1) mineCount++;
                        if (Grid.cellGrid.get((position - GUI.GRIDSIZE - 1)).getType() == 1) mineCount++;
                        if (Grid.cellGrid.get((position + 1)).getType() == 1) mineCount++;
                        if (Grid.cellGrid.get((position - 1)).getType() == 1) mineCount++;
                    }
                } else if (position % GUI.GRIDSIZE == 0) {
                    if (Grid.cellGrid.get((position - GUI.GRIDSIZE)).getType() == 1) mineCount++;
                    if (Grid.cellGrid.get((position + GUI.GRIDSIZE)).getType() == 1) mineCount++;
                    if (Grid.cellGrid.get((position - GUI.GRIDSIZE + 1)).getType() == 1) mineCount++;
                    if (Grid.cellGrid.get((position + GUI.GRIDSIZE + 1)).getType() == 1) mineCount++;
                    if (Grid.cellGrid.get((position + 1)).getType() == 1) mineCount++;
                } else if (position % GUI.GRIDSIZE == GUI.GRIDSIZE - 1) {
                    if (Grid.cellGrid.get((position - GUI.GRIDSIZE)).getType() == 1) mineCount++;
                    if (Grid.cellGrid.get((position + GUI.GRIDSIZE)).getType() == 1) mineCount++;
                    if (Grid.cellGrid.get((position - GUI.GRIDSIZE - 1)).getType() == 1) mineCount++;
                    if (Grid.cellGrid.get((position + GUI.GRIDSIZE - 1)).getType() == 1) mineCount++;
                    if (Grid.cellGrid.get((position + 1)).getType() == 1) mineCount++;
                } else { //for all the remaining cells
                    if (Grid.cellGrid.get((position - GUI.GRIDSIZE)).getType() == 1) mineCount++;
                    if (Grid.cellGrid.get((position + GUI.GRIDSIZE)).getType() == 1) mineCount++;
                    if (Grid.cellGrid.get((position - GUI.GRIDSIZE - 1)).getType() == 1) mineCount++;
                    if (Grid.cellGrid.get((position + GUI.GRIDSIZE - 1)).getType() == 1) mineCount++;
                    if (Grid.cellGrid.get((position - GUI.GRIDSIZE + 1)).getType() == 1) mineCount++;
                    if (Grid.cellGrid.get((position + GUI.GRIDSIZE + 1)).getType() == 1) mineCount++;
                    if (Grid.cellGrid.get((position - 1)).getType() == 1) mineCount++;
                    if (Grid.cellGrid.get((position + 1)).getType() == 1) mineCount++;
                }
                cell.setText(String.valueOf(mineCount)); //assign the value of the chosen cell to the mineCount
            } else if (cell.getType() == 3 && GUI.ATTEMPTS[GUI.GAMES] <= 4) { //if it is a supermine and the number of attempts is less than or equal to 4
                // reveal all cells in the same row and column as this cell
               // System.out.println(position); used for debugging
                int row = position/ GUI.GRIDSIZE;
                for (int i = 0; i < GUI.GRIDSIZE; i++) {
                    //reveal the cells in the same column
                    Cell rowCell = Grid.cellGrid.get(row * GUI.GRIDSIZE + i );
                    if (!rowCell.isDiscovered()) {
                        rowCell.setDiscovered();
                        rowCell.setDisable(true);
                        if (rowCell.getType() == 1) { //if it's a mine
                            rowCell.setStyle("-fx-background-color: #FF0000;"); // set the mine cell's background color to red
                        } else if (rowCell.getType() == 2) {
                            rowCell.setText("");
                        }
                    }
                    //reveal the cells in the same column
                   Cell colCell = Grid.cellGrid.get( position % GUI.GRIDSIZE + i * GUI.GRIDSIZE  );
                    if (!colCell.isDiscovered()) {
                        colCell.setDiscovered();
                        colCell.setDisable(true);
                        if (colCell.getType() == 1) {
                            colCell.setStyle("-fx-background-color: #FF0000;");
                        } else if (colCell.getType() == 2) {
                            colCell.setText("");
                        }
                    }
                }
                cell.setBackground(Background.fill(Color.GOLD)); //when you have marked a supermine, the cell turns golden
            } else if (cell.getType() == 3 && GUI.ATTEMPTS[GUI.GAMES] > 4){
                for (int j = 0; j < Grid.cellGrid.size(); j++) { //disable all of our cells
                    Grid.cellGrid.get(j).setDisable(true);
                    Grid.cellGrid.get(j).setText(""); //no text inside any of the cells that are not mines
                    if (Grid.cellGrid.get(j).getType() == 1) { //if cell is a mine cell
                        Grid.cellGrid.get(j).setText("X"); // the cells that have mines hidden underneath them are revealed with an "X"
                        Grid.cellGrid.get(j).setBackground(Background.fill(Color.LIGHTCORAL));
                    }
                    else if (Grid.cellGrid.get(j).getType() == 3) { //if cell is a mine cell
                        Grid.cellGrid.get(j).setText("X"); // the cells that have mines hidden underneath them are revealed with an "X"
                        Grid.cellGrid.get(j).setBackground(Background.fill(Color.LIGHTGOLDENRODYELLOW));
                    }

                }
                cell.setText("X"); //the cell we have clicked on that made us lose the game is distinguished by an "X"
                cell.setBackground(Background.fill(Color.RED));
                GUI.timer.cancel();
                GUI.gameEnded = true;
                GUI.TOTALGAMETIME[GUI.GAMES] = (GUI.MAX_TIME - GUI.remainingTime);
                GUI.WINNER[GUI.GAMES] = "computer";
                if (GUI.GAMES == 5) {
                    GUI.gameCounter(GUI.GAMES);
                }
                //System.out.println(GUI.TOTALGAMETIME[GUI.GAMES]); used for debugging
                GUI.show("Oops! You clicked on a mine!");
            }
            for (int x = 0; x < queue.size(); x++) {
                if (!queue.get(x).isDiscovered()) {
                    current.add(queue.get(x));
                    queue.get(x).discovered = true; //avoid duplication
                } //the reason we have two different array lists is that the queue is going to populate any cell that is adjacent to the cell we are looking at
            } //the current list contains the cells we are actually going to handle
            queue.clear();
            while (!current.isEmpty()) {
                Cell temp = current.get(0); //the 1st item in our current array list
                current.remove(0);
                temp.clickButton();
            }
            for (int i = 0; i < Grid.cellGrid.size(); i++) {
                if (Grid.cellGrid.get(i).isDiscovered())
                    discoveredCells++; //keeping track of the cells that have been discovered
            }
            if (discoveredCells == Grid.cellGrid.size() - GUI.MINECOUNT) {
                for (int x = 0; x < Grid.cellGrid.size(); x++) {
                    if (Grid.cellGrid.get(x).getType() == 1) {
                        Grid.cellGrid.get(x).setDisable(true); //disable the cell
                        Grid.cellGrid.get(x).setText("X");
                        Grid.cellGrid.get(x).setBackground(Background.fill(Color.LIME));
                    }else if (Grid.cellGrid.get(x).getType() == 3) {
                        Grid.cellGrid.get(x).setDisable(true); //disable the cell
                        Grid.cellGrid.get(x).setText("X");
                        Grid.cellGrid.get(x).setBackground(Background.fill(Color.GOLD));
                    } else {
                        Grid.cellGrid.get(x).setDisable(true); //disable the cell
                        Grid.cellGrid.get(x).setText(" ");
                    }
                }
                GUI.timer.cancel();
                GUI.gameEnded = true;

                GUI.TOTALGAMETIME[GUI.GAMES] = (GUI.MAX_TIME - GUI.remainingTime);
                GUI.WINNER[GUI.GAMES] = "player";
                if (GUI.GAMES == 5) {
                    GUI.gameCounter(GUI.GAMES);
                }
                GUI.show("You won!");
            }
        }
    }

    /* functionality for the right click */

    /**
     * Implements the functionality of being able to flag a cell
     * @param cell: the cell the user wants to right-click on
     */
    public void rightClick(Cell cell) {
        if (!cell.isDiscovered()) {
            if (!cell.isFlagged()) {
                cell.setFlagged(true);
                cell.setText("F"); //indication that the cell is flagged
                flaggedCells++; //increment the number of cells that are flagged
                GUI.update(flaggedCells);
            } else {
                cell.setFlagged(false);
                cell.setText("");
                flaggedCells--;
                GUI.update(flaggedCells);
            }
        }
    }
}

