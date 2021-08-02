package de.test.sudoku.recyclerAdapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import de.test.sudoku.R;

public class NumberListAdapter extends RecyclerView.Adapter <NumberListAdapter.NumberViewHolder>{
    private static final String TAG = "NumberListAdapter";
    private Context context;
    private Integer[] numberList;
    private NumberListAdapter.NumberViewHolder.ListItemListener listener;
    private NumberViewHolder lastModified = null;


    public NumberListAdapter(Context context){
        this.context = context;
        numberList = new Integer[]{1,2,3,4,5,6,7,8,9};
    }



    @Override
    public NumberListAdapter.NumberViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View slItemView = inflater.inflate(R.layout.number_list_item, null, false);

        NumberListAdapter.NumberViewHolder holder =
                new NumberListAdapter.NumberViewHolder(slItemView, listener);
        return holder;
    }

    @Override
    public void onBindViewHolder(NumberListAdapter.NumberViewHolder holder, int position) {
        holder.setIsRecyclable(false);
        int number;
        if(position == 9){
            //holder.imageView.setVisibility(View.VISIBLE);
            holder.numberBlock.setText("-");
            number = 0;
        }else{
            number = numberList[position];
            holder.numberBlock.setText(String.valueOf(number));
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (position!= -1 ){
                    holder.numberBlock.setBackgroundColor(context.getResources().getColor(R.color.teal_200));
                    holder.numberBlock.setTextColor(context.getResources().getColor(R.color.black));
                    listener.onSelect(position);

                    if (lastModified != null){
                        int lastPosition = lastModified.getAdapterPosition();
                        if(lastPosition == position){
                            return;
                        }
                        lastModified.numberBlock.setBackgroundColor(context.getResources().getColor(R.color.white));
                        lastModified.numberBlock.setTextColor(context.getResources().getColor(R.color.teal_200));
                    }
                    lastModified = holder;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public static class NumberViewHolder extends RecyclerView.ViewHolder {
        private TextView numberBlock;
        private ListItemListener listener;
        private ImageView imageView;


        public NumberViewHolder(View itemView, ListItemListener listener) {
            super(itemView);
            this.listener = listener;
            imageView = itemView.findViewById(R.id.number_list_cancel);
            numberBlock = itemView.findViewById(R.id.number_list_textview);
        }

        public interface ListItemListener{
            void onSelect(int position);
        }
    }

    public void setListener(NumberViewHolder.ListItemListener listener) {
        this.listener = listener;
    }
}