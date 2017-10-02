package com.firminapp.smsanonyme.appdata.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.util.Log;

import com.firminapp.smsanonyme.appdata.AppDatabase;
import com.firminapp.smsanonyme.appdata.dataModels.ContactModel;

import java.util.List;

/**
 * Created by firmin on 14/09/17.
 */

public class ContactViewModel extends AndroidViewModel {
    
    private final LiveData<List<ContactModel>> itemAndsmslist;

    private AppDatabase appDatabase;

    public ContactViewModel(Application application) {
        super(application);

        appDatabase = AppDatabase.getDatabase(this.getApplication());

        itemAndsmslist = appDatabase.itemAndContactModel().getAll();
    }


    public LiveData<List<ContactModel>> getItemAndContactList() {
        return itemAndsmslist;
    }

    public void delete(ContactModel ContactModel) {
        new deleteAsyncTask(appDatabase).execute(ContactModel);
    }

    private static class deleteAsyncTask extends AsyncTask<ContactModel, Void, Void> {

        private AppDatabase db;

        deleteAsyncTask(AppDatabase appDatabase) {
            db = appDatabase;
        }

        @Override
        protected Void doInBackground(final ContactModel... params) {
            ContactModel contact=params[0];
            db.itemAndContactModel().delete(contact);
            return null;
        }


    }

}
