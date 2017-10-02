package com.firminapp.smsanonyme.appdata.daos;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.firminapp.smsanonyme.appdata.dataModels.ContactModel;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

/**
 * Created by firmin on 14/09/17.
 */
@Dao
public interface ContactDao {

    @Query("select * from ContactModel")
    LiveData<List<ContactModel>> getAll();

    @Query("select * from ContactModel where id = :id")
    ContactModel getById(String id);

    @Insert(onConflict = REPLACE)
    void addNew(ContactModel model);

    @Delete
    void delete(ContactModel model);
    @Update
    void update(ContactModel model);
}
