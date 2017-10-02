package com.firminapp.smsanonyme.activities;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.firminapp.smsanonyme.R;
import com.firminapp.smsanonyme.appconfig.PrefManager;
import com.firminapp.smsanonyme.appconfig.WebsServices;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.BufferedSink;

public class SignUpActi extends AppCompatActivity implements View.OnClickListener {
    private EditText tvpseudo, tvtel,tvmail;
    private FloatingActionButton flsigup;
    private ProgressBar progressBar;
    private View rootlayout;
    private String TAG=SignUpActi.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        progressBar=(ProgressBar)findViewById(R.id.pb_loader) ;
        rootlayout=findViewById(R.id.rootlayout);
        tvmail=(EditText)findViewById(R.id.sigup_tvmail);
        tvpseudo=(EditText)findViewById(R.id.signup_tvpseudo);
        tvtel=(EditText)findViewById(R.id.signup_tvtel);
        flsigup=(FloatingActionButton)findViewById(R.id.flsigup);
        flsigup.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.flsigup:
                signup();
                break;
        }
    }
    public void signup(){
        String pseudo="Anonyme";
        String mail="inconnu";
        String tel="";
        if(TextUtils.isEmpty(tvpseudo.getText().toString()))
        {
            pseudo="Anonyme";
        }
        else if(TextUtils.isEmpty(tvmail.getText().toString()))
        {
            mail="inconnu";
        }
        else
        {
            pseudo=tvpseudo.getText().toString().trim();
            tel=tvtel.getText().toString().trim();
            mail=tvmail.getText().toString().trim();
        }
        if(TextUtils.isEmpty(tvtel.getText().toString())|tvtel.getText().toString().trim().length()<8)
        {
            tvtel.setError(getString(R.string.inccorect_tel_error));
        }else
        {
            PrefManager.getInstance().setPseudo(pseudo);
            PrefManager.getInstance().setTelephone(tel);
            PrefManager.getInstance().setTotalsms(0);
            PrefManager.getInstance().setSendedsms(0);
            PrefManager.getInstance().setIsNew(false);
            send(pseudo,"",mail,tel);



        }


    }
    private void send(String firstname, String lastname, String email, String tel){
        Log.d("Send", "Preparing to  signup");
        swichViews(View.VISIBLE,View.GONE,View.GONE);
        OkHttpClient client = new OkHttpClient();
        final Request request = makeRequest(firstname,lastname,email,tel);

        if (request != null){
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.d("sendsms", "Dans onfaillur... il y a eu un probleme "+e.getMessage());
                    Snackbar.make(rootlayout,"Problème de connexion!",Snackbar.LENGTH_LONG).show();
                    //succefullySended();
                    swichViews(View.GONE,View.VISIBLE,View.VISIBLE);


                    e.printStackTrace();
                }

                @Override
                public void onResponse(Call call, final Response response) throws IOException {
                    if (response != null){
                        Log.d("signup", "reponse de l'api= "+response.toString());
                        if (response.isSuccessful()){
                            try {
                                String responseData = response.body().string();
                                Log.d("signup", " "+responseData);
                                JSONObject json = new JSONObject(responseData);

                                if (json.has("statut")){

                                    String code=json.has("code")?json.getString("code"):null;
                                    PrefManager.getInstance().setVerifCode(code);
                                    Snackbar.make(rootlayout,"Inscription effectuée avec succès",Snackbar.LENGTH_LONG).show();
                                    // succefullySended();
                                    swichViews(View.GONE,View.VISIBLE,View.VISIBLE);
                                    Intent i=new Intent(SignUpActi.this,SmsActi.class);
                                    i.putExtra(DashboardActi.KEY_FRAG_EXTRA,"recharger");
                                    startActivity(i);
                                    finish();

                                } else{
                                    Log.d(TAG, "Mauvais Json");
                                    swichViews(View.GONE,View.VISIBLE,View.VISIBLE);
                                    Snackbar.make(rootlayout,"Il y a eu une erreur inatendu! Réponse serveur incohérrente. Réessayez svp.",Snackbar.LENGTH_LONG).show();

                                }
                            } catch (JSONException e) {
                                swichViews(View.GONE,View.VISIBLE,View.VISIBLE);
                                Log.d(TAG, "Problème service"+e.getMessage());
                                Snackbar.make(rootlayout,"Il y a eu une erreur! réponse serveur inatendu! Réessayez svp. ",Snackbar.LENGTH_LONG).show();
                            }
                        } else{
                            swichViews(View.GONE,View.VISIBLE,View.VISIBLE);
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
    public void swichViews(final int pbstatut, final int flboutonstatut, final int rootlayoutstatut){
       runOnUiThread(new Runnable() {
            @Override
            public void run() {

                rootlayout.setVisibility(rootlayoutstatut);
                progressBar.setVisibility(pbstatut);
               flsigup.setVisibility(flboutonstatut);
                 /*ibcontact.setVisibility(flboutonstatut);
                spv.setVisibility(pbstatut);*/

            }
        });
    }
    public Request makeRequest(String firstname, String lastname, String email, String tel){
        RequestBody formBody = new FormBody.Builder()
                .add("firstname",firstname)
                .add("lastname", lastname)
                .add("email", email)
                .add("tel", tel)
               .build();
        String url =WebsServices.SIGNUP;
        Log.d("signup", "url= "+url);
        return new Request.Builder()
                .url(url)
                .post(formBody)
                .addHeader("content-type", "form-data")
                .build();
    }
}
