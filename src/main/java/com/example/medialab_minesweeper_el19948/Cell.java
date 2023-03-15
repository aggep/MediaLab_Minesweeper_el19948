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

    public int getType() {
        return type;
    }

    public int getPosition(){
        return position;
    }

    public boolean isDiscovered(){
        return discovered;
    }

    public void setDiscovered(){
        this.discovered = true;
    }

    public boolean isFlagged(){
        return flagged;
    }

    public void setFlagged(boolean f){
        this.flagged = f;
    }

    public void clickButton() {
       if(GUI.gameStarted == true) {
           handler.click(this);
         //  System.out.println(GUI.ATTEMPTS[GUI.GAMES]); used for debugging
       }
    }

    public void rightClickButton(){
        if(GUI.gameStarted == true) handler.rightClick(this);
    }
}

