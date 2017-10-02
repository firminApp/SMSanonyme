package com.firminapp.smsanonyme.appdata.daos;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.firminapp.smsanonyme.appdata.dataModels.SmsModel;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

/**
 * Created by firmin on 14/09/17.
 */
@Dao
public interface SmsDao {

    @Query("select * from SmsModel")
    LiveData<List<SmsModel>> getAll();

    @Query("select * from SmsModel where id = :id")
    SmsModel getById(String id);

    @Insert(onConflict = REPLACE)
    void addNew(SmsModel model);

    @Delete
    void delete(SmsModel model);
    @Update
    void update(SmsModel model);
}
