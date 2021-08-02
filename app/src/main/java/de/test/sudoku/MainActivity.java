package de.test.sudoku;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;

import de.test.sudoku.database.TemplateHelper;
import de.test.sudoku.fragments.MainFragment;
import de.test.sudoku.recyclerAdapter.NumberListAdapter;
import de.test.sudoku.recyclerAdapter.SquareAdapter;
import de.test.sudoku.sudokuObject.Block;
import de.test.sudoku.sudokuObject.Game;
import de.test.sudoku.sudokuRule.Check;

public class MainActivity extends AppCompatActivity{
    public static final String TAG = "MainActivity";
    private SquareAdapter squareAdapter;
    private NumberListAdapter listAdapter;
    private RecyclerView recyclerView, listRecyclerView;
    private Integer[] chooseNumberList = new Integer[]{1,2,3,4,5,6,7,8,9,0};//to pick up numbers from
    private static int selectedNumber;
    private Integer[] wholeList = new Integer[81];
    private ArrayList<Block> initialList;
    private String gameName ="NoName";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //addFragment(savedInstanceState);
        TemplateHelper dbHelper = new TemplateHelper(this);
        Game game = dbHelper.getGame("default template");
        if(game != null){
            gameName = game.getName();
            initialList = game.getInitial();
            wholeList = game.getGameState();
            for(Block b : initialList){
                wholeList[b.getIndex().getArrayIndex()] = b.getValue();
            }
            if(gameFinished()){
                Log.d("game Finished", ".............");
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("you win");
                builder.setCancelable(true);
                builder.show();
            }
        }
        recyclerView = findViewById(R.id.parent_recyc_main);
        listRecyclerView = findViewById(R.id.list_recyc_main);
        setNumberList();
        setRecycler();
    }

    public void setNumberList(){
        listAdapter = new NumberListAdapter(this);
        listAdapter.setListener(new NumberListAdapter.NumberViewHolder.ListItemListener() {
            @Override
            public void onSelect(int position) {
                selectedNumber = chooseNumberList[position];
            }
        });
        listRecyclerView.setAdapter(listAdapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 7);
        listRecyclerView.setLayoutManager(gridLayoutManager);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(listAdapter != null){
                    listRecyclerView.findViewHolderForAdapterPosition(0).itemView.performClick();
                }
            }
        }, 500);

    }
    public void setRecycler(){
        squareAdapter = new SquareAdapter(this, initialList, wholeList);
        squareAdapter.setChildListener(new SquareAdapter.SquareViewHolder.ChildListener() {
            @Override
            public void onClick(int parentPos, int childPos) {
                if(wholeList[9 * parentPos + childPos] != null && wholeList[9 * parentPos + childPos] == selectedNumber){
                    return;
                }
                wholeList[9 * parentPos + childPos] = selectedNumber;
                Game game = new Game(gameName, initialList, wholeList);
                TemplateHelper dbHelper = new TemplateHelper(MainActivity.this);
                dbHelper.saveGame(game);
                if(gameFinished()){
                    Log.d("game Finished", ".............");
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setMessage("you win");
                    builder.setCancelable(true);
                    builder.show();
                }
                squareAdapter.notifyDataSetChanged();
            }
        });
        recyclerView.setAdapter(squareAdapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 3);
        recyclerView.setLayoutManager(gridLayoutManager);

    }

    public boolean gameFinished(){
        Check check = new Check(wholeList);
        return check.gameFinished();
    }

    /**
    public void addFragment(Bundle savedInstanceState){
        if(findViewById(R.id.fragment_container_main) != null){
            if(savedInstanceState != null){
                return;
            }
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container_main, new MainFragment(), null)
                    .commit();
        }
    }*/
}