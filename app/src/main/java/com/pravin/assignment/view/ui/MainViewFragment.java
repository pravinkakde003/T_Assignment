package com.pravin.assignment.view.ui;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.pravin.assignment.R;
import com.pravin.assignment.service.adapter.DataAdapter;
import com.pravin.assignment.service.model.FactsModel;
import com.pravin.assignment.utils.NetworkUtils;
import com.pravin.assignment.viewmodel.MainViewModel;

import java.util.Objects;

import static android.support.v7.widget.LinearLayoutManager.VERTICAL;

public class MainViewFragment extends Fragment implements View.OnClickListener {
    SwipeRefreshLayout swipeRefreshLayout;
    RelativeLayout noInternetLayout;
    Button btnRetry;
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
        btnRetry=view.findViewById(R.id.btnRetry);
        btnRetry.setOnClickListener(this);
        recyclerView = view.findViewById(R.id.data_recycler_view);
        noInternetLayout = view.findViewById(R.id.noInternetLayout);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        swipeRefreshLayout.setRefreshing(true);
        fetchDataList();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchDataList();
            }
        });
    }

    private void fetchDataList() {
        final DataAdapter adapter = new DataAdapter();
        recyclerView.setAdapter(adapter);
        if (NetworkUtils.isNetworkAvailable(getContext())) {
            viewModel.getFacts().observe(this, new Observer<FactsModel>() {
                @Override
                public void onChanged(@Nullable FactsModel factsModel) {
                    if (factsModel != null) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                            Objects.requireNonNull(getActivity()).setTitle(factsModel.getTitle());
                        }
                        adapter.updateData(factsModel.getRows());
                    } else {
                        Toast.makeText(getContext(), R.string.error_message, Toast.LENGTH_LONG).show();
                    }
                    swipeRefreshLayout.setRefreshing(false);
                    noInternetLayout.setVisibility(View.GONE);
                }
            });
        } else {
            swipeRefreshLayout.setRefreshing(false);
            noInternetLayout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnRetry:
                fetchDataList();
                break;
        }
    }
}
