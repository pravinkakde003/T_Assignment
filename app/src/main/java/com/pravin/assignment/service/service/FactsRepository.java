package com.pravin.assignment.service.service;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.gson.Gson;
import com.pravin.assignment.service.model.FactsModel;
import com.pravin.assignment.service.model.FeedModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/*
Repository to handle the network call to get facts feed
 */
public class FactsRepository {

    private static FactsRepository Instance = null;
    private MutableLiveData<FactsModel> factsModelMutableLiveData;
    public static FactsRepository getInstance() {
        if (Instance == null)
            Instance = new FactsRepository();
        return Instance;
    }


    /**
     * @return Fact Model Live data List
     */
    public LiveData<FactsModel> getFacts() {
        factsModelMutableLiveData  = new MutableLiveData<>();
        final APIInterface service = RetrofitClient.getRetrofitInstance().create(APIInterface.class);
        service.getFeeds().enqueue(new Callback<FactsModel>() {
            @Override
            public void onResponse(@NonNull Call<FactsModel> call, @NonNull Response<FactsModel> response) {
                factsModelMutableLiveData.setValue(removeNullDataFromResponse(response.body()));
            }
            @Override
            public void onFailure(@NonNull Call<FactsModel> call, Throwable t) {
                factsModelMutableLiveData.setValue(null);
            }
        });

        return factsModelMutableLiveData;
    }

    /**
     *
     * @return Fact Model Live data cached List
     */
    public LiveData<FactsModel> getOfflineFacts() {
        return factsModelMutableLiveData;
    }


    /**
     *  Function to remove null data to avoid blank row in recyclerView
     * @param facts Fact model
     * @return null data removed from list
     */
    private FactsModel removeNullDataFromResponse(FactsModel facts) {
        if (facts != null) {
            for (int i = 0; i < facts.getRows().size(); i++) {
                FeedModel feed = facts.getRows().get(i);
                if (feed.getTitle() == null && feed.getDescription() == null && feed.getImageHref() == null) {
                    facts.getRows().remove(feed);
                } else if (feed.getDescription() == null) {
                    feed.setDescription("No Description available");
                }
            }
        }
        return facts;
    }

}
