package com.example.medialab_minesweeper_el19948;

public class Results {
    private String firstColumn = "Games";
    private String secondColumn = "Attempts";
    private String thirdColumn = "Total Time";
    private String fourthColumn = "Winner";


    public Results() {
    }
    public Results(String firstColumn, String secondColumn, String thirdColumn, String fourthColumn){
        this.firstColumn = firstColumn;
        this.secondColumn = secondColumn;
        this.thirdColumn = thirdColumn;
        this.fourthColumn = fourthColumn;
    }
}