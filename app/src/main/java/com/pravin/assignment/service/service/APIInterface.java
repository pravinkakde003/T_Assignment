package com.pravin.assignment.service.service;

import com.pravin.assignment.service.model.FactsModel;
import com.pravin.assignment.utils.AppConfig;

import retrofit2.Call;
import retrofit2.http.GET;

/*
Service interface for Facts entities
 */
public interface APIInterface {

    // Retrieves the facts feed
    @GET(AppConfig.GET_FEEDS)
    Call<FactsModel> getFeeds();
}
