
package com.firminapp.smsanonyme;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.widget.LinearLayout;

import com.firminapp.smsanonyme.activities.DashboardActi;
import com.firminapp.smsanonyme.activities.PresentationActi;
import com.firminapp.smsanonyme.appconfig.PrefManager;
import com.firminapp.smsanonyme.appconfig.SmsApp;
import com.github.ybq.android.spinkit.SpinKitView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (PrefManager.getInstance().isNew()){
            startActivity(new Intent(this, PresentationActi.class));
            finish();
        }
        else
        {
           // startActivity(new Intent(this, DashboardActi.class));
            startActivity(new Intent(this, PresentationActi.class));
           startActivity(new Intent(this, DashboardActi.class));
            finish();


        }

    }
}
