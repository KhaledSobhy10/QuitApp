package com.example.quit.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.quit.models.Addiction;
import com.example.quit.models.AddictionWithRelapse;

import java.util.List;

@Dao
public  interface AddictionDao {

    @Query("SELECT * FROM Addiction")
    LiveData<List<Addiction>> getAllAddiction();

    @Insert
    void insertAddictions(Addiction... addiction);

    @Update
    void updateAddiction(Addiction addiction);

    @Query("DELETE FROM Addiction")
    void deleteAllData();

    @Query("SELECT * FROM Addiction WHERE id = :addictionId")
    LiveData<Addiction> getAddictionById(int addictionId);


    @Transaction
     @Query("SELECT * FROM Addiction WHERE id = :addictionId ")
     LiveData<AddictionWithRelapse> getAddictionWithRelapseDataById(int addictionId);

    @Query("DELETE  FROM Addiction WHERE id = :addictionId")
    void deleteAddictionById(int addictionId);



    @Query("SELECT lastDateOfQuit FROM Addiction WHERE id = :id")
    long getAddictionLastDate(int id);




    @Query("UPDATE addiction SET lastDateOfQuit = :lastDatOfQuit ,targetDate = :targetDate ," +
            "targetDateType = :targetDateType WHERE id= :addictionId")
    void updateLastQuitAndTargetDates(int addictionId,long lastDatOfQuit,long targetDate,String targetDateType);

    @Query("DELETE FROM Addiction ")
     void deleteAll();

    @Query("SELECT firstDateOfQuit FROM Addiction WHERE id = :addictionId")
    long getFirstDayOfQuitById(int addictionId);
}
