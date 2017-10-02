package com.firminapp.smsanonyme.services;

import android.app.IntentService;
import android.content.Intent;
import android.icu.text.UnicodeSetSpanner;
import android.util.Log;
import android.widget.Toast;

import com.firminapp.smsanonyme.activities.DashboardActi;
import com.firminapp.smsanonyme.appconfig.PrefManager;
import com.firminapp.smsanonyme.appconfig.WebsServices;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions and extra parameters.
 */
public class VerificationService extends IntentService {
    private String TAG=VerificationService.class.getSimpleName();

    public VerificationService() {
        super("VerificationService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            Log.d(TAG, "token intent service will start ");
            String code=intent.getStringExtra("CODE");
            verify(code);
        }
        else
            Log.d("refreshing token", "intent is null");
    }

    private void verify(String code){
        Log.d("code", "start checking code "+code);
        OkHttpClient client = new OkHttpClient();
        Request request = makeRequest(code);

        if (request != null){
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.d("verification", "Dans onfaillur... il y a eu un probleme");
                    e.printStackTrace();
                }

                @Override
                public void onResponse(Call call, final Response response) throws IOException {
                    if (response != null){
                        Log.d("code", "reponse server= "+response.toString());
                        if (response.code() == 200){
                            try {
                                String responseData = response.body().string();
                                JSONObject json = new JSONObject(responseData);

                                if (json.has("statut")){
                                    if(json.getString("statut").equals("succes"))
                                    {
                                        startActivity(new Intent(VerificationService.this, DashboardActi.class));
                                    }
                                    else {
                                        Toast.makeText(VerificationService.this,"Code erronné!",Toast.LENGTH_LONG).show();
                                    }

                                } else{
                                    Log.d(TAG, "Mauvais Json");
                                }
                            } catch (JSONException e) {
                                Log.d(TAG, "Problème service");
                            }
                        } else{
                            Log.e(TAG, "verification response = "+response.toString());
                            //SessionManager.getInstance().setIsRefresh(false);
                        }
                    }
                }
            });
        }
        else
            Log.e(TAG,"Rewest est null");
    }
    public Request makeRequest(String code){
        PrefManager pm=PrefManager.getInstance();
        HttpUrl.Builder urlBuilder = HttpUrl.parse(WebsServices.URL_CODE_VERIFICATION).newBuilder();
        RequestBody body=new FormBody.Builder().add("tel",pm.getTelephone())
                                                .add("code",code).build();
        String url = urlBuilder.build().toString();
        Log.d("vérification", "url= "+url);
        return new Request.Builder()
                .url(url)
                .post(body)
                .addHeader("content-type", "application/x-www-form-urlencoded")
                .build();
    }

}
