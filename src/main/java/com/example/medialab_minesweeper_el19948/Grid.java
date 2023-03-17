package com.example.medialab_minesweeper_el19948;

import java.util.ArrayList;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;

public class Grid extends GridPane {
    public static int bound = GUI.GRIDSIZE * GUI.GRIDSIZE; //the max position
    private static boolean picked = false;
    public static ArrayList<Integer> mines = new ArrayList<>();

    public static ArrayList<Cell> cellGrid = new ArrayList<>(); //we want to access this everywhere
    public static ArrayList<Integer> SuperMine = new ArrayList<>();
    static Handler h;

    static {
        try {
            h = new Handler();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /*our constructor*/
    public Grid(Handler h) throws IOException {
        super();
        createCells(h);
        addCells(this);

    }
    public static void createCells(Handler h) throws IOException {
        
        // Create the file chooser to select the directory
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save description file");

        String folderPath = System.getProperty("user.home") + "/medialab"; //direct the file to the medialab folder
        File folder = new File(folderPath);
        if (!folder.exists()) { //if the folder does not exist,
            folder.mkdir(); //create one in the folder in the default folder of your computer
        }

        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text files", "*.txt"));
       // System.out.println(LoadPopup.SuperMine); used for debugging
        File file = new File(folder, "mines.txt");
        //the first cells we will create are the mines
        BufferedWriter writer = new BufferedWriter(new FileWriter(file)); //we will need a buffer to write the text that has the positions of the mines
        try {
            if(LoadPopup.SuperMine == 1) { //if there is a supermine
                for (int i = 1; i <= GUI.MINECOUNT-1; i++) { //out of the MINECOUNT one of the mines is going to be a supermine
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
                while(!picked){
                    int SuperminePos = (int) (Math.random()*bound);
                    SuperMine.add(SuperminePos);
                    picked = true;
                }
                for (int i = 0; i < bound; i++) { //from 0 to grids - 1
                    if (mines.contains(i)) { //if it is a mine
                        cellGrid.add(new Cell(1, i, false, false, h));
                        //write mine position to file
                        int row = i / GUI.GRIDSIZE;
                        int col = i % GUI.GRIDSIZE;
                        int notSuperMine = 1;
                        writer.write(row + "," + col + "," + notSuperMine + "\n"); //in the txt file the number 1 indicates that there is no mine
                    } else if(SuperMine.contains(i)){
                        cellGrid.add(new Cell(3, i, false, false, h));
                        //write mine position to file
                        int row = i / GUI.GRIDSIZE;
                        int col = i % GUI.GRIDSIZE;
                        System.out.println(row+" "+ col); //used for debugging - to find the supermine more easily
                        int notSuperMine = 0;
                        writer.write(row + "," + col + "," + notSuperMine + "\n"); //in the txt file the number 0 indicates that there is a mine
                    } else if (i % GUI.GRIDSIZE == 0) { //for the left side
                        if (mines.contains(i - GUI.GRIDSIZE) || //directly above
                                mines.contains(i - GUI.GRIDSIZE + 1) || //directly above to the right
                                mines.contains(i - 1) || mines.contains(i + 1) ||
                                mines.contains(i + GUI.GRIDSIZE) || //directly below
                                mines.contains((i + GUI.GRIDSIZE + 1))) //directly below to the right
                        {
                            cellGrid.add(new Cell(2, i, false, false, h));
                        } else {
                            cellGrid.add(new Cell(0, i, false, false, h));
                        }
                    } else if (i % GUI.GRIDSIZE == GUI.GRIDSIZE - 1) {
                        if (mines.contains(i - GUI.GRIDSIZE - 1) || //to the left above it
                                mines.contains(i - GUI.GRIDSIZE) || //directly above
                                mines.contains(i - 1) || mines.contains(i + GUI.GRIDSIZE - 1) || //bottom to the left
                                mines.contains(i + GUI.GRIDSIZE)) {
                            cellGrid.add(new Cell(2, i, false, false, h));
                        } else {
                            cellGrid.add(new Cell(0, i, false, false, h));
                        }
                    } else {
                        if (mines.contains(i - GUI.GRIDSIZE - 1) || //to the left above it
                                mines.contains(i - GUI.GRIDSIZE) || //directly above
                                mines.contains(i - GUI.GRIDSIZE + 1) || //directly above to the right
                                mines.contains(i - 1) || mines.contains(i + 1) || mines.contains(i + GUI.GRIDSIZE - 1) || //bottom to the left
                                mines.contains(i + GUI.GRIDSIZE) || //directly below
                                mines.contains((i + GUI.GRIDSIZE + 1))) //directly below to the right
                        {
                            cellGrid.add(new Cell(2, i, false, false, h));
                        } else {
                            cellGrid.add(new Cell(0, i, false, false, h));
                        }
                    }
                }
            } else {
                for (int i = 1; i <= GUI.MINECOUNT; i++) { //out of the MINECOUNT one of the mines is going to be a supermine
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
                        int row = i / GUI.GRIDSIZE;
                        int col = i % GUI.GRIDSIZE;
                        int notSuperMine = 1;
                        writer.write(row + "," + col + "," + notSuperMine + "\n");
                    } else if (i % GUI.GRIDSIZE == 0) { //for the left side
                        if (mines.contains(i - GUI.GRIDSIZE) || //directly above
                                mines.contains(i - GUI.GRIDSIZE + 1) || //directly above to the right
                                mines.contains(i - 1) || mines.contains(i + 1) ||
                                mines.contains(i + GUI.GRIDSIZE) || //directly below
                                mines.contains((i + GUI.GRIDSIZE + 1))) //directly below to the right
                        {
                            cellGrid.add(new Cell(2, i, false, false, h));
                        } else {
                            cellGrid.add(new Cell(0, i, false, false, h));
                        }
                    } else if (i % GUI.GRIDSIZE == GUI.GRIDSIZE - 1) {
                        if (mines.contains(i - GUI.GRIDSIZE - 1) || //to the left above it
                                mines.contains(i - GUI.GRIDSIZE) || //directly above
                                mines.contains(i - 1) || mines.contains(i + GUI.GRIDSIZE - 1) || //bottom to the left
                                mines.contains(i + GUI.GRIDSIZE)) {
                            cellGrid.add(new Cell(2, i, false, false, h));
                        } else {
                            cellGrid.add(new Cell(0, i, false, false, h));
                        }
                    } else {
                        if (mines.contains(i - GUI.GRIDSIZE - 1) || //to the left above it
                                mines.contains(i - GUI.GRIDSIZE) || //directly above
                                mines.contains(i - GUI.GRIDSIZE + 1) || //directly above to the right
                                mines.contains(i - 1) || mines.contains(i + 1) || mines.contains(i + GUI.GRIDSIZE - 1) || //bottom to the left
                                mines.contains(i + GUI.GRIDSIZE) || //directly below
                                mines.contains((i + GUI.GRIDSIZE + 1))) //directly below to the right
                        {
                            cellGrid.add(new Cell(2, i, false, false, h));
                        } else {
                            cellGrid.add(new Cell(0, i, false, false, h));
                        }
                    }
                }
            }
            writer.close();
        } catch(IOException e){
            System.err.println("Failed to save mine positions to file.");
        }
       // System.out.println(bound+ " "+ GUI.GRIDSIZE); used for debugging
    }
    public static void addCells(Grid grid) {
        for (int i = 0; i < cellGrid.size(); i++) {
            grid.add(cellGrid.get(i), i % GUI.GRIDSIZE, i / GUI.GRIDSIZE); // add cell to GridPane at specified row and column
        }
    }
}
