package com.firminapp.smsanonyme.recevers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.firminapp.smsanonyme.appconfig.SmsApp;
import com.firminapp.smsanonyme.services.TokenIntentService;

public class TokenReceiver extends BroadcastReceiver {
    public TokenReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("tokenrecever", "dans onreceve");
        SmsApp owoMyApp = (SmsApp) context.getApplicationContext();
        if (owoMyApp.isOnline()) {
            Intent intentRefresh = new Intent(context, TokenIntentService.class);
            context.startService(intentRefresh);
        }
        else{
            Log.e("tokenrecever", "non connecté a internet");
        }
    }
}
