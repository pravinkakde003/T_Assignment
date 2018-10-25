package com.pravin.assignment.view.ui;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.pravin.assignment.R;
import com.pravin.assignment.service.adapter.DataAdapter;
import com.pravin.assignment.service.model.FactsModel;
import com.pravin.assignment.viewmodel.MainViewModel;

import java.util.Objects;

import static android.support.v7.widget.LinearLayoutManager.VERTICAL;

public class MainViewFragment extends Fragment {

    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView recyclerView;
    MainViewModel viewModel;
    public MainViewFragment() {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mainview, container, false);
        initRecyclerView(view);
        return view;
    }

    private void initRecyclerView(View view) {
        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout);
        recyclerView = view.findViewById(R.id.data_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), VERTICAL));
        fetchDataList();
    }

    private void fetchDataList() {
        final DataAdapter adapter = new DataAdapter();
        recyclerView.setAdapter(adapter);
        viewModel.getFacts().observe(this, new Observer<FactsModel>() {
            @Override
            public void onChanged(@Nullable FactsModel factsModel) {
                if (factsModel != null) {
                    Objects.requireNonNull(getActivity()).setTitle(factsModel.getTitle());
                    adapter.updateData(factsModel.getRows());
                }else {
                    Toast.makeText(getContext(), "Failure_text", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
