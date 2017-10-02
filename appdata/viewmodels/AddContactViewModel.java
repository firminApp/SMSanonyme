package com.firminapp.smsanonyme.appdata.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.os.AsyncTask;

import com.firminapp.smsanonyme.appdata.AppDatabase;
import com.firminapp.smsanonyme.appdata.dataModels.ContactModel;

public class AddContactViewModel extends AndroidViewModel {

    private AppDatabase appDatabase;

    public AddContactViewModel(Application application) {
        super(application);

        appDatabase = AppDatabase.getDatabase(this.getApplication());

    }

    public void addContact(final ContactModel ContactModel) {
        new addAsyncTask(appDatabase).execute(ContactModel);
    }

    private static class addAsyncTask extends AsyncTask<ContactModel, Void, Void> {

        private AppDatabase db;

        addAsyncTask(AppDatabase appDatabase) {
            db = appDatabase;
        }

        @Override
        protected Void doInBackground(final ContactModel... params) {
            db.itemAndContactModel().addNew(params[0]);
            return null;
        }

    }
}
