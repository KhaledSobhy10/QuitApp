package com.example.quit.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.example.quit.models.Relapse;
import com.example.quit.repo.AppRepo;

public class EntryViewModel extends AndroidViewModel {
    private final AppRepo appRepo;
    private LiveData<Relapse> relapseLiveData;

    public EntryViewModel(@NonNull Application application) {
        super(application);
        appRepo = AppRepo.getInstance(application);
    }

    public LiveData<Relapse> getRelapseToEditByDate(long date) {
        if (relapseLiveData == null)
            relapseLiveData = appRepo.getRelapseByDate(date);
        return relapseLiveData;
    }

    public void updateRelapseDateAndComment(long newDate, String newComment) {
        Observer<Relapse> relapseObserver = new Observer<Relapse>() {
            @Override
            public void onChanged(Relapse relapse) {
                relapse.setComment(newComment);
                relapse.setRelapseDate(newDate);
                appRepo.updateRelapse(relapse);

                relapseLiveData.removeObserver(this);
            }
        };
        relapseLiveData.observeForever(relapseObserver);
    }

    public void insertEntry(Relapse relapse){
        if (relapse != null)
        appRepo.insertRelapse(relapse);
    }

}
