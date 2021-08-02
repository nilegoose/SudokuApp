package de.test.sudoku.sudokuObject;

public class Index {
    /**
     * squareIndex tells the index of a 3*3 square
     * position tells the index within the 3*3 square
     */
    private int squareIndex;
    private int position;

    public Index(int squareIndex, int position) {
        this.squareIndex = squareIndex;
        this.position = position;
    }

    public int getSquareIndex() {
        return squareIndex;
    }

    public void setSquareIndex(int squareIndex) {
        this.squareIndex = squareIndex;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    /**
     * gives the corresponding index in a flat integer array
     * @return index in an integer array
     */
    public int getArrayIndex(){
        return squareIndex * 9 + position;
    }
}
