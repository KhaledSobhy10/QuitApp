package com.example.quit.repo;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.quit.background.AppExecutor;
import com.example.quit.dao.AddictionDao;
import com.example.quit.dao.RelapseDao;
import com.example.quit.database.AppDatabase;
import com.example.quit.models.Addiction;
import com.example.quit.models.AddictionWithRelapse;
import com.example.quit.models.Relapse;
import com.example.quit.models.TargetDateType;

import java.util.List;

public class AppRepo {
    private static AppRepo appRepo;
    private AddictionDao addictionDao;
    private RelapseDao relapseDao;

    public static AppRepo getInstance(Application application) {
        if (appRepo == null) {
            synchronized (AppRepo.class) {
                if (appRepo == null) {
                    appRepo = new AppRepo();
                    AppDatabase appDatabase = AppDatabase.getInstance(application);
                    appRepo.addictionDao = appDatabase.getAddictionDao();
                    appRepo.relapseDao = appDatabase.getRelapseDao();
                }
            }
        }
        return appRepo;
    }

    public void insertAddiction(Addiction... addictions) {
        AppExecutor.getAppExecutor().getDiskIOExecutor().execute(() -> {
            addictionDao.insertAddictions(addictions);
        });
    }

    public void insertRelapse(Relapse relapse) {
        AppExecutor.getAppExecutor().getDiskIOExecutor().execute(() -> {

            relapseDao.insertRelapse(relapse);
            updateAddictionIfNeeded(relapse);

        });
    }

    public void updateAddiction(Addiction addiction) {
        AppExecutor.getAppExecutor().getDiskIOExecutor().execute(() -> {
            addictionDao.updateAddiction(addiction);
        });
    }

    public LiveData<List<Addiction>> getAddictions() {
        return addictionDao.getAllAddiction();
    }

    public LiveData<AddictionWithRelapse> addictionWithRelapseById(int id) {
        return addictionDao.getAddictionWithRelapseDataById(id);
    }

    public void deleteAllData() {
        AppExecutor.getAppExecutor().getDiskIOExecutor().execute(() -> {
            addictionDao.deleteAll();
            relapseDao.deleteAll();
        });
    }

    public void deleteAddictionAndRelapse(int addictionId) {
        AppExecutor.getAppExecutor().getDiskIOExecutor().execute(() -> {
            addictionDao.deleteAddictionById(addictionId);
            relapseDao.deleteRelapseByAddictionId(addictionId);
        });
    }

    public void deleteRelapse(Relapse relapse) {
        AppExecutor.getAppExecutor().getDiskIOExecutor().execute(() -> {
            /*
            if that relapse date = CurrentRelapse then
               update addiction to relapse time before that
               and set target date and  1 -relapse time

            * */
            relapseDao.deleteRelapse(relapse);

            long lastDate = addictionDao.getAddictionLastDate(relapse.getAddictionId());

            if (relapse.getRelapseDate() == lastDate) {
                long newsRelapseDate = relapseDao.getNewestRelapseDateByAddictionId(relapse.getAddictionId());
                if (newsRelapseDate == 0) {
                    newsRelapseDate = addictionDao.getFirstDayOfQuitById(relapse.getAddictionId());
                }
                TargetDateType targetDateType = TargetDateType.TD_24HOUR;
                addictionDao.updateLastQuitAndTargetDates(relapse.getAddictionId(), newsRelapseDate, targetDateType.getTargetDateFromThatTime(newsRelapseDate)
                        , targetDateType.toString());
            }

        });
    }


    public void updateRelapse(Relapse relapse) {
        AppExecutor.getAppExecutor().getDiskIOExecutor().execute(() -> {
            relapseDao.update(relapse);
            updateAddictionIfNeeded(relapse);
        });

    }


    private void updateAddictionIfNeeded(Relapse relapse) {
        /*
         *
         *  if relapse time > lastQuitDate
         * -update last date to that relapse time
         * -update target date and type
         * */

        long newestDate = relapseDao.getNewestRelapseDateByAddictionId(relapse.getAddictionId());
        long lastDate = addictionDao.getAddictionLastDate(relapse.getAddictionId());

        if (relapse.getRelapseDate() > lastDate || newestDate < lastDate) {
            TargetDateType targetDateType = TargetDateType.TD_24HOUR;
            long newTargetDate = targetDateType.getTargetDateFromThatTime(relapse.getRelapseDate());
            addictionDao.updateLastQuitAndTargetDates(relapse.getAddictionId(), relapse.getRelapseDate(), newTargetDate, targetDateType.toString());
            Log.d("test", "insertRelapse: target date type" + targetDateType.toString());
        }
    }

    public LiveData<Relapse> getRelapseByDate(long date) {
        return relapseDao.getRelapseByDate(date);
    }
}
