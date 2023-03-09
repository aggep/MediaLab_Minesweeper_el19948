package com.example.medialab_minesweeper_el19948;

public class Game {

    public static int TIME = 120; //this will determine the time of the game
    public static int GRIDSIZE = 9;
    public static int MINECOUNT = 10; //how many mines there will be in the game - this will also have to be altered



    public Game() {
        new GUI(); //we call the GUI class to implement the game's interface
       GUI.update(0);
    }
    public static void main(String[] args){
        new Game();
    }
}

