package com.example.woods.views;

import android.app.Application;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import com.example.woods.R;
import com.example.woods.ViewModels.OrdersViewModel;
import com.example.woods.model.Orders;
import com.example.woods.model.User;
import com.example.woods.model.Woods;
import com.example.woods.model.WoodsRepository;

import java.util.ArrayList;
import java.util.List;

public class AdapterOrders extends RecyclerView.Adapter<AdapterOrders.ViewHolder> {

    private List<Orders> ordersList;
    private Context context;
    private LayoutInflater layoutInflater;
    private WoodsRepository woodsRepository;
    private LifecycleOwner getViewLifecycleOwner;

    public AdapterOrders(Context context, @NonNull Application application, LifecycleOwner getViewLifecycleOwner) {
        this.context = context;
        this.ordersList = new ArrayList<>();
        this.layoutInflater = LayoutInflater.from(context);
        this.woodsRepository = new WoodsRepository(application.getApplicationContext());
        this.getViewLifecycleOwner = getViewLifecycleOwner;
    }

    @NonNull
    @Override
    public AdapterOrders.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = layoutInflater.inflate(R.layout.layout_orders, parent, false);
        return new AdapterOrders.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterOrders.ViewHolder holder, int position) {
        Orders orders = this.ordersList.get(position);
        woodsRepository.getWoodById(orders.getWoods_id()).observe(getViewLifecycleOwner, new Observer<Woods>() {
            @Override
            public void onChanged(Woods woods) {
                holder.wood_type.setText(woods.getWood_type());
                holder.wood_color.setText(woods.getColor());
                holder.woods_size.setText(woods.getSize());
                holder.orders_quantity.setText(Integer.toString(orders.getQuantity()));
                holder.orders_street.setText(orders.getStreet());
            }
        });

    }

    @Override
    public int getItemCount() {
        return this.ordersList.size();
    }

    public void updateList(List<Orders> newList) {
        this.ordersList = newList;
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView wood_type;
        private final TextView wood_color;
        private final TextView woods_size;
        private final TextView orders_quantity;
        private final TextView orders_street;
        private View root;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.wood_type = itemView.findViewById(R.id.textViewOrdersTypeChange);
            this.wood_color = itemView.findViewById(R.id.textViewOrdersColorChange);
            this.woods_size = itemView.findViewById(R.id.textViewOrdersSizeChange);
            this.orders_street = itemView.findViewById(R.id.textViewOrdersStreetChange);
            this.orders_quantity = itemView.findViewById(R.id.textViewOrdersQuantityChange);
            this.root = itemView;
        }
    }
}
