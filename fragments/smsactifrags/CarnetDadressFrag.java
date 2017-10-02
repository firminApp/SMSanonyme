package com.firminapp.smsanonyme.fragments.smsactifrags;

import android.app.AlertDialog;
import android.arch.lifecycle.LifecycleFragment;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.firminapp.smsanonyme.R;
import com.firminapp.smsanonyme.activities.SmsActi;
import com.firminapp.smsanonyme.adapters.ContactsRecyclerAdapter;
import com.firminapp.smsanonyme.appdata.dataModels.ContactModel;
import com.firminapp.smsanonyme.appdata.viewmodels.AddContactViewModel;
import com.firminapp.smsanonyme.appdata.viewmodels.ContactViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CarnetDadressFrag.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CarnetDadressFrag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CarnetDadressFrag extends LifecycleFragment implements  View.OnLongClickListener {

    private OnFragmentInteractionListener mListener;
    private RecyclerView recyclerView;
    private ContactsRecyclerAdapter recyclerViewAdapter;
    private AddContactViewModel addcontactviewModel;
    private ContactViewModel contactViewModel;
    private View rootlayout;
  

    public CarnetDadressFrag() {
        // Required empty public constructor
    }


    @Override
    public void onStart(){
        super.onStart();

        ActionBar actionBar = ((SmsActi) getActivity()).getSupportActionBar();
        if (actionBar != null){
            actionBar.setTitle(getString(R.string.app_name));
            actionBar.setSubtitle(getString(R.string.tool_title_carnetdadress_frag));

        }


        // Apply any required UI change now that the Fragment is visible.
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ActionBar actionBar = ((SmsActi) getActivity()).getSupportActionBar();
        if (actionBar != null){
            actionBar.setTitle(getString(R.string.app_name));
            actionBar.setSubtitle(getString(R.string.tool_title_carnetdadress_frag));
        }

    }
    public static CarnetDadressFrag newInstance(boolean canselect) {
        CarnetDadressFrag fragment = new CarnetDadressFrag();
        Bundle args = new Bundle();
        args.putBoolean("canselect", canselect);
        fragment.setArguments(args);
        Log.e("frag","Carnet d'adresse frag");
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

     if (getArguments() != null) {
         SmsActi.contactCanBeSelectable=getArguments().getBoolean("canselect");
        }

    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.carnetdadressfrag_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

       switch (item.getItemId())
       {
           case R.id.action_select:
               if (SmsActi.contactCanBeSelectable==false){
                   SmsActi.contactCanBeSelectable=true;
                   reloadfrag();
               }
               else
               if  (SmsActi.contactCanBeSelectable==true)
               { SmsActi.contactCanBeSelectable=false;
                   reloadfrag();
               }
               break;
           case R.id.action_selection_ok:
               SmsActi.selectedContact=recyclerViewAdapter.getSelection();
               mListener.contactsSelectionner();

               break;
           case R.id.action_add_contac:
               createNewContact();

               break;
       }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_carnet_dadress, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView=(RecyclerView)view.findViewById(R.id.recyclercontact);
        recyclerViewAdapter = new ContactsRecyclerAdapter(new ArrayList<ContactModel>(), this,   SmsActi.contactCanBeSelectable);
        RecyclerView.LayoutManager layoutManager =new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(recyclerViewAdapter);
        addcontactviewModel = ViewModelProviders.of(this).get(AddContactViewModel.class);
        rootlayout=view.findViewById(R.id.rootlayout);
        contactViewModel = ViewModelProviders.of(this).get(ContactViewModel.class);

        contactViewModel.getItemAndContactList().observe(this, new Observer<List<ContactModel>>() {
            @Override
            public void onChanged(@Nullable List<ContactModel> contacts) {
                recyclerViewAdapter.addItems(contacts);
            }
        });
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public boolean onLongClick(final View view) {
        AlertDialog.Builder alert=new AlertDialog.Builder(getContext());
        alert.setNegativeButton("Non",null);
        alert.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ContactModel contat = (ContactModel) view.getTag();
                Log.e("idcontact", contat.id+"");
                contactViewModel.delete(contat);
            }
        });
        alert.setTitle("Suppression");
        alert.setMessage("Supprimer ce contact?");
        alert.show();
        return false;
    }

    public void reloadfrag()
    {
        Fragment frg = null;
        frg = getActivity().getSupportFragmentManager().findFragmentByTag("carnet");
        final FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.detach(frg);
        ft.attach(frg);
        ft.commit();
    }
    public void createNewContact(){
        AlertDialog.Builder alert=new AlertDialog.Builder(getContext());
        View v=LayoutInflater.from(getContext()).inflate(R.layout.addnewcontactform,null);
        alert.setView(v);
        final EditText etnom=(EditText)v.findViewById(R.id.addcontact_etnom) ;
        final EditText ettel=(EditText)v.findViewById(R.id.addcontact_ettel) ;
        ettel.setInputType(InputType.TYPE_CLASS_PHONE);
        alert.setPositiveButton("Ajouter", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(TextUtils.isEmpty(ettel.getText().toString().trim())|ettel.getText().toString().trim().length()<8)
                {
                    ettel.setError(getString(R.string.inccorect_tel_error));
                    Snackbar.make(rootlayout,R.string.inccorect_tel_error,Snackbar.LENGTH_LONG).show();
                }else
                {
                    String nom="";
                    String tel=ettel.getText().toString().trim();
                    if (TextUtils.isEmpty(etnom.getText().toString().trim()))
                        nom="Inconnu";
                    else
                        nom=etnom.getText().toString().trim();
                    ContactModel contact=new ContactModel(nom,tel);
                    addcontactviewModel.addContact(contact);

                }
            }
        });
        alert.setNegativeButton("Annuler",null);
        alert.setTitle("Nouveau contact");
        alert.show();
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
        //void shoseContact();
        void contactsSelectionner();
    }

}
