package de.test.sudoku.sudokuObject;

public class Block {
    private int value;
    private Index index;

    public Block(int value, Index index) {
        this.value = value;
        this.index = index;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public Index getIndex() {
        return index;
    }

    public void setIndex(Index index) {
        this.index = index;
    }
}
