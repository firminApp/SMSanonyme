package com.firminapp.smsanonyme.appdata;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.firminapp.smsanonyme.appdata.daos.ContactDao;
import com.firminapp.smsanonyme.appdata.daos.MessageToContactDao;
import com.firminapp.smsanonyme.appdata.daos.SmsDao;
import com.firminapp.smsanonyme.appdata.dataModels.ContactModel;
import com.firminapp.smsanonyme.appdata.dataModels.MessageToContactModel;
import com.firminapp.smsanonyme.appdata.dataModels.SmsModel;

/**
 * Created by firmin on 27/08/17.
 */
@Database(entities = {SmsModel.class, ContactModel.class, MessageToContactModel.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE;

    public static AppDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE =
                    Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "anonymesmsb")
                            .build();
        }
        return INSTANCE;
    }

    public abstract SmsDao itemAndSmsModel();
    public abstract ContactDao itemAndContactModel();
    public abstract MessageToContactDao itemAndMtoCModel();

}