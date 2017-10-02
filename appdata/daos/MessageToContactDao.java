package com.firminapp.smsanonyme.appdata.daos;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.firminapp.smsanonyme.appdata.dataModels.MessageToContactModel;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

/**
 * Created by firmin on 14/09/17.
 */
@Dao
public interface MessageToContactDao {

    @Query("select * from MessageToContactModel")
    LiveData<List<MessageToContactModel>> getAll();

    @Query("select * from MessageToContactModel where id = :id")
    MessageToContactModel getById(String id);

    @Insert(onConflict = REPLACE)
    void addNew(MessageToContactModel model);

    @Delete
    void delete(MessageToContactModel model);
    @Update
    void update(MessageToContactModel model);
}
