package com.example.medialab_minesweeper_el19948;

import java.util.ArrayList;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Grid extends GridPane {

    private static final int bound = Game.GRIDSIZE * Game.GRIDSIZE; //the max position
    private static boolean picked = false;
    public static ArrayList<Integer> mines = new ArrayList<Integer>();

    public static ArrayList<Cell> cellGrid = new ArrayList<Cell>(); //we want to access this everywhere

    /*our constructor*/
    public Grid(Handler h) throws IOException {
        super();
        createCells(h);
        addCells();

    }

    public static void createCells(Handler h) throws IOException {
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

        File file = new File(folder, "mines.txt");
        //the first cells we will create are the mines
        BufferedWriter writer = new BufferedWriter(new FileWriter(file)); //we will need a buffer to write the text that has the positions of the mines
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
    public void addCells() {
        for (int i = 0; i < cellGrid.size(); i++) {
            add(cellGrid.get(i), i % Game.GRIDSIZE, i / Game.GRIDSIZE); // add cell to GridPane at specified row and column
        }
    }




}
