package com.firminapp.smsanonyme.appconfig;

import android.Manifest;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.firminapp.smsanonyme.recevers.TokenReceiver;
import com.firminapp.smsanonyme.services.InitApiService;

import java.io.IOException;
import java.util.regex.Pattern;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@SuppressWarnings({"unused", "deprecation"})
public class SmsApp extends Application {
    private String TAG=SmsApp.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();
        initSingletons();

        Log.e("smsapp","Starting the application");
       // startTokenAlarm();
        //AndroidThreeTen.init(this);
    }

    protected void initSingletons() {
        //UtilApplicationClass.initInstance();
        //DbManager.init(this);
        PrefManager.initInstance(getApplicationContext());
        startService(new Intent(getApplicationContext(), InitApiService.class));


    }
    public void alertNoInternet(View view, boolean error) {
        String alertMessage;

        if (!error)
            alertMessage = "Pas de connexion";
        else
            alertMessage = "Impossible de se connecter";
        Snackbar snackbar = Snackbar.make(view, alertMessage, Snackbar.LENGTH_LONG);
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.RED);
        snackbar.show();
    }

    public boolean isOnline() {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            NetworkInfo[] info = connMgr.getAllNetworkInfo();
            if (info != null)
                for (NetworkInfo anInfo : info)
                    if (anInfo.getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
        }
        return (networkInfo != null && networkInfo.isConnected());
    }

    boolean finish = false;
    boolean internet = false;

    public boolean hasActiveInternetConnection() {
        if (isOnline()) {
            OkHttpClient client = new OkHttpClient();
            Request request = makeRequest();
            if(request!=null)
            {
                try {
                   Response response= client.newCall(request).execute();
                    if ((response.code()==200||response.code()==201))
                    {
                        return  true;
                    }
                    else
                        return false;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


        } else {
            Log.d("Check has internet", "No network available!");
            return false;

        }
        return false;
    }
    public Request makeRequest(){
        HttpUrl.Builder urlBuilder = HttpUrl.parse(WebsServices.APISMS).newBuilder();
        urlBuilder.addQueryParameter("app", "ws");
        urlBuilder.addQueryParameter("op", "get_token");
        urlBuilder.addQueryParameter("u", "Kpapou");
        urlBuilder.addQueryParameter("p", "kpapou");
        String url = urlBuilder.build().toString();
        Log.d("refreshing token", "url= "+url);

        return new Request.Builder()
                .url(url)
                .get()
                .build();
    }



    public void startTokenAlarmPASBESOINS() {
      Intent alarmIntent = new Intent(this, TokenReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent, 0);

        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        manager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
  Log.e("smsapp.startalarm","Token alarme just started");
    }

    public void refreshApiToken()
    {

    }

}