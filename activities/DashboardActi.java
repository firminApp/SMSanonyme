package com.firminapp.smsanonyme.activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.firminapp.smsanonyme.R;
import com.firminapp.smsanonyme.appconfig.PrefManager;
import com.firminapp.smsanonyme.appconfig.SmsApp;

import static android.text.Html.fromHtml;

public class DashboardActi extends AppCompatActivity implements View.OnClickListener{
    private TextView tvpseud, tvtel,tvsmssended,tvsmsrest,tvsendsms,tvhisto,tvcarnet,tvrecharger;
    public static final String KEY_FRAG_EXTRA="com.firminapp.smsanonyme.DashboardActi.KEYFRAG_EXTRA";
    private Toolbar toolbar;
    public static  String police_ABRIFATFACE= "fonts/abrilfatface.otf";

    public static  String police_BEBAS= "fonts/bebas.ttf";

    public static  String police_BLACKJACK= "fonts/blackjack.otf";

    public static  String police_CAPTURE= "fonts/capture.ttf";

    public static  String police_CHUNKFIVE= "fonts/chunkfive.otf";
    public static String police_FFF_TUSJ= "fonts/fff_tusj.ttf";
    public static String police_SEASRN= "fonts/seasrn.ttf";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getString(R.string.app_name));
        getSupportActionBar().setSubtitle(getString(R.string.tool_title_dashboard));
        //SmsApp app=(SmsApp)getApplicationContext();
       // app.startTokenAlarm();


        tvpseud=(TextView)findViewById(R.id.tvpseudo);
        tvtel=(TextView)findViewById(R.id.tvtelephone);
        tvsmssended=(TextView)findViewById(R.id.tvsmsenvoyer);
        tvsmsrest=(TextView)findViewById(R.id.tvsmsrestant);

        tvsendsms=(TextView)findViewById(R.id.tvenvoyersms);
        tvhisto=(TextView)findViewById(R.id.tvhistoriquesms);
        tvcarnet=(TextView)findViewById(R.id.tvcarnetdadress);
        tvrecharger=(TextView)findViewById(R.id.tvrecharge);
        tvsendsms.setOnClickListener(this);
        tvhisto.setOnClickListener(this);
        tvcarnet.setOnClickListener(this);
        tvrecharger.setOnClickListener(this);


        tvpseud.setText(fromHtml(tvpseud.getText().toString()+": <b>"+PrefManager.getInstance().getPseudo()+"</b>"));
        tvtel.setText(fromHtml(tvtel.getText().toString()+": <b>"+PrefManager.getInstance().getTelephone()+"</b>"));
        tvsmssended.setText(fromHtml(tvsmssended.getText().toString()+": <b>"+PrefManager.getInstance().getKeySendedSms()+"</b>"));
        tvsmsrest.setText(fromHtml(tvsmsrest.getText().toString()+": <b>"+
                (PrefManager.getInstance().getTotalsms()-PrefManager.getInstance().getKeySendedSms())+"</b>"));
        setFont(tvpseud,police_ABRIFATFACE);
        setFont(tvtel,police_ABRIFATFACE);
        setFont(tvsmsrest,police_ABRIFATFACE);
        setFont(tvsmssended,police_ABRIFATFACE);
        setFont(tvhisto,police_ABRIFATFACE);
        setFont(tvcarnet,police_ABRIFATFACE);
        setFont(tvrecharger,police_ABRIFATFACE);
        setFont(tvsendsms,police_ABRIFATFACE);

    }
    public  void setFont(TextView textView, String fontName) {
        if(fontName != null){
            try {
                Typeface typeface = Typeface.createFromAsset(getAssets(),  fontName);
                textView.setTypeface(typeface);

            } catch (Exception e) {
                Log.e("FONT", fontName + " not found", e);
            }
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(this, ConfActi.class));
            return true;
        }
        if (id == R.id.action_infos) {
            startActivity(new Intent(this, AproposActi.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        int id=view.getId();
        switch (id)
        {
            case R.id.tvenvoyersms:
                Intent i=new Intent(this, SmsActi.class);
                i.putExtra(KEY_FRAG_EXTRA,"sendsms");
                startActivity(i);

                break;
            case R.id.tvhistoriquesms:
                Intent i2=new Intent(this, SmsActi.class);
                i2.putExtra(KEY_FRAG_EXTRA,"histo");
                startActivity(i2);
                break;
            case R.id.tvcarnetdadress:
                Intent i3=new Intent(this, SmsActi.class);
                i3.putExtra(KEY_FRAG_EXTRA,"carnet");
                startActivity(i3);
                break;
            case R.id.tvrecharge:

                Intent i4=new Intent(this, SmsActi.class);
                i4.putExtra(KEY_FRAG_EXTRA,"recharger");
                startActivity(i4);

                break;
        }

    }

}
