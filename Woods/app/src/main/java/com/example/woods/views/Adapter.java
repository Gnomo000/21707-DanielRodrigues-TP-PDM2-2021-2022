package com.example.woods.views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.woods.R;
import com.example.woods.model.User;
import com.example.woods.model.Woods;

import java.util.ArrayList;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private List<Woods> woodsList;
    private User user;
    private Context context;
    private LayoutInflater layoutInflater;

    public Adapter(Context context, User user) {
        this.context = context;
        this.woodsList = new ArrayList<>();
        this.user = user;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = layoutInflater.inflate(R.layout.layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.ViewHolder holder, int position) {
        Woods woods = this.woodsList.get(position);
        User user = this.user;
        holder.wood_type.setText(woods.getWood_type());
        holder.color.setText(woods.getColor());
        holder.size.setText(woods.getSize());
        holder.quantity.setText(Integer.toString(woods.getQuantity()));
        holder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WoodsDetailsFragment.startFragment(woods.getId(), v, user);
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.woodsList.size();
    }

    public void updateList(List<Woods> newList) {
        this.woodsList = newList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView wood_type;
        private final TextView color;
        private final TextView size;
        private final TextView quantity;
        private View root;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.wood_type = itemView.findViewById(R.id.textViewWoodsTypeChange);
            this.color = itemView.findViewById(R.id.textViewWoodsColorChange);
            this.size = itemView.findViewById(R.id.textViewWoodsSizeChange);
            this.quantity = itemView.findViewById(R.id.textViewWoodsQuantityChange);
            this.root = itemView;
        }
    }
}
