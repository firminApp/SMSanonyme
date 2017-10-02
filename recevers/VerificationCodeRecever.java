package com.firminapp.smsanonyme.recevers;

import android.arch.lifecycle.LiveData;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import com.firminapp.smsanonyme.activities.DashboardActi;
import com.firminapp.smsanonyme.appconfig.PrefManager;
import com.firminapp.smsanonyme.appdata.AppDatabase;
import com.firminapp.smsanonyme.appdata.dataModels.SmsModel;
import com.firminapp.smsanonyme.services.VerificationService;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class VerificationCodeRecever extends BroadcastReceiver {


    private   AppDatabase app;
    @Override
    public void onReceive(Context context, Intent intent) {

        app= AppDatabase.getDatabase(context.getApplicationContext());
        JSONObject josms=null;
        String code="";
        Bundle intentExtras = intent.getExtras();
        if (intentExtras != null) {
            /* Get Messages */
            Object[] sms = (Object[]) intentExtras.get("pdus");
            if(sms!=null)

                for (int i = 0; i < sms.length; ++i) {
                /* Parse Each Message */
                    SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) sms[i]);

                    String phone = smsMessage.getOriginatingAddress();
                    if (phone.contains("Maxi sms"))
                    {
                        String message = smsMessage.getMessageBody().toString();
                        Log.e("message",message);
                        String []plite=message.split("est votre");
                        code=plite[0].trim();
                        Log.e("befor tim",code+" longeur "+code.length());

                        Log.e("code aftertrim",code+" longeur "+code.length());
                        //verification du code envoyé par sms
                        if(code.equals(PrefManager.getInstance().getVerifCode()))
                        {

                            Intent intentRefresh = new Intent(context, DashboardActi.class);
                            intentRefresh.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
                            //intentRefresh.putExtra("CODE",code);
                            context.startActivity(intentRefresh);
                        }
                        else
                        {
                            Toast.makeText(context,"Code incorrect!", Toast.LENGTH_LONG).show();
                        }

                    }

                }
        }
    }

}
