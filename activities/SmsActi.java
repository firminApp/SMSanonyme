package com.firminapp.smsanonyme.activities;

import android.content.res.Configuration;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.firminapp.smsanonyme.R;
import com.firminapp.smsanonyme.appdata.dataModels.ContactModel;
import com.firminapp.smsanonyme.fragments.smsactifrags.CarnetDadressFrag;
import com.firminapp.smsanonyme.fragments.smsactifrags.HistoriqueFrag;
import com.firminapp.smsanonyme.fragments.smsactifrags.RechargeFrag;
import com.firminapp.smsanonyme.fragments.smsactifrags.SendSmsFrag;

import java.util.ArrayList;

public class SmsActi extends AppCompatActivity implements SendSmsFrag.OnListFragmentInteractionListener,
HistoriqueFrag.OnFragmentInteractionListener,
CarnetDadressFrag.OnFragmentInteractionListener{

    public static String KEY_CURRENT_FRAGMENT = "com.hesystems_group.second_app.android.owomy.activities.CURRENT_FRAGMENT";
    private SmsActiFragment currentFragment = SmsActiFragment.SENDSMSFRAG;
    //private DrawerLayout mDrawer;
    private Toolbar toolbar;
    //public static Partenaire rootpartener;
    private String intentextarvalue="";
    public static boolean contactCanBeSelectable=false;
    // private NavigationView nvDrawer;
    // private ActionBarDrawerToggle drawerToggle;
    public static LinearLayout linearLayout;
    public static  ArrayList<ContactModel>selectedContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);
        selectedContact=new ArrayList<>();
        // Set a Toolbar to replace the ActionBar.
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        intentextarvalue=getIntent().getStringExtra(DashboardActi.KEY_FRAG_EXTRA);
//        Log.e("intentextra", intentextarvalue);
        if (savedInstanceState != null)
            currentFragment = (SmsActiFragment) savedInstanceState.getSerializable(KEY_CURRENT_FRAGMENT);

        if (currentFragment != null) {
            if(null!=intentextarvalue) {
                if (intentextarvalue.equals("sendsms"))
                    currentFragment = SmsActiFragment.SENDSMSFRAG;
                else if (intentextarvalue.equals("recharger"))
                    currentFragment = SmsActiFragment.RECHARGEFRAGT;
                else if (intentextarvalue.equals("carnet"))
                    currentFragment = SmsActiFragment.CARNETDADRESSFRAG;
                else if (intentextarvalue.equals("histo"))
                    currentFragment = SmsActiFragment.HISTORIQUEFRAG;
            }

            switch (currentFragment){
                case SENDSMSFRAG:
                    updateFragment(0, false);
                    break;
                case HISTORIQUEFRAG:
                    updateFragment(1, false);
                    break;
                case CARNETDADRESSFRAG:
                    updateFragment(2, contactCanBeSelectable);
                    break;

                case RECHARGEFRAGT:
                    updateFragment(3, false);
                    break;
            }
        }
    }

   /* private ActionBarDrawerToggle setupDrawerToggle() {
        return new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.drawer_open, R.string.drawer_close);
    }*/

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(KEY_CURRENT_FRAGMENT, currentFragment);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }

    public void selectDrawerItem(MenuItem menuItem) {

        // Create a new fragment and specify the fragment to show based on nav item clicked
        /*switch(menuItem.getItemId()) {
            case R.id.drawer_sub_item_invitation_received:
                updateFragment(0, true);
                break;
            case R.id.drawer_sub_item_invitation_sended:
                updateFragment(1, true);
                break;
            case R.id.drawer_sub_item_invitation_placement:
                PlacementDialogFragment.newInstance().show(getSupportFragmentManager(),null);
                break;
            case R.id.drawer_arbre_parainage:
                updateFragment(3,true);
                break;
            case R.id.drawer_arbre_placement:
                updateFragment(4,true);
                break;
*/

    //    }

        // Highlight the selected item has been done by NavigationView
       // menuItem.setChecked(true);
        // Close the navigation drawer
        // mDrawer.closeDrawers();
    }

    private void updateFragment(int witchFragment, boolean toBackStack){
        boolean isGood = false;
        boolean isNew = false;
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fl_content);

        //Vérification si le fragment est nul
        if (fragment == null){
            fragment = instantiateCorrectFragment(witchFragment);
            isNew = true;
        }else {
            //Vérifier si nous avons la bonne instance de fragment
            switch (witchFragment){
                case 0:
                    if (fragment instanceof SendSmsFrag)
                        isGood = true;
                    break;

                case 1:
                    if (fragment instanceof HistoriqueFrag)
                        isGood = true;
                    break;
                case 2:
                    if (fragment instanceof CarnetDadressFrag)
                        isGood = true;
                    break;
                case 3:
                    if (fragment instanceof RechargeFrag)
                        isGood = true;
                    break;

                default:
                    isGood = false;
            }

            //Si l'instance du fragment n'est pas la bonne, faire la bonne instanciation
            if (!isGood){
                fragment = instantiateCorrectFragment(witchFragment);
                isNew = true;
            }
        }

        if (fragment != null && isNew){
            doFragmentTransation(fragment, toBackStack);
        }
    }

    private void doFragmentTransation(Fragment fragment, boolean toBackStack){
        String tag;

        if (fragment instanceof SendSmsFrag){
            tag = "sendsms";
        } else if (fragment instanceof HistoriqueFrag)
            tag = "histo";
        else if (fragment instanceof CarnetDadressFrag)
            tag = "carnet";
        else if (fragment instanceof RechargeFrag)
            tag = "recharge";
        else
            tag = null;

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fl_content, fragment, tag);

        if (toBackStack)
            fragmentTransaction.addToBackStack(null);

        fragmentTransaction.commit();
    }

    private Fragment instantiateCorrectFragment(int witchFragment){
        switch (witchFragment){
            case 0:
                currentFragment = SmsActiFragment.SENDSMSFRAG;
                return SendSmsFrag.newInstance(1);

            case 1:
                currentFragment = SmsActiFragment.HISTORIQUEFRAG;
                return HistoriqueFrag.newInstance();
            case 2:
                currentFragment = SmsActiFragment.CARNETDADRESSFRAG;
                return CarnetDadressFrag.newInstance(false);
            case 3:
                currentFragment = SmsActiFragment.RECHARGEFRAGT;
                return RechargeFrag.newInstance();

            default:
                return null;
        }
    }

   /* @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return drawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }*/

    // `onPostCreate` called when activity start-up is complete after `onStart()`
    // NOTE! Make sure to override the method with only a single `Bundle` argument
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Sync the toggle state after onRestoreInstanceState has occurred.
        //drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        // drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void contactsSelectionner() {
        updateFragment(0,false);
    }

    @Override
    public void shoseContact() {
        updateFragment(2,false);
    }

    @Override
    public void viewdetail(String codeproduit) {

    }


    enum SmsActiFragment {SENDSMSFRAG, HISTORIQUEFRAG,CARNETDADRESSFRAG, RECHARGEFRAGT}
}