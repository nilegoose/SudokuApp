package de.test.sudoku.recyclerAdapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import de.test.sudoku.R;
import de.test.sudoku.sudokuObject.Block;
import de.test.sudoku.sudokuRule.Check;

public class SingleBlockAdapter extends RecyclerView.Adapter <SingleBlockAdapter.SingleBlockViewHolder>{
    private static final String TAG = "SingleBlockAdapter";
    private SingleBlockViewHolder.OnChildItemClicked dropListener;
    private Integer[] squareList;
    private Integer[] wholeList;
    private Integer squareIndex;
    private ArrayList<Block> initialList;
    private ArrayList<Integer> skipList = new ArrayList<>();
    private Context context;

    public SingleBlockAdapter(Context context,
            ArrayList<Block> initialList,  Integer squareIndex, Integer[] wholeList){
        this.context = context;
        this.initialList = initialList;
        this.squareIndex = squareIndex;
        this.wholeList = wholeList;
        squareList = new Integer[9];
        for(int i = 0; i < 9; i++){
            squareList[i] = (wholeList[squareIndex * 9 + i] == null)? 0 : wholeList[squareIndex * 9 + i];
        }
        for(Block festBlock : initialList){
            if(festBlock.getIndex().getSquareIndex() == squareIndex){
                skipList.add(festBlock.getIndex().getPosition());
            }
        }
    }



    @Override
    public SingleBlockViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View slItemView = inflater.inflate(R.layout.single_block_item, null, false);
        // cannot use parent here

        SingleBlockAdapter.SingleBlockViewHolder holder =
                new SingleBlockAdapter.SingleBlockViewHolder(slItemView, initialList,
                        dropListener, skipList);
        return holder;
    }

    @Override
    public void onBindViewHolder(SingleBlockAdapter.SingleBlockViewHolder holder, int position) {
        String numberText = String.valueOf(squareList[position]);
        holder.numberBlock.setText(numberText);
        if(squareList[position] == 0){
            holder.numberBlock.setTextColor(context.getResources().getColor(R.color.white));
        }else if(skipList.contains(position)){
            holder.numberBlock.setTextColor(context.getResources().getColor(R.color.teal_200));
            holder.numberBlock.setTypeface(null, Typeface.BOLD);
        }else{
            Check check = new Check(wholeList);
            boolean red = check.moveNotValid(squareIndex, position);
            if(red){
                holder.numberBlock.setTextColor(context.getResources().getColor(R.color.red));
            }else{
                holder.numberBlock.setTextColor(context.getResources().getColor(R.color.black));
            }
        }
    }

    @Override
    public int getItemCount() {
        return 9;
    }

    public static class SingleBlockViewHolder extends RecyclerView.ViewHolder {
        private TextView numberBlock;
        private OnChildItemClicked dropListener;

        public SingleBlockViewHolder(View itemView, ArrayList<Block> initialList,
                                     OnChildItemClicked dropListener, ArrayList<Integer> skipList) {
            super(itemView);
            this.dropListener = dropListener;
            numberBlock = itemView.findViewById(R.id.number_block_recycler);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (getAdapterPosition()!= -1 && !skipList.contains(getAdapterPosition())){
                        numberBlock.setTypeface(null, Typeface.BOLD);
                        numberBlock.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                numberBlock.setTypeface(null, Typeface.NORMAL);
                            }
                        }, 1500);
                        dropListener.onBlockClick(getAdapterPosition());
                    }
                }
            });
        }

        public interface OnChildItemClicked{
            void onBlockClick(int position);
        }
    }


    public void setDropListener(SingleBlockViewHolder.OnChildItemClicked dropListener) {
        this.dropListener = dropListener;
    }

}
