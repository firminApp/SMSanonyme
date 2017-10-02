package com.firminapp.smsanonyme.fragments.smsactifrags;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.arch.lifecycle.LifecycleFragment;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.firminapp.smsanonyme.MainActivity;
import com.firminapp.smsanonyme.R;
import com.firminapp.smsanonyme.activities.SmsActi;
import com.firminapp.smsanonyme.appconfig.PrefManager;
import com.firminapp.smsanonyme.appconfig.SmsApp;
import com.firminapp.smsanonyme.appconfig.WebsServices;
import com.firminapp.smsanonyme.appdata.AppDatabase;
import com.firminapp.smsanonyme.appdata.dataModels.ContactModel;
import com.firminapp.smsanonyme.appdata.dataModels.SmsModel;
import com.firminapp.smsanonyme.appdata.viewmodels.AddContactViewModel;
import com.firminapp.smsanonyme.appdata.viewmodels.ContactViewModel;
import com.firminapp.smsanonyme.appdata.viewmodels.SmsViewModel;
import com.github.ybq.android.spinkit.SpinKitView;
import com.github.ybq.android.spinkit.style.DoubleBounce;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SendSmsFrag extends LifecycleFragment implements View.OnClickListener{

    private static final String KEY_SAVED_SCROLL_POSITION = "com.firminapp.smsanonyme.KEY_SAVED_SCROLL_POSITION";
    private final String TAG = this.getClass().getSimpleName();
    public static final int PICK_CONTACT=11;
    public static int MY_PERMISSIONS_REQUEST_READ_CONTACTS=10;
    private int scrollPosition = 0;
    private RecyclerView recyclerView;
    private SmsViewModel viewModel;
    private String slectedcontact="";
    private String currentcontactname, currentcontactphone;
    private ArrayList<ContactModel>destinationListe;

    private ImageView emptylist;
    private SmsApp owoMyApp;
    private SpinKitView spv;
    public ProgressBar progressBar;
    public String aftercorrection;

    //private SendSmsFrag.GetJsonArray js=null;

    private static final int ITEM_SPACING = 5;

    private View rootlayout;

    private FloatingActionButton fladd, flenvoyer;
    private ImageButton ibcontact;
    private EditText etexpedi, etdesti, etmsg;
    private AddContactViewModel cvm;


    private ActionBar actionBar;

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private SendSmsFrag.OnListFragmentInteractionListener mListener;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public SendSmsFrag() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static SendSmsFrag newInstance(int columnCount) {
        SendSmsFrag fragment = new SendSmsFrag();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        Log.e("frag","Send sms frag");
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_send_sms, container, false);

        return view;

    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        progressBar=(ProgressBar)view.findViewById(R.id.pb_loader) ;
        DoubleBounce doubleBounce = new DoubleBounce();
        progressBar.setIndeterminateDrawable(doubleBounce);
        etdesti=(EditText)view.findViewById(R.id.etdestinataire);
        etexpedi=(EditText)view.findViewById(R.id.etexpediteur);
        etmsg=(EditText)view.findViewById(R.id.etmessagebody);
        rootlayout=view.findViewById(R.id.rootlayout);
        spv=(SpinKitView)view.findViewById(R.id.animelayout);
        ibcontact=(ImageButton) view.findViewById(R.id.ibcontact);
        flenvoyer=(FloatingActionButton)view.findViewById(R.id.flsendsms);
        flenvoyer.setOnClickListener(this);
        ibcontact.setOnClickListener(this);
        viewModel= ViewModelProviders.of(this).get(SmsViewModel.class);
        cvm= ViewModelProviders.of(this).get(AddContactViewModel.class);
        destinationListe=new ArrayList<>();
        spv.setVisibility(View.GONE);

        if (SmsActi.selectedContact!=null&&SmsActi.selectedContact.size()>0)
        {
            String contacts="";
            for (ContactModel contactModel:SmsActi.selectedContact)
            {
               contacts+=","+contactModel.tel;

            }
            etdesti.setText(etdesti.getText()+contacts);
        }
    }
    public void onResume(){
        super.onResume();
    }
    @Override
    public void onStart(){
        super.onStart();

        ActionBar actionBar = ((SmsActi) getActivity()).getSupportActionBar();
        if (actionBar != null){
            actionBar.setTitle(getString(R.string.app_name));
            actionBar.setSubtitle(getString(R.string.tool_title_sendsms_frag));
        }


        // Apply any required UI change now that the Fragment is visible.
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ActionBar actionBar = ((SmsActi) getActivity()).getSupportActionBar();
        if (actionBar != null){
            actionBar.setTitle(getString(R.string.app_name));
            actionBar.setSubtitle(getString(R.string.tool_title_sendsms_frag));
        }

        if (savedInstanceState != null) {
            scrollPosition = savedInstanceState.getInt(KEY_SAVED_SCROLL_POSITION);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e("sendsmsfrag","Dans onPAuse de sendsmsfrag");

    }

    @Override
    public void onStop() {
        super.onStop();
        /*if (js==null)
        {

        }else
            js.cancel(true);*/
        Log.e("sendsmsfrag","Dans onStop de sendsmsfrag");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //new SendSmsFrag.GetJsonArray(rootpart).execute();
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }

    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private void sendSms(String body, String from, String to){
        Log.d("Send", "Preparing to send sms");
       swichViews(View.VISIBLE,View.GONE,View.GONE);
        OkHttpClient client = new OkHttpClient();
        final Request request = makeRequest(from,to,body);

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
                        Log.d("sendsms token", "reponse de l'api= "+response.toString());
                        if (response.code() == 200){
                            /* String responseData = response.body().string();
                             Log.d("sendsms", " "+responseData);
                             JSONObject json = new JSONObject(responseData);
                             JSONObject jsono = json.getJSONArray("data").getJSONObject(0);

                             if (jsono.has("status")){

                                 if (jsono.getString("status").equals("OK"))
                                 {*/
                            Snackbar.make(rootlayout,"Message envoyé avec succès",Snackbar.LENGTH_LONG).show();
                            succefullySended();
                            swichViews(View.GONE,View.VISIBLE,View.VISIBLE);
                                  /*  }
                                    else
                                    Snackbar.make(rootlayout,"Il y a eu une erreur inatendu de! Veuillez reéssayez svp",Snackbar.LENGTH_LONG).show();

                                    swichViews(View.GONE,View.VISIBLE,View.VISIBLE);
                                } else{
                                    Log.d(TAG, "Mauvais Json");
                                    swichViews(View.GONE,View.VISIBLE,View.VISIBLE);
                                    Snackbar.make(rootlayout,"Il y a eu une erreur inatendu! Réponse serveur incohérrente. Réessayez svp.",Snackbar.LENGTH_LONG).show();

                                }*/
                        } else{
                            swichViews(View.GONE,View.VISIBLE,View.VISIBLE);
                            Snackbar.make(rootlayout,"Message non envoyé. Reessayez svp...",Snackbar.LENGTH_LONG).show();
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

    public Request makeRequest(String from, String to, String body){
        PrefManager pm=PrefManager.getInstance();
        HttpUrl.Builder urlBuilder = HttpUrl.parse(WebsServices.APISMS).newBuilder();
        urlBuilder.addQueryParameter(pm.getApipasskey(), pm.getApiPass());
       // urlBuilder.addQueryParameter(pm.getApiappkey(), pm.getApiapp());
        urlBuilder.addQueryParameter(pm.getApiuserkey(), pm.getApiUser());
       // urlBuilder.addQueryParameter(pm.getApiopkey(), "pv");
       // urlBuilder.addQueryParameter(pm.getApitokenkey(), pm.getApiToken());
        urlBuilder.addQueryParameter(pm.getApitokey(), to);
        urlBuilder.addQueryParameter(pm.getApifromkey(), from);
        urlBuilder.addQueryParameter(pm.getApibodykey(), body);
        String url = urlBuilder.build().toString();
        Log.d("refreshing token", "url= "+url);
        return new Request.Builder()
                .url(url)
                .get()
                .build();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.flsendsms:
                collectdataAndSendit();
                break;
            case R.id.ibcontact:
                Log.e("bouton","flcarnet");
                showContactdialog();

                break;
              }

    }
    public  void collectdataAndSendit(){
        String from=etexpedi.getText().toString();
        String to=etdesti.getText().toString();
        String body=etmsg.getText().toString();
         if (from.equals(""))

         {
             etexpedi.setError("Destinataire vide");
         }else  if (to.equals(""))
         {
             etdesti.setError("Expéditaire vide");
         }else if (body.equals(""))
         {
             etmsg.setError("Message vid!");
         }
         else
         {
             sendSms(body,from,to);
             final SmsModel smsModel=new SmsModel(body,from);
             new Thread(new Runnable() {
                 @Override
                 public void run() {
                     AppDatabase.getDatabase(getContext()).itemAndSmsModel().addNew(smsModel);
                 }
             }).start();

         }
    }
    public void swichViews(final int pbstatut, final int flboutonstatut, final int rootlayoutstatut){
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {

                rootlayout.setVisibility(rootlayoutstatut);
                progressBar.setVisibility(pbstatut);
                flenvoyer.setVisibility(flboutonstatut);
                ibcontact.setVisibility(flboutonstatut);
                spv.setVisibility(pbstatut);

            }
        });
    }
    public void succefullySended(){
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
        etexpedi.setText("");
        etdesti.setText("");
        etmsg.setText("");
            }
        });
    }
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        //void onListFragmentInteraction(Produit item);
        void shoseContact();
        public void viewdetail(String codeproduit);
    }

    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);

        switch (reqCode) {
            case PICK_CONTACT :
                if (resultCode == Activity.RESULT_OK) {
                    final String name="";
                    String number="";

                    Uri contactData = data.getData();
                    Cursor cursor =  getActivity().managedQuery(contactData, null, null, null, null);
                    if (cursor.moveToFirst()) {


                        String id =cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.Contacts._ID));

                        String hasPhone =cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
                        currentcontactname = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

                        if (hasPhone.equalsIgnoreCase("1")) {
                            Cursor phones = getActivity().getContentResolver().query(
                                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,
                                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = "+ id,
                                    null, null);
                            while (phones.moveToNext()) {
                                //si aucun contact dans la liste on ne met pas de ,
                                if (!slectedcontact.trim().equals(""))
                                {
                                    slectedcontact=slectedcontact+",";
                                }
                                currentcontactphone=phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                                ContactModel contact=new ContactModel(currentcontactname,currentcontactphone);
                              if (isCorrectphoneNumber(currentcontactphone))
                              {
                                //  cvm.addContact(contact);
                                 // slectedcontact+=contact.tel;
                                  //  number = phones.getString(phones.getColumnIndex("DATA_1"));
                                  System.out.println("number is:" + currentcontactphone+" and name is: "+currentcontactname);

                              }
                              else {

                                  currentcontactphone=corrigerNum(currentcontactphone);
                                /*  Log.e("cooooo",currentcontactphone);
                                  Log.e("after",aftercorrection);
                                  contact=new ContactModel(currentcontactname,currentcontactphone);*/

                                  contact=new ContactModel(currentcontactname,currentcontactphone);

                              }

                                slectedcontact+=contact.tel;
                                etdesti.setText(slectedcontact);
                                cvm.addContact(contact);
                                Log.e("cooooo",contact.tel+"");
                               }

                        }

                    }
                }
                etdesti.setText(slectedcontact);
                break;
        }
    }
    public  boolean isCorrectphoneNumber(String phonnumber)
    {
        return  phonnumber.length()>8&&phonnumber.trim().contains("+")&&phonnumber.indexOf("+")==0;
    }

    public String corrigerNum(final String beforcorrection)
    {
        final AlertDialog.Builder alert=new AlertDialog.Builder(getContext());
        alert.setTitle(getString(R.string.corriger_num));
        final EditText etnum=new EditText(getContext());
        etnum.setInputType(InputType.TYPE_CLASS_PHONE);
        etnum.setHint(getString(R.string.correct_phone_exemple));
        etnum.setText(beforcorrection);

        alert.setPositiveButton(getString(R.string.ok),
                new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                    aftercorrection= etnum.getText().toString().trim();
                    Log.e("corect",aftercorrection);

            }
        });
        alert.setView(etnum);
        alert.show();
        return aftercorrection;
    }
public void showContactdialog(){
    final AlertDialog.Builder alert=new AlertDialog.Builder(getContext());
    alert.setTitle("Liste de contactes");
    boolean[]listb={false,false,false};
    CharSequence[]items=new CharSequence[]{"Contactes du téléphone","Contacte de l'application", "Annuller"};
   alert.setItems(items, new DialogInterface.OnClickListener() {
       @Override
       public void onClick(DialogInterface dialogInterface, int i) {
           switch (i){
               case 0:
                   shoseInphonecontact();
                   break;
               case 1:
                   shoseincarnetdadresse();
                   break;
               case 3:
                   break;
           }
       }
   });
    alert.show();
}

public void shoseInphonecontact(){

    if (ContextCompat.checkSelfPermission(getContext(),
            Manifest.permission.READ_CONTACTS)
            != PackageManager.PERMISSION_GRANTED) {

        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                Manifest.permission.READ_CONTACTS)) {

            Log.e("permision","deja accordée");


            //Cela signifie que la permission à déjà était
            //demandé et l'utilisateur l'a refusé
            //Vous pouvez aussi expliquer à l'utilisateur pourquoi
            //cette permission est nécessaire et la redemander
        } else {
            Log.e("permision","demande de permission");
            //Sinon demander la permission
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.READ_CONTACTS},
                    MY_PERMISSIONS_REQUEST_READ_CONTACTS);
        }

    }
    Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
    startActivityForResult(intent, PICK_CONTACT);
}
public void shoseincarnetdadresse(){
mListener.shoseContact();
}
}
