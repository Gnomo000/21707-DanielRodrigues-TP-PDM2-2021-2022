package com.example.woods.views;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.woods.R;
import com.example.woods.ViewModels.WoodsDetailsViewModel;
import com.example.woods.model.Orders;
import com.example.woods.model.User;
import com.example.woods.model.Woods;

public class WoodsDetailsFragment extends Fragment {

    private WoodsDetailsViewModel mViewModel;
    private static Fragment exampleFragment ;
    private static User user;

    public static void startFragment(int id, View v, User user) {
        Navigation.findNavController(v).navigate(R.id.action_mainFragment_to_woodsDetailsFragment);
        Bundle bundle = new Bundle();
        bundle.putInt(KEY_ITEMID,id);
        exampleFragment =  WoodsDetailsFragment.newInstance(id);
        WoodsDetailsFragment.user = user;
    }

    private static final String KEY_ITEMID = "ITEMID";
    private TextView woodType;
    private TextView woodColor;
    private TextView woodSize;
    private TextView woodQuantity;
    private Button addOrder;
    private EditText orderStreet;
    private EditText orderQuantity;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.woods_details_fragment, container, false);
        
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(WoodsDetailsViewModel.class);

        this.woodType = view.findViewById(R.id.textViewWoodsTypeChange);
        this.woodColor = view.findViewById(R.id.textViewWoodsColorChange);
        this.woodSize = view.findViewById(R.id.textViewWoodsSizeChange);
        this.woodQuantity = view.findViewById(R.id.textViewWoodsQuantityChange);
        this.addOrder = view.findViewById(R.id.buttonAddOrder);
        this.orderStreet = view.findViewById(R.id.editTextStreetChange);
        this.orderQuantity = view.findViewById(R.id.editTextQuantityChange);

        Bundle bundle = exampleFragment.getArguments();
        if (bundle != null){
            int id = bundle.getInt(KEY_ITEMID,-1);
            if (id == -1) {
                Log.e("WoodsDetailsFragment", "Invalid position found!");
                requireActivity().finish();
                return;
            }

            this.mViewModel.getWoodById(id).observe(getViewLifecycleOwner(), new Observer<Woods>() {
                @Override
                public void onChanged(Woods woods) {
                    WoodsDetailsFragment.this.woodType.setText(woods.getWood_type());
                    WoodsDetailsFragment.this.woodColor.setText(woods.getColor());
                    WoodsDetailsFragment.this.woodSize.setText(woods.getSize());
                    WoodsDetailsFragment.this.woodQuantity.setText(String.valueOf(woods.getQuantity()));

                    WoodsDetailsFragment.this.addOrder.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            String street = WoodsDetailsFragment.this.orderStreet.getText().toString();
                            String quantity = WoodsDetailsFragment.this.orderQuantity.getText().toString();

                            if (!street.isEmpty() && !quantity.isEmpty()) {
                                if (Integer.parseInt(quantity) <= woods.getQuantity()) {
                                    Orders orders = Orders.createOrder(woods.getId(),WoodsDetailsFragment.user.getId(),street,Integer.parseInt(quantity),"Por Tratar");
                                    mViewModel.addOrder(orders);
                                    NavDirections action = WoodsDetailsFragmentDirections.actionWoodsDetailsFragmentToMainFragment();
                                    NavHostFragment.findNavController(WoodsDetailsFragment.this).navigate(action);
                                }else {
                                    Toast toast = Toast.makeText(WoodsDetailsFragment.this.getContext(), R.string.ERROR_ADDORDER_TWO,Toast.LENGTH_SHORT);
                                    toast.show();
                                }
                            }else {
                                Toast toast = Toast.makeText(WoodsDetailsFragment.this.getContext(), R.string.ERROR_ADDORDER,Toast.LENGTH_SHORT);
                                toast.show();
                            }
                        }
                    });
                }
            });
        }else {
            Log.e("PostDetailsFragment", "No position specified!");
            requireActivity().finish();
        }
    }

    public static WoodsDetailsFragment newInstance(int id) {
        WoodsDetailsFragment fragment = new WoodsDetailsFragment();

        Bundle args = new Bundle();
        args.putInt(KEY_ITEMID, id);
        fragment.setArguments(args);

        return fragment;
    }
}