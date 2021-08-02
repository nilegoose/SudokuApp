package de.test.sudoku.sudokuObject;

import java.util.ArrayList;

public class Game {
    private String name;
    private ArrayList<Block> initial;
    private Integer[] gameState;

    public Game(String name, ArrayList<Block> initial, Integer[] gameState) {
        this.name = name;
        this.initial = initial;
        this.gameState = gameState;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Block> getInitial() {
        return initial;
    }

    public void setInitial(ArrayList<Block> initial) {
        this.initial = initial;
    }

    public Integer[] getGameState() {
        return gameState;
    }

    public void setGameState(Integer[] gameState) {
        this.gameState = gameState;
    }
}
