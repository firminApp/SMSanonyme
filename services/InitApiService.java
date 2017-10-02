package com.firminapp.smsanonyme.services;

import android.app.IntentService;
import android.content.Intent;

import com.firminapp.smsanonyme.processes.InitApiparams;

public class InitApiService extends IntentService {

    public InitApiService() {
        super("InitApiService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            new InitApiparams();

        }
    }
}
