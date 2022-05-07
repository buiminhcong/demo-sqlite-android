package com.bmcong2k.sqlite.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bmcong2k.sqlite.R;
import com.bmcong2k.sqlite.model.Item;

import java.util.ArrayList;
import java.util.List;

public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.HomeViewHoler> {

    private List<Item> list;
    private ItemListener itemListener;

    public void setItemListener(ItemListener itemListener) {
        this.itemListener = itemListener;
    }

    public RecycleViewAdapter() {
        list = new ArrayList<>();
    }

    public void setList(List<Item> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public Item getItem(int po){
        return list.get(po);
    }

    @NonNull
    @Override
    public HomeViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new HomeViewHoler(v);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeViewHoler holder, int position) {

        Item item = getItem(position);
        holder.title.setText(item.getTitle());
        holder.category.setText(item.getCategory());
        holder.price.setText(item.getPrice());
        holder.date.setText(item.getDate());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class HomeViewHoler extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView title, category, price, date;

        public HomeViewHoler(@NonNull View itemView) {
            super(itemView);
            title= itemView.findViewById(R.id.tvTitle);
            category= itemView.findViewById(R.id.tvCategory);
            price= itemView.findViewById(R.id.tvPrice);
            date= itemView.findViewById(R.id.tvDate);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(itemListener != null){
                itemListener.onItemClick(v, getAdapterPosition());
            }
        }
    }

    // OVeright click item

    public interface ItemListener{
        void onItemClick(View view, int position);
    }

}
