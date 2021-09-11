package com.example.quit.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.quit.models.Addiction;
import com.example.quit.repo.AppRepo;

import java.util.List;

public class MainViewModel extends AndroidViewModel {
    private  AppRepo appRepo;
    LiveData<List<Addiction>> listLiveData;

    public MainViewModel(@NonNull Application application) {
        super(application);
        appRepo = AppRepo.getInstance(application);
        listLiveData = appRepo.getAddictions();
    }

    public LiveData<List<Addiction>> getAddictionListLiveData() {
        if (listLiveData == null){
           listLiveData = appRepo.getAddictions();
        }
        return listLiveData;
    }

    public void updateAddiction(Addiction addiction){
        appRepo.updateAddiction(addiction);
    }

    public void deleteAllData(){
        appRepo.deleteAllData();
    }

    public void deleteAddictionById(int addictionId) {
        appRepo.deleteAddictionAndRelapse(addictionId);
    }
}
