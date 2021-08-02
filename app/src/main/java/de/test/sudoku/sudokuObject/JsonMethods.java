package de.test.sudoku.sudokuObject;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonMethods {
    public static String toJson(ArrayList<Block> blockList){
        String s = "{\"name\":[null]}";
        if(blockList == null || blockList.size() == 0){
            return s;
        }
        JSONArray array = new JSONArray();

        for(Block b : blockList){
            try {
                JSONObject indexJson = new JSONObject();
                indexJson.put("squareIndex", b.getIndex().getSquareIndex());
                indexJson.put("position", b.getIndex().getPosition());
                JSONObject blockJson = new JSONObject();
                blockJson.put("index", indexJson);
                blockJson.put("value", b.getValue());
                array.put(blockJson);
            } catch (JSONException e){
                Log.e("JsonObjToJson", "unexpected JSON exception for one block object", e);
            }
        }
        try {
            JSONObject json = new JSONObject();
            json.put("name", array);
            s = json.toString();
        } catch (JSONException e) {
            Log.e("JsonObjToJson", "unexpected JSON exception converting list to json", e);
        }
        return s;
    }

    public static ArrayList<Block> fromJson(String jsonString){
        ArrayList<Block> blockList = new ArrayList<Block>();
        try {
            JSONObject blocks = new JSONObject(jsonString);
            JSONArray arr = blocks.getJSONArray("name");
            for(int i = 0; i < arr.length(); i++){
                JSONObject o = arr.getJSONObject(i);
                Block block = new Block(o.getInt("value"), new Index(
                        o.getJSONObject("index").getInt("squareIndex"),
                        o.getJSONObject("index").getInt("position")
                ));
                blockList.add(block);
            }
        } catch (JSONException e) {
            Log.e("JsonObjFromJson", "unexpected JSON exception", e);
        }
        return blockList;
    }
}
