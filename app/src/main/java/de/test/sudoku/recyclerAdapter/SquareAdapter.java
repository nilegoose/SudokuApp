package de.test.sudoku.recyclerAdapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import de.test.sudoku.R;
import de.test.sudoku.sudokuObject.Block;

public class SquareAdapter extends RecyclerView.Adapter<SquareAdapter.SquareViewHolder> {
    public static final String TAG = "SquareAdapter";
    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
    private ArrayList<Integer> squareList;
    private RecyclerView singBlockRecycler;
    private ArrayList<Block> initialList;
    private SquareAdapter.SquareViewHolder.ChildListener childListener;
    private Integer[] wholeList;
    private Context context;

    public SquareAdapter(Context context, ArrayList<Block> initialList, Integer[] wholeList){
        this.wholeList = wholeList;
        this.initialList = initialList;
        this.context = context;
    }
    @Override
    public SquareViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.square_item, parent, false);
        SquareViewHolder holder = new SquareViewHolder(view, childListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(SquareAdapter.SquareViewHolder holder, int parentPos) {
        holder.setIsRecyclable(false);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(holder.singleBlockRecyc.getContext(), 3);
        SingleBlockAdapter childItemAdapter = new SingleBlockAdapter(context, initialList, parentPos, wholeList);
        childItemAdapter.setDropListener(new SingleBlockAdapter.SingleBlockViewHolder.OnChildItemClicked() {
            @Override
            public void onBlockClick(int position) {
                if(childListener != null && holder.getAdapterPosition() != -1 && position != -1){
                    childListener.onClick(holder.getAdapterPosition(), position);
                }
            }
        });
        /**
        childItemAdapter.setItemListener(new SingleShoppingItemAdapter.SingleItemViewHolder.OnChildItemClicked() {
            @Override
            public void onDelClick(int position) {
                if(onChildItem != null && holder.getAdapterPosition() != -1 && position != -1){
                    onChildItem.onClick(holder.getAdapterPosition(), position);
                }
            }
        }); */
        holder.singleBlockRecyc.setLayoutManager(gridLayoutManager);
        holder.singleBlockRecyc.setAdapter(childItemAdapter);
        holder.singleBlockRecyc.setRecycledViewPool(viewPool);
    }

    @Override
    public int getItemCount() {
        return 9;
    }

    public static class SquareViewHolder extends RecyclerView.ViewHolder{
        public RecyclerView singleBlockRecyc;
        public ChildListener childListener;

        public SquareViewHolder(View itemView, ChildListener childListener) {
            super(itemView);
            this.childListener = childListener;
            singleBlockRecyc = itemView.findViewById(R.id.square_recyc_main);

    }
        public interface ChildListener{
            void onClick(int parentPos, int childPos);
        }
    }

    public void setChildListener(SquareViewHolder.ChildListener childListener) {
        this.childListener = childListener;
    }
}
