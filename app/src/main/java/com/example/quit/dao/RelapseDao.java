package com.example.quit.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.quit.models.Relapse;

@Dao
public interface RelapseDao {
    @Insert
    void insertRelapse(Relapse... relapses);

    @Query("DELETE FROM RELAPSE ")
    void  deleteAll();

    @Query("DELETE FROM relapse WHERE addictionId =:addictionId")
    void deleteRelapseByAddictionId(int addictionId);

    @Delete
    void deleteRelapse(Relapse relapse);

    @Query("SELECT relapseDate FROM Relapse WHERE addictionId = :addictionId ORDER BY  relapseDate DESC LIMIT 1")
    long getNewestRelapseDateByAddictionId(int addictionId);

    @Update
    void update(Relapse relapse);

    @Query("SELECT * FROM relapse WHERE relapseDate = :date")
    LiveData<Relapse> getRelapseByDate(long date);
}
