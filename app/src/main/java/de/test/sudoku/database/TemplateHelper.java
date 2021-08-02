package de.test.sudoku.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import de.test.sudoku.sudokuObject.Block;
import de.test.sudoku.sudokuObject.Game;
import de.test.sudoku.sudokuObject.Index;
import de.test.sudoku.database.TemplateContract.*;
import de.test.sudoku.sudokuObject.JsonMethods;


public class TemplateHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "SUDOKUTemplate.db";
    private static final int DATABASE_VERSION = 1;
    private SQLiteDatabase database;


    public TemplateHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        this.database = sqLiteDatabase;
        final String SQL_CREATE_SUDOKU_TEMPLATE = "create table " + FeedEntry.TABLE_NAME +
                " ( " + TemplateContract.FeedEntry._ID + " Integer primary key autoIncrement, " +
                FeedEntry.COLUMN_NAME_NAME + " text, " +
                FeedEntry.COLUMN_NAME_LIST + " text, " +
                FeedEntry.COLUMN_NAME_INI + " text" +
                " )";
        database.execSQL(SQL_CREATE_SUDOKU_TEMPLATE);
        giveDefaultTemp();
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        database.execSQL("drop table if exists " + FeedEntry.TABLE_NAME);
        onCreate(database);
    }

    private void giveDefaultTemp(){
        ArrayList<Block> initial = new ArrayList<>();
        initial.add(new Block(1, new Index(0, 0)));
        initial.add(new Block(2, new Index(1, 1)));
        initial.add(new Block(3, new Index(2, 2)));
        initial.add(new Block(4, new Index(3, 3)));
        initial.add(new Block(5, new Index(4, 4)));
        initial.add(new Block(6, new Index(5, 5)));
        initial.add(new Block(7, new Index(6, 6)));
        initial.add(new Block(8, new Index(7, 7)));
        initial.add(new Block(9, new Index(8, 8)));


        String arrayList = JsonMethods.toJson(initial);

        ContentValues cv = new ContentValues();
        cv.put(FeedEntry.COLUMN_NAME_NAME, "default template");
        cv.put(FeedEntry.COLUMN_NAME_INI, arrayList);
        cv.put(FeedEntry.COLUMN_NAME_LIST, "");
        database.insert(FeedEntry.TABLE_NAME, null, cv);
        database.close();
    }

    public Game getGame(String name){
        database = getReadableDatabase();
        Cursor cursor = database.rawQuery("select * from " + FeedEntry.TABLE_NAME
        + " where " + FeedEntry.COLUMN_NAME_NAME + " = '" + name + "';", null);
        if (cursor.moveToFirst()){
            Game game = new Game(name, null, null);
            String initialListString = cursor.getString(cursor.getColumnIndex(FeedEntry.COLUMN_NAME_INI));
            String stateString = cursor.getString(cursor.getColumnIndex(FeedEntry.COLUMN_NAME_LIST));
            game.setInitial(JsonMethods.fromJson(initialListString));
            game.setName(name);
            game.setGameState(gameStateFromString(stateString));
            cursor.close();
            database.close();
            return game;
        }else{
            Log.d("jsonTest!!", "else statement");
        }
        return null;
    }

    public void saveGame(Game game){
        String name = game.getName();
        String initial = JsonMethods.toJson(game.getInitial());
        Integer[] gameState = game.getGameState();
        String state = gameStateToString(gameState);
        ContentValues cv = new ContentValues();
        cv.put(FeedEntry.COLUMN_NAME_LIST, state);
        cv.put(FeedEntry.COLUMN_NAME_NAME, name);
        cv.put(FeedEntry.COLUMN_NAME_INI, initial);
        SQLiteDatabase db = getWritableDatabase();
        db.update(FeedEntry.TABLE_NAME, cv, FeedEntry.COLUMN_NAME_NAME + " = ?", new String[]{name});
        db.close();
    }

    /**
     * convert the integer array as game state to a string
     * @param gameState an integer array with the size 81
     * @return a string containing information for game state
     */
    public String gameStateToString(Integer[] gameState){
        String state = "";
        if(gameState == null){
            Log.d("gameStateToString", "TemplateHelper " + "null game state");
            return state;
        }
        for(int i = 0; i < 81; i++){
            if(gameState[i] == null){
                state += String.valueOf(0) + ",";
            } else {
                state += String.valueOf(gameState[i]) + ",";
            }
        }
        return state;
    }


    /**
     * convert the string from database into an integer array
     * @param s a string containing information for game state
     * @return an integer array as game state
     */
    public Integer[] gameStateFromString(String s){
        Integer[] gameState = new Integer[81];
        if(s.equals("")){
            return gameState;
        }
        String[] stateString = s.split(",");
        for(int i = 0; i < 81; i++){
            gameState[i] = Integer.parseInt(stateString[i]);
        }
        return gameState;
    }

}
