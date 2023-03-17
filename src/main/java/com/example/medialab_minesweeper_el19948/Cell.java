package com.example.medialab_minesweeper_el19948;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;

import java.lang.reflect.GenericArrayType;

public class Cell extends Button {
    private int type;
    private int position;
    boolean discovered;
    private boolean flagged;
    private Handler handler;

    public Cell(int type, int position, boolean discovered, boolean flagged , Handler handler){
        this.type = type;
        this.position = position;
        this.discovered = discovered;
        this.flagged = flagged;
        this.handler = handler;

        setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                clickButton();
            } else if (event.getButton() == MouseButton.SECONDARY) {
                rightClickButton();
            }
        });
    }

    /**
     * Returns the type of this cell.
     *
     * @return the type of this cell
     */
    public int getType() {
        return type;
    }

    /**
     * Returns the position of this cell.
     *
     * @return the type of this cell
     */

    public int getPosition(){
        return position;
    }
    /**
     * Returns if a cell has been discovered.
     *
     * @return the type of this cell
     */

    public boolean isDiscovered(){
        return discovered;
    }
    /**
     * It is a void function, thus it does not return somethinf.
     *
     * sets if a sell is discovered
     */
    public void setDiscovered(){
        this.discovered = true;
    }

    /**
     * Checks if the cell is flagged.
     *
     *
     */
    public boolean isFlagged(){
        return flagged;
    }

    /**
     * Sets the flagged status of this cell.
     *
     * @param f the new flagged status of this cell
     */
    public void setFlagged(boolean f){
        this.flagged = f;
    }

    /**
     * If the game has started, it allows the player to click on cells
     *
     */
    public void clickButton() {
       if(GUI.gameStarted == true) {
           handler.click(this);
         //  System.out.println(GUI.ATTEMPTS[GUI.GAMES]); used for debugging
       }
    }
    /**
     * If the game has started, it allows the player to right-click (flag) on cells
     *
     */
    public void rightClickButton(){
        if(GUI.gameStarted == true) handler.rightClick(this);
    }
}

