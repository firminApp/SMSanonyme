package com.firminapp.smsanonyme.processes;

import android.util.Log;

import com.firminapp.smsanonyme.appconfig.PrefManager;
import com.firminapp.smsanonyme.appconfig.WebsServices;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by firmin on 18/09/17.
 */

public class InitApiparams {
    private String TAG=InitApiparams.class.getSimpleName();

    public InitApiparams(){
        Log.e(TAG, "Init params en cour...");
        init();

    }
    public void init() {
        OkHttpClient client = new OkHttpClient();
        Request request = makeRequest();
        if (request != null) {
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.e("proble", "probleme de connxion echec initiliation "+e.getMessage());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    {
                        if (response != null) {
                            Log.e(TAG, response.toString()+"");
                            if ( response.isSuccessful()) {
                                try {
                                    String responseData = response.body().string();
                                    Log.e(TAG, responseData+"");
                                    JSONObject json = new JSONObject(responseData);

                                    PrefManager mg=PrefManager.getInstance();
                                    mg.setApiUser(json.has(WebsServices.JSON_KEY_API_USER)?json.getString(WebsServices.JSON_KEY_API_USER):"");
                                    mg.setApiPass(json.has(WebsServices.JSON_KEY_API_PASS)?json.getString(WebsServices.JSON_KEY_API_PASS):"");
                                    mg.setApiToken(json.has(WebsServices.JSON_KEY_API_TOKEN)?json.getString(WebsServices.JSON_KEY_API_TOKEN):"");
                                    mg.setApifrom(json.has(WebsServices.JSON_KEY_API_FROM)?json.getString(WebsServices.JSON_KEY_API_FROM):"");
                                    mg.setApito(json.has(WebsServices.JSON_KEY_API_TO)?json.getString(WebsServices.JSON_KEY_API_TO):"");
                                    mg.setApibody(json.has(WebsServices.JSON_KEY_API_BODY)?json.getString(WebsServices.JSON_KEY_API_BODY):"");
                                    mg.setApiuserkey(json.has(WebsServices.JSON_KEY_API_USER_KEY)?json.getString(WebsServices.JSON_KEY_API_USER_KEY):"");
                                    mg.setApipasskey(json.has(WebsServices.JSON_KEY_API_PASS_KEY)?json.getString(WebsServices.JSON_KEY_API_PASS_KEY):"");
                                    mg.setApitokenkey(json.has(WebsServices.JSON_KEY_API_TOKEN_KEY)?json.getString(WebsServices.JSON_KEY_API_TOKEN_KEY):"");
                                    mg.setApifromkey(json.has(WebsServices.JSON_KEY_API_FROM_KEY)?json.getString(WebsServices.JSON_KEY_API_FROM_KEY):"");
                                    mg.setApitokey(json.has(WebsServices.JSON_KEY_API_TO_KEY)?json.getString(WebsServices.JSON_KEY_API_TO_KEY):"");
                                    mg.setApibodykey(json.has(WebsServices.JSON_KEY_API_BODY_KEY)?json.getString(WebsServices.JSON_KEY_API_BODY_KEY):"");
                                    mg.setApiApp(json.has(WebsServices.JSON_KEY_API_APP)?json.getString(WebsServices.JSON_KEY_API_APP):"");
                                    mg.setApiOp(json.has(WebsServices.JSON_KEY_API_OP)?json.getString(WebsServices.JSON_KEY_API_OP):"");
                                    mg.setApiAppKey(json.has(WebsServices.JSON_KEY_API_APP_KEY)?json.getString(WebsServices.JSON_KEY_API_APP_KEY):"");
                                    mg.setApiOpKey(json.has(WebsServices.JSON_KEY_API_OP_KEY)?json.getString(WebsServices.JSON_KEY_API_OP_KEY):"");
                                    Log.e(TAG, "Init params terminé");
                                } catch (JSONException e) {
                                    Log.d(TAG, "Problème service");
                                }
                            } else {
                                Log.e(TAG, "Autre Problème access_token = " + response.toString());
                                //SessionManager.getInstance().setIsRefresh(false);
                            }
                        }


                    }

                }

            });
        }
    }

        public Request makeRequest(){
            HttpUrl.Builder urlBuilder = HttpUrl.parse(WebsServices.INIT_API_PARAMS).newBuilder();
            String url = urlBuilder.build().toString();
            Log.d("geting api params", "url= "+url);
            return new Request.Builder()
                    .url(url)
                    .get()
                    .build();


    }
}
