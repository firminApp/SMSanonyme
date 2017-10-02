package com.firminapp.smsanonyme.appdata.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.firminapp.smsanonyme.appdata.AppDatabase;
import com.firminapp.smsanonyme.appdata.dataModels.SmsModel;

import java.util.List;

/**
 * Created by firmin on 14/09/17.
 */

public class SmsViewModel extends  AndroidViewModel{
    

        private final LiveData<List<SmsModel>> itemAndsmslist;

        private AppDatabase appDatabase;

        public SmsViewModel(Application application) {
            super(application);

            appDatabase = AppDatabase.getDatabase(this.getApplication());

            itemAndsmslist = appDatabase.itemAndSmsModel().getAll();
        }


        public LiveData<List<SmsModel>> getItemSmsList() {
            return itemAndsmslist;
        }

        public void delete(SmsModel SmsModel) {
            new com.firminapp.smsanonyme.appdata.viewmodels.SmsViewModel.deleteAsyncTask(appDatabase).execute(SmsModel);
        }

        private static class deleteAsyncTask extends AsyncTask<SmsModel, Void, Void> {

            private AppDatabase db;

            deleteAsyncTask(AppDatabase appDatabase) {
                db = appDatabase;
            }

            @Override
            protected Void doInBackground(final SmsModel... params) {
                db.itemAndSmsModel().delete(params[0]);
                return null;
            }

        }
    
}
