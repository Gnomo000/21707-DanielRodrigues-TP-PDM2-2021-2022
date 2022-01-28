package com.example.woods.views;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.woods.ViewModels.OrdersViewModel;
import com.example.woods.R;
import com.example.woods.model.Orders;
import com.example.woods.model.Woods;

import java.util.List;

public class OrdersFragment extends Fragment {

    private OrdersViewModel mViewModel;
    private AdapterOrders adapter;
    private Orders orders;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.orders_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(OrdersViewModel.class);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView2);
        this.adapter = new AdapterOrders(this.getContext(), requireActivity().getApplication(),getViewLifecycleOwner());
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setAdapter(this.adapter);

        this.mViewModel.getOrders(mViewModel.getActiveSession()).observe(getViewLifecycleOwner(), new Observer<List<Orders>>() {
            @Override
            public void onChanged(List<Orders> orders) {
                OrdersFragment.this.adapter.updateList(orders);
            }
        });

    }
}