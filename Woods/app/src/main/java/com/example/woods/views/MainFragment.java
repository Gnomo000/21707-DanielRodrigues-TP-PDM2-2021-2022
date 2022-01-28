package com.example.woods.views;

import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.woods.R;
import com.example.woods.ViewModels.LoginViewModel;
import com.example.woods.ViewModels.MainViewModel;
import com.example.woods.model.Woods;
import com.example.woods.model.local.AppDatabase;

import java.util.List;
import java.util.Objects;

public class MainFragment extends Fragment {

    private MainViewModel mViewModel;
    private Adapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.main_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        swipeRefreshLayout = view.findViewById(R.id.swipelayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mViewModel.updateList();
            }
        });
        

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        this.adapter = new Adapter(this.getContext(),mViewModel.getActiveSession());
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setAdapter(this.adapter);

        this.mViewModel.getWoods().observe(getViewLifecycleOwner(), new Observer<List<Woods>>() {
            @Override
            public void onChanged(List<Woods> woods) {
                MainFragment.this.adapter.updateList(woods);
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        
        setHasOptionsMenu(true);
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_home, menu);
        menu.add(0, 1, 1, menuIconWithText(Objects.requireNonNull(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_cart, null)), getResources().getString(R.string.MY_ORDERS)));
        menu.add(0, 2, 2, menuIconWithText(Objects.requireNonNull(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_edit, null)), getResources().getString(R.string.EDITPROFILE)));
        menu.add(0, 3, 3, menuIconWithText(Objects.requireNonNull(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_logout, null)), getResources().getString(R.string.LOGOUT)));
    }

    private CharSequence menuIconWithText(Drawable drawable, String title) {
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        SpannableString sb = new SpannableString("    " + title);
        ImageSpan imageSpan = new ImageSpan(drawable, ImageSpan.ALIGN_BOTTOM);
        sb.setSpan(imageSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        return sb;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case 1:
                NavDirections action0 = MainFragmentDirections.actionMainFragmentToOrdersFragment();
                NavHostFragment.findNavController(MainFragment.this).navigate(action0);
                return true;
            case 2:
                NavDirections action1 = MainFragmentDirections.actionMainFragmentToProfileFragment();
                NavHostFragment.findNavController(MainFragment.this).navigate(action1);
                return true;
            case 3:
                mViewModel.clearSession();
                NavDirections action2 = MainFragmentDirections.actionMainFragmentToLoginFragment();
                NavHostFragment.findNavController(MainFragment.this).navigate(action2);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}