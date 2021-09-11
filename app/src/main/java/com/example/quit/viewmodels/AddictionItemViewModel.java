package com.example.quit.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.quit.models.Addiction;
import com.example.quit.models.AddictionWithRelapse;
import com.example.quit.database.AppDatabase;
import com.example.quit.repo.AppRepo;

public class AddictionItemViewModel extends AndroidViewModel {
    private LiveData<AddictionWithRelapse> addictionWithRelapseLiveData;
    private AppRepo appRepo;

    public AddictionItemViewModel(@NonNull Application application) {
        super(application);
        appRepo = AppRepo.getInstance(application);
    }

    public LiveData<AddictionWithRelapse> getAddictionWithRelapseLiveData(int addictionId) {
        if (addictionWithRelapseLiveData == null){
            addictionWithRelapseLiveData = appRepo.addictionWithRelapseById(addictionId);
        }
        return addictionWithRelapseLiveData;
    }
    public LiveData<AddictionWithRelapse> getAddictionWithRelapseLiveData() {

        return addictionWithRelapseLiveData;
    }

    public void updateAddiction(Addiction addiction){
        appRepo.updateAddiction(addiction);
    }
}
