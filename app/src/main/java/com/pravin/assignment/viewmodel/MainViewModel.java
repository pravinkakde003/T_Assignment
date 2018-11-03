package com.pravin.assignment.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.pravin.assignment.service.model.FactsModel;
import com.pravin.assignment.service.service.FactsRepository;


public class MainViewModel extends AndroidViewModel {

    public MainViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<FactsModel> getFacts(){
        return FactsRepository.getInstance().getFacts();
    }

    public LiveData<FactsModel> getOfflineFacts(){
        return FactsRepository.getInstance().getOfflineFacts();
    }
}
