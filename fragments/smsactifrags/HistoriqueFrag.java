package com.firminapp.smsanonyme.fragments.smsactifrags;

import android.app.AlertDialog;
import android.arch.lifecycle.LifecycleFragment;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firminapp.smsanonyme.R;
import com.firminapp.smsanonyme.activities.SmsActi;
import com.firminapp.smsanonyme.adapters.ContactsRecyclerAdapter;
import com.firminapp.smsanonyme.adapters.SmsRecyclerAdapter;
import com.firminapp.smsanonyme.appdata.dataModels.ContactModel;
import com.firminapp.smsanonyme.appdata.dataModels.SmsModel;
import com.firminapp.smsanonyme.appdata.viewmodels.ContactViewModel;
import com.firminapp.smsanonyme.appdata.viewmodels.SmsViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HistoriqueFrag.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HistoriqueFrag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HistoriqueFrag extends LifecycleFragment implements  View.OnLongClickListener{

    private OnFragmentInteractionListener mListener;
    private RecyclerView recyclerView;
    private SmsViewModel viewModel;
    private SmsRecyclerAdapter recyclerViewAdapter;
    public HistoriqueFrag() {
        // Required empty public constructor
    }
    @Override
    public void onStart(){
        super.onStart();

        ActionBar actionBar = ((SmsActi) getActivity()).getSupportActionBar();
        if (actionBar != null){
            actionBar.setTitle(getString(R.string.app_name));
            actionBar.setSubtitle(getString(R.string.tool_title_historique_frag));

        }


        // Apply any required UI change now that the Fragment is visible.
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ActionBar actionBar = ((SmsActi) getActivity()).getSupportActionBar();
        if (actionBar != null){
            actionBar.setTitle(getString(R.string.app_name));
            actionBar.setSubtitle(getString(R.string.tool_title_historique_frag));
        }

    }
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *

     * @return A new instance of fragment HistoriqueFrag.
     */
    // TODO: Rename and change types and number of parameters
    public static HistoriqueFrag newInstance() {
        HistoriqueFrag fragment = new HistoriqueFrag();
        Bundle args = new Bundle();
        Log.e("frag","Historique frag");
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_historique2, container, false);
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
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView=(RecyclerView)view.findViewById(R.id.recyclersms);
        recyclerViewAdapter = new SmsRecyclerAdapter(new ArrayList<SmsModel>(), this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        recyclerView.setAdapter(recyclerViewAdapter);

        viewModel = ViewModelProviders.of(this).get(SmsViewModel.class);

        viewModel.getItemSmsList().observe(this, new Observer<List<SmsModel>>() {
            @Override
            public void onChanged(@Nullable List<SmsModel> smss) {
                recyclerViewAdapter.addItems(smss);
            }
        });
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
                SmsModel sms = (SmsModel) view.getTag();
                Log.e("idcontact", sms.id+"");
                viewModel.delete(sms);
            }
        });
        alert.setTitle("Suppression");
        alert.setMessage("Supprimer ce contact?");
        alert.show();
        return false;
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
    }
}
