package com.example.medialab_minesweeper_el19948;

import java.util.ArrayList;
import javafx.scene.layout.GridPane;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Grid extends GridPane {
    private int bound = Game.GRIDSIZE * Game.GRIDSIZE; //the max position
    private boolean picked = false;
    private ArrayList<Integer> mines = new ArrayList<Integer>();
    private int gridSize;

    public static ArrayList<Cell> cellGrid = new ArrayList<Cell>(); //we want to access this everywhere

    /*our constructor*/
    public Grid(int gridSize, Handler h) throws IOException {
        super();
        createCells(h);
        addCells();
        this.gridSize = Game.GRIDSIZE;
    }

    public void createCells(Handler h) throws IOException {
        //the first cells we will create are the mines
        BufferedWriter writer = new BufferedWriter(new FileWriter("mines.txt")); //we will need a buffer to write the text that has the positions of the mines
        try {
            for (int i = 1; i <= Game.MINECOUNT; i++) {
                //keep creating random numbers until all numbers are unique
                while (!picked) {
                    int minePos = (int) (Math.random() * bound);
                    //check if there are duplicates
                    if (!mines.contains(minePos)) {
                        //If that is not the case = we have unique value
                        mines.add(minePos);
                        picked = true;
                    }
                }
                picked = false;
            }
            for (int i = 0; i < bound; i++) { //from 0 to grids-1
                if (mines.contains(i)) { //if it is a mine
                    cellGrid.add(new Cell(1, i, false, false, h));
                    // write mine position to file
                    int row = i / Game.GRIDSIZE;
                    int col = i % Game.GRIDSIZE;
                    int notSuperMine = 1;
                    writer.write(row + "," + col + "," + notSuperMine + "\n");
                } else if (i % Game.GRIDSIZE == 0) { //for the left side
                    if (
                            mines.contains(i - Game.GRIDSIZE) || //directly above
                                    mines.contains(i - Game.GRIDSIZE + 1) || //directly above to the right
                                    mines.contains(i - 1) || mines.contains(i + 1) ||
                                    mines.contains(i + Game.GRIDSIZE) || //directly below
                                    mines.contains((i + Game.GRIDSIZE + 1))) //directly below to the right
                    {
                        cellGrid.add(new Cell(2, i, false, false, h));
                    } else {
                        cellGrid.add(new Cell(0, i, false, false, h));
                    }
                } else if (i % Game.GRIDSIZE == Game.GRIDSIZE - 1) {
                    if (mines.contains(i - Game.GRIDSIZE - 1) || //to the left above it
                            mines.contains(i - Game.GRIDSIZE) || //directly above

                            mines.contains(i - 1) || mines.contains(i + Game.GRIDSIZE - 1) || //bottom to the left
                            mines.contains(i + Game.GRIDSIZE)) {
                        cellGrid.add(new Cell(2, i, false, false, h));
                    } else {
                        cellGrid.add(new Cell(0, i, false, false, h));
                    }

                } else {
                    if (mines.contains(i - Game.GRIDSIZE - 1) || //to the left above it
                            mines.contains(i - Game.GRIDSIZE) || //directly above
                            mines.contains(i - Game.GRIDSIZE + 1) || //directly above to the right
                            mines.contains(i - 1) || mines.contains(i + 1) || mines.contains(i + Game.GRIDSIZE - 1) || //bottom to the left
                            mines.contains(i + Game.GRIDSIZE) || //directly below
                            mines.contains((i + Game.GRIDSIZE + 1))) //directly below to the right
                    {
                        cellGrid.add(new Cell(2, i, false, false, h));
                    } else {
                        cellGrid.add(new Cell(0, i, false, false, h));
                    }

                }


            }
            writer.close();
        } catch(IOException e){
            System.err.println("Failed to save mine positions to file.");
        }

    }
    private void addCells() {
        for (int i = 0; i < cellGrid.size(); i++) {
            add(cellGrid.get(i), i % Game.GRIDSIZE, i / Game.GRIDSIZE); // add cell to GridPane at specified row and column
        }
    }

}
