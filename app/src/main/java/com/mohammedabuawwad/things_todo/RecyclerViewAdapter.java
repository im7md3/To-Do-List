package com.mohammedabuawwad.things_todo;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;


import java.util.ArrayList;
import java.util.List;



public class RecyclerViewAdapter extends Adapter<RecyclerViewAdapter.ViewHolder> implements Filterable {

    private Context context;
    private List<Item> itemList;
    private List<Item> forSearchList;

    public RecyclerViewAdapter(Context context, List<Item> itemList) {
        this.context = context;
        this.itemList = itemList;
        forSearchList = new ArrayList<>(itemList);
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_row, viewGroup, false);

        return new ViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder viewHolder, int position) {
        Item item = itemList.get(position);
        viewHolder.itemListName.setText(item.getListName());
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView itemListName;

        public ViewHolder(@NonNull View itemView, Context ctx) {
            super(itemView);
            context = ctx;

            itemListName = itemView.findViewById(R.id.nameOfList);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    Item item = itemList.get(position);
                    Intent intent = new Intent(context, TaskActivity.class);
                    intent.putExtra("listName", item.getListName());
                    intent.putExtra("listId",item.getListId());

                    context.startActivity(intent);
                }
            });

        }


    }

    @Override
    public Filter getFilter() {
        return itemSearch;
    }

    private Filter itemSearch = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Item> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0){
                filteredList.addAll(forSearchList);
            }else{
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Item item : forSearchList){
                    if (item.getListName().toLowerCase().contains(filterPattern)){
                        filteredList.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            itemList.clear();
            itemList.addAll((List)results.values);
            notifyDataSetChanged();
        }
    };
}
