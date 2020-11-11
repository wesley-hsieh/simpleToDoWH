package com.example.simpletodowh;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import static android.R.layout.simple_list_item_1;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ViewHolder>{

    public interface OnLongClickListener{
        void onItemLongClicked(int position);
    }

    List<String> items;
    OnLongClickListener longClickListener;
    public ItemsAdapter(List<String> items, OnLongClickListener longClickListener) {
        this.items = items;
        this.longClickListener = longClickListener;
    }

    @NonNull
    @Override
    //responsible for creating each view
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        //use layout inflator to inflate a view,
        //wrap it inside a view holder and return it
        View toDoView = LayoutInflater.from(parent.getContext()).inflate(simple_list_item_1, parent, false);
        return new ViewHolder(toDoView);
    }

    @Override
    //responsible for taking data at particular position and putting it into a respective viewholder
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        //grab the item at the position indicated,
        String item = items.get(position);
        //bind the item into the specified view holder
        viewHolder.bind(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
    //container to provide easy access to views that represent each row of the list

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvItem = itemView.findViewById(android.R.id.text1);
        }

        public void bind(String item){

            tvItem.setText(item);
            tvItem.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    //remove item from the view


                    //communicate the listener which position that was pressed
                    longClickListener.onItemLongClicked(getAdapterPosition());
                    return true;
                }
            });
        }


    }

}
