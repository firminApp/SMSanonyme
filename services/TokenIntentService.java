package com.firminapp.smsanonyme.services;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.firminapp.smsanonyme.appconfig.PrefManager;
import com.firminapp.smsanonyme.appconfig.WebsServices;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class TokenIntentService extends IntentService {

    private final String TAG = this.getClass().getSimpleName();
    private String code;

    public TokenIntentService() {
        super("TokenIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            Log.d(TAG, "token intent service will start ");
            rafraichirToken();
        }
        else
            Log.d("refreshing token", "intent is null");
    }

    private void rafraichirToken(){
        Log.d("refreshing token", "token en cour de rafraichissement");
        OkHttpClient client = new OkHttpClient();
        Request request = makeRequest();

        if (request != null){
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.d("refreshing token", "Dans onfaillur... il y a eu un probleme");
                    e.printStackTrace();
                }

                @Override
                public void onResponse(Call call, final Response response) throws IOException {
                    if (response != null){
                        Log.d("refreshing token", "reponse de l'api= "+response.toString());
                        if (response.code() == 200){
                            try {
                                String responseData = response.body().string();
                                JSONObject json = new JSONObject(responseData);

                                if (json.has("token")){

                                    final String accesToken = json.getString("token");
                                    Log.e("tokenrecu",accesToken+"");
                                    PrefManager.getInstance().setApiToken(accesToken);
                                } else{
                                    Log.d(TAG, "Mauvais Json");
                                }
                            } catch (JSONException e) {
                                Log.d(TAG, "Problème service");
                            }
                        } else{
                            Log.e(TAG, "Autre Problème access_token = "+response.toString());
                            //SessionManager.getInstance().setIsRefresh(false);
                        }
                    }
                }
            });
        }
        else
        Log.e(TAG,"Rewest est null");
    }
    public Request makeRequest(){
        HttpUrl.Builder urlBuilder = HttpUrl.parse(WebsServices.APISMS).newBuilder();
        PrefManager pm=PrefManager.getInstance();
        urlBuilder.addQueryParameter(pm.getApiappkey(), pm.getApiapp());
        urlBuilder.addQueryParameter(pm.getApiopkey(), "get_token");
        urlBuilder.addQueryParameter(pm.getApiuserkey(), pm.getApiUser());
        urlBuilder.addQueryParameter(pm.getApipasskey(), pm.getApiPass());
        String url = urlBuilder.build().toString();
        Log.d("refreshing token", "url= "+url);
        return new Request.Builder()
                .url(url)
                .get()
                .build();
    }
}
