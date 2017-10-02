package com.firminapp.smsanonyme.appdata.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.os.AsyncTask;

import com.firminapp.smsanonyme.appdata.AppDatabase;
import com.firminapp.smsanonyme.appdata.dataModels.SmsModel;

public class AddSmsViewModel extends AndroidViewModel {

    private AppDatabase appDatabase;

    public AddSmsViewModel(Application application) {
        super(application);

        appDatabase = AppDatabase.getDatabase(this.getApplication());

    }

    public void addSms(final SmsModel smsModel) {
        new addAsyncTask(appDatabase).execute(smsModel);
    }

    private static class addAsyncTask extends AsyncTask<SmsModel, Void, Void> {

        private AppDatabase db;

        addAsyncTask(AppDatabase appDatabase) {
            db = appDatabase;
        }

        @Override
        protected Void doInBackground(final SmsModel... params) {
            db.itemAndSmsModel().addNew(params[0]);
            return null;
        }

    }
}
