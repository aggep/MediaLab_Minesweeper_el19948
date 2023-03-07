package com.example.medialab_minesweeper_el19948;

import java.util.ArrayList;

public class Handler {

    private static ArrayList<Cell> current = new ArrayList<Cell>(); //create two arrays that will handle the cells differently, depending on their "states"
    private static ArrayList<Cell> queue = new ArrayList<Cell>();
    private static int flaggedCells = 0; //keeps track of the flagged cells

    public static void click(Cell cell) {
        int discoveredCells = 0; //how many cells are discovered during the game
        if (!cell.isFlagged()) { //we do this because we should not be able to click on cells that are flagged
            cell.setDisable(true);
            cell.setDiscovered();

            int position = cell.getPosition();
            if (cell.getType() == 0) { //if cell to determine the type of cell (e.g. type 0 : empty, type 1: mine, type 2: numbered)
                if (position < Game.GRIDSIZE) { //if the position of the cell is between 0 and 9, in other words in the 1st row
                    if (position % Game.GRIDSIZE == 0) { //topmost left-hand corner
                    /*we take all the adjacent cells of the cell we have clicked on
                    ,and we add them to our queue, which we are going to iterate through*/
                        queue.add(Grid.cellGrid.get((position + Game.GRIDSIZE)));
                        queue.add(Grid.cellGrid.get((position + Game.GRIDSIZE + 1)));
                        queue.add(Grid.cellGrid.get((position + 1)));
                    } else if (position % Game.GRIDSIZE == Game.GRIDSIZE - 1) { //if the cell is in the top-right corner
                        queue.add(Grid.cellGrid.get((position + Game.GRIDSIZE)));
                        queue.add(Grid.cellGrid.get((position + Game.GRIDSIZE - 1)));
                        queue.add(Grid.cellGrid.get((position - 1)));
                    } else { // cells in the top row but not in the corners
                        queue.add(Grid.cellGrid.get((position + Game.GRIDSIZE)));
                        queue.add(Grid.cellGrid.get((position + Game.GRIDSIZE + 1)));
                        queue.add(Grid.cellGrid.get((position + Game.GRIDSIZE - 1)));
                        queue.add(Grid.cellGrid.get((position + 1)));
                        queue.add(Grid.cellGrid.get((position - 1)));
                    }
                } else if (position >= (Game.GRIDSIZE * (Game.GRIDSIZE - 1))) { //if the cell is in the last row
                    if (position % Game.GRIDSIZE == 0) { //topmost left-hand corner
                    /*we take all the adjacent cells of the cell we have clicked on,
                    and we add them to our queue, which we are going to iterate through*/
                        queue.add(Grid.cellGrid.get((position - Game.GRIDSIZE)));
                        queue.add(Grid.cellGrid.get((position - Game.GRIDSIZE + 1)));
                        queue.add(Grid.cellGrid.get((position + 1)));
                    } else if (position % Game.GRIDSIZE == Game.GRIDSIZE - 1) { //if the cell is in the top-right corner
                        queue.add(Grid.cellGrid.get((position - Game.GRIDSIZE)));
                        queue.add(Grid.cellGrid.get((position - Game.GRIDSIZE - 1)));
                        queue.add(Grid.cellGrid.get((position - 1)));
                    } else { // cells in the top row but not in the corners
                        queue.add(Grid.cellGrid.get((position - Game.GRIDSIZE)));
                        queue.add(Grid.cellGrid.get((position - Game.GRIDSIZE + 1)));
                        queue.add(Grid.cellGrid.get((position - Game.GRIDSIZE - 1)));
                        queue.add(Grid.cellGrid.get((position + 1)));
                        queue.add(Grid.cellGrid.get((position - 1)));
                    }
                } else if (position % Game.GRIDSIZE == 0) { //being in the leftmost column
                    queue.add(Grid.cellGrid.get((position - Game.GRIDSIZE)));
                    queue.add(Grid.cellGrid.get((position + Game.GRIDSIZE)));
                    queue.add(Grid.cellGrid.get((position - Game.GRIDSIZE + 1)));
                    queue.add(Grid.cellGrid.get((position + Game.GRIDSIZE + 1)));
                    queue.add(Grid.cellGrid.get((position + 1)));
                } else if (position % Game.GRIDSIZE == Game.GRIDSIZE - 1) { //being in the rightmost column
                    queue.add(Grid.cellGrid.get((position - Game.GRIDSIZE)));
                    queue.add(Grid.cellGrid.get((position + Game.GRIDSIZE)));
                    queue.add(Grid.cellGrid.get((position - Game.GRIDSIZE - 1)));
                    queue.add(Grid.cellGrid.get((position + Game.GRIDSIZE - 1)));
                    queue.add(Grid.cellGrid.get((position - 1)));
                } else {
                    queue.add(Grid.cellGrid.get((position - Game.GRIDSIZE)));
                    queue.add(Grid.cellGrid.get((position + Game.GRIDSIZE)));
                    queue.add(Grid.cellGrid.get((position - Game.GRIDSIZE - 1)));
                    queue.add(Grid.cellGrid.get((position + Game.GRIDSIZE - 1)));
                    queue.add(Grid.cellGrid.get((position - Game.GRIDSIZE + 1)));
                    queue.add(Grid.cellGrid.get((position + Game.GRIDSIZE + 1)));
                    queue.add(Grid.cellGrid.get((position + 1)));
                    queue.add(Grid.cellGrid.get((position - 1)));
                }
            } else if (cell.getType() == 1) { //handle the event of clicking on a cell that has a mine underneath it
                for (int j = 0; j < Grid.cellGrid.size(); j++) { //disable all of our cells
                    Grid.cellGrid.get(j).setDisable(true);
                    Grid.cellGrid.get(j).setText(""); //no text inside any of the cells that are not mines
                    if (Grid.cellGrid.get(j).getType() == 1) { //if cell is a mine cell
                        Grid.cellGrid.get(j).setText("X"); // the cells that have mines hidden underneath them are revealed with an "X"
                    }
                }
                cell.setText("*"); //the cell we have clicked on that made us lose the game is distinguished by an "*"
                GUI.show("Oops! You clicked on a mine!");
            } else if (cell.getType() == 2) {
                /*the functionality will be how we will determine the number*/
                int mineCount = 0;
                if (position < Game.GRIDSIZE) {
                    if (position % Game.GRIDSIZE == 0) { //check if this is top left corner
                        if (Grid.cellGrid.get((position + Game.GRIDSIZE)).getType() == 1) mineCount++;
                        if (Grid.cellGrid.get((position + Game.GRIDSIZE + 1)).getType() == 1) mineCount++;
                        if (Grid.cellGrid.get((position + 1)).getType() == 1) mineCount++;
                    } else if (position % Game.GRIDSIZE == Game.GRIDSIZE - 1) {     //check for top right corne
                        if (Grid.cellGrid.get((position + Game.GRIDSIZE)).getType() == 1) mineCount++;
                        if (Grid.cellGrid.get((position + Game.GRIDSIZE - 1)).getType() == 1) mineCount++;
                        if (Grid.cellGrid.get((position - 1)).getType() == 1) mineCount++;
                    } else { //fot the five cells that are adjacent to the top row
                        if (Grid.cellGrid.get((position + Game.GRIDSIZE)).getType() == 1) mineCount++;
                        if (Grid.cellGrid.get((position + Game.GRIDSIZE + 1)).getType() == 1) mineCount++;
                        if (Grid.cellGrid.get((position + Game.GRIDSIZE - 1)).getType() == 1) mineCount++;
                        if (Grid.cellGrid.get((position + 1)).getType() == 1) mineCount++;
                        if (Grid.cellGrid.get((position - 1)).getType() == 1) mineCount++;
                    }
                } else if (position >= Game.GRIDSIZE * (Game.GRIDSIZE - 1)) {
                    if (position % Game.GRIDSIZE == 0) { //check if this is top left corner
                        if (Grid.cellGrid.get((position - Game.GRIDSIZE)).getType() == 1) mineCount++;
                        if (Grid.cellGrid.get((position - Game.GRIDSIZE + 1)).getType() == 1) mineCount++;
                        if (Grid.cellGrid.get((position + 1)).getType() == 1) mineCount++;
                    } else if (position % Game.GRIDSIZE == Game.GRIDSIZE - 1) {     //check for top right corne
                        if (Grid.cellGrid.get((position - Game.GRIDSIZE)).getType() == 1) mineCount++;
                        if (Grid.cellGrid.get((position - Game.GRIDSIZE - 1)).getType() == 1) mineCount++;
                        if (Grid.cellGrid.get((position - 1)).getType() == 1) mineCount++;
                    } else { //fot the five cells that are adjacent to the top row
                        if (Grid.cellGrid.get((position - Game.GRIDSIZE)).getType() == 1) mineCount++;
                        if (Grid.cellGrid.get((position - Game.GRIDSIZE + 1)).getType() == 1) mineCount++;
                        if (Grid.cellGrid.get((position - Game.GRIDSIZE - 1)).getType() == 1) mineCount++;
                        if (Grid.cellGrid.get((position + 1)).getType() == 1) mineCount++;
                        if (Grid.cellGrid.get((position - 1)).getType() == 1) mineCount++;
                    }
                } else if (position % Game.GRIDSIZE == 0) {
                    if (Grid.cellGrid.get((position - Game.GRIDSIZE)).getType() == 1) mineCount++;
                    if (Grid.cellGrid.get((position + Game.GRIDSIZE)).getType() == 1) mineCount++;
                    if (Grid.cellGrid.get((position - Game.GRIDSIZE + 1)).getType() == 1) mineCount++;
                    if (Grid.cellGrid.get((position + Game.GRIDSIZE + 1)).getType() == 1) mineCount++;
                    if (Grid.cellGrid.get((position + 1)).getType() == 1) mineCount++;
                } else if (position % Game.GRIDSIZE == Game.GRIDSIZE - 1) {
                    if (Grid.cellGrid.get((position - Game.GRIDSIZE)).getType() == 1) mineCount++;
                    if (Grid.cellGrid.get((position + Game.GRIDSIZE)).getType() == 1) mineCount++;
                    if (Grid.cellGrid.get((position - Game.GRIDSIZE - 1)).getType() == 1) mineCount++;
                    if (Grid.cellGrid.get((position + Game.GRIDSIZE - 1)).getType() == 1) mineCount++;
                    if (Grid.cellGrid.get((position + 1)).getType() == 1) mineCount++;
                } else { //for all the remaining cells
                    if (Grid.cellGrid.get((position - Game.GRIDSIZE)).getType() == 1) mineCount++;
                    if (Grid.cellGrid.get((position + Game.GRIDSIZE)).getType() == 1) mineCount++;
                    if (Grid.cellGrid.get((position - Game.GRIDSIZE - 1)).getType() == 1) mineCount++;
                    if (Grid.cellGrid.get((position + Game.GRIDSIZE - 1)).getType() == 1) mineCount++;
                    if (Grid.cellGrid.get((position - Game.GRIDSIZE + 1)).getType() == 1) mineCount++;
                    if (Grid.cellGrid.get((position + Game.GRIDSIZE + 1)).getType() == 1) mineCount++;
                    if (Grid.cellGrid.get((position - 1)).getType() == 1) mineCount++;
                    if (Grid.cellGrid.get((position + 1)).getType() == 1) mineCount++;
                }
                cell.setText(String.valueOf(mineCount)); //assign the value of the chosen cell to the mineCount
            }
            for(int x = 0; x < queue.size(); x++) {
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
            for(int i = 0; i < Grid.cellGrid.size(); i++){
                if(Grid.cellGrid.get(i).isDiscovered()) discoveredCells++; //keeping track of the cells that have been discovered
            }
            if (discoveredCells == Grid.cellGrid.size() - Game.MINECOUNT) {
                for (int x = 0; x < Grid.cellGrid.size(); x++) {
                    if (Grid.cellGrid.get(x).getType() == 1) {
                        Grid.cellGrid.get(x).setDisable(true); //disable the cell
                        Grid.cellGrid.get(x).setText("X"); //check this after reading the instructions
                    } else {
                        Grid.cellGrid.get(x).setDisable(true); //disable the cell
                        Grid.cellGrid.get(x).setText("!");
                    }
                }
                GUI.show("You won!");
            }
        }
    }
    /* functionality for the right click */
    public void rightClick(Cell cell){
        if(!cell.isDiscovered()){
            if(!cell.isFlagged()){
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

