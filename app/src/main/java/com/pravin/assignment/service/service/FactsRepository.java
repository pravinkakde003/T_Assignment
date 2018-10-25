package com.pravin.assignment.service.service;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.gson.Gson;
import com.pravin.assignment.service.model.FactsModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/*
Repository to handle the network call to get facts feed
 */
public class FactsRepository {

    private static FactsRepository Instance = null;

    public static FactsRepository getInstance() {
        if (Instance == null)
            Instance = new FactsRepository();
        return Instance;
    }

    // Initiate the call to get facts
    public LiveData<FactsModel> getFacts() {
        final MutableLiveData<FactsModel> factsModelMutableLiveData = new MutableLiveData<>();
        final APIInterface service = RetrofitClient.getRetrofitInstance().create(APIInterface.class);
        service.getFeeds().enqueue(new Callback<FactsModel>() {
            @Override
            public void onResponse(@NonNull Call<FactsModel> call, @NonNull Response<FactsModel> response) {
                Log.e("onResponse",new Gson().toJson(response.body()));
                factsModelMutableLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<FactsModel> call, Throwable t) {
                factsModelMutableLiveData.setValue(null);
                Log.e("onFailure",t.getLocalizedMessage().toString());
            }
        });

        return factsModelMutableLiveData;
    }

}
