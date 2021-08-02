package de.test.sudoku.sudokuRule;

import android.util.Log;

import androidx.appcompat.app.AlertDialog;

import de.test.sudoku.MainActivity;

public class Check {
    private Integer[] wholeList;

    public Check(Integer[] wholeList) {
        this.wholeList = wholeList;
    }

    /**
     * checks if there is a same number within the same square
     * @param squareIndex 0,1,...8 index of square
     * @param position 0,1,...8 index of number block within a square
     * @return true if there is at least one same number within the square
     *         false if the number at given position is unique in this square
     */
    public boolean checkSquare(int squareIndex, int position){
        for(int i = 0; i < 9; i++){
            if(i != position && wholeList[squareIndex * 9 + i] != null
                    && wholeList[squareIndex * 9 + i] == wholeList[squareIndex * 9 + position]){
                return true;
            }
        }
        return false;
    }

    /**
     * checks if there is a same number within the same column
     * @param squareIndex 0,1,...8 index of square
     * @param position 0,1,...8 index of number block within a square
     * @return true if there is at least one same number within the column
     *         false if the number at given position is unique in this column
     */
    public boolean checkColumn(int squareIndex, int position){
        int index = squareIndex % 3;
        int col = position % 3;
        for(int i = index; i < 9; i += 3){
            for(int j = col; j < 9; j += 3){
                if(i != squareIndex && wholeList[i * 9 + j] != null
                        && wholeList[i * 9 + j] == wholeList[squareIndex * 9 + position]){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * checks if there is a same number within the same row
     * @param squareIndex 0,1,...8 index of square
     * @param position 0,1,...8 index of number block within a square
     * @return true if there is at least one same number within the row
     *         false if the number at given position is unique in this row
     */
    public boolean checkRow(int squareIndex, int position){
        int squareToCheck = squareIndex / 3 * 3;
        int rowPos = (position / 3) * 3;
        for(int i = squareToCheck; i < squareToCheck + 3; i++){
            for(int j = rowPos; j < rowPos + 3; j ++){
                if(i != squareIndex && wholeList[i * 9 + j] != null
                        && wholeList[i * 9 + j] == wholeList[squareIndex * 9 + position]){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * tells if the insertion at given position doesnt violet the rule
     * @param squareIndex 0,1,...8 index of square
     * @param position 0,1,...8 index of number block within a square
     * @return true if the number at given position unique in square, column and row
     */
    public boolean moveNotValid(int squareIndex, int position){
        return checkSquare(squareIndex,  position) ||
                checkColumn(squareIndex, position) || checkRow(squareIndex, position);
    }

    /**
     *
     */
    public boolean gameFinished(){
        if(wholeList == null){
            return false;
        }
        for(int i = 0; i < 81; i++){
            if(wholeList[i] == null || wholeList[i] == 0)
                return false;
        }
        for(int i = 0; i < 81; i++){
            if(moveNotValid(i / 9, i % 9)){
                return false;
            }
        }
        return true;
    }
}
