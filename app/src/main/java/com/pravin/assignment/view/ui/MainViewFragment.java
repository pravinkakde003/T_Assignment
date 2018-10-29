package com.pravin.assignment.view.ui;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.pravin.assignment.R;
import com.pravin.assignment.service.adapter.ListDataAdapter;
import com.pravin.assignment.service.model.FactsModel;
import com.pravin.assignment.service.model.FeedModel;
import com.pravin.assignment.utils.NetworkUtils;
import com.pravin.assignment.viewmodel.MainViewModel;

import java.util.Objects;

/**
 * MainView Fragment to show the List feeds
 */
public class MainViewFragment extends Fragment implements View.OnClickListener,ListDataAdapter.PostsAdapterListener {
    SwipeRefreshLayout swipeRefreshLayout;
    RelativeLayout noInternetLayout;
    Button btnRetry;
    RecyclerView recyclerView;
    MainViewModel viewModel;

    public MainViewFragment() {

    }

    /**
     * @param context
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
    }

    /**
     * @param inflater Layout inflater object
     * @param container Container where to load fragment
     * @param savedInstanceState Bundle object to save fragment state
     * @return Inflated view in fragment container.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mainview, container, false);
        initRecyclerView(view);
        return view;
    }

    /** Initialize the view
     * @param view
     */
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


    /**
     * Fetch Data from API and Update
     */
    private void fetchDataList() {
        final ListDataAdapter adapter = new ListDataAdapter(this);
        recyclerView.setAdapter(adapter);
        if (NetworkUtils.isNetworkAvailable(getContext())) {
            viewModel.getFacts().observe(this, new Observer<FactsModel>() {
                @Override
                public void onChanged(@Nullable FactsModel factsModel) {
                    if (factsModel != null) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                            Objects.requireNonNull(getActivity()).setTitle(factsModel.getTitle());
                        }
                        adapter.updateDataInRecyclerView(factsModel.getRows());
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

    /** Handle Click Listener on view
     * @param view
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnRetry:
                fetchDataList();
                break;
        }
    }

    @Override
    public void onFeedClicked(FeedModel feedModel) {
        Toast toast = Toast.makeText(getActivity(), "Clicked On: " + feedModel.getTitle(), Toast.LENGTH_SHORT);
        View view = toast.getView();
        view.getBackground().setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_IN);
        TextView text = view.findViewById(android.R.id.message);
        text.setTextColor(getResources().getColor(R.color.white));
        toast.show();
    }
}
