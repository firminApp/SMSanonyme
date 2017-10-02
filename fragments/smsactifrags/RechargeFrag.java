package com.firminapp.smsanonyme.fragments.smsactifrags;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firminapp.smsanonyme.R;
import com.firminapp.smsanonyme.activities.DashboardActi;
import com.firminapp.smsanonyme.activities.SmsActi;
import com.firminapp.smsanonyme.appconfig.PrefManager;
import com.firminapp.smsanonyme.services.VerificationService;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RechargeFrag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RechargeFrag extends Fragment {
    private EditText etcode;
    private Button btcheckCode;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    // TODO: Rename and change types of parameters


    public RechargeFrag() {
        // Required empty public constructor
    }
    @Override
    public void onStart(){
        super.onStart();

        ActionBar actionBar = ((SmsActi) getActivity()).getSupportActionBar();
        if (actionBar != null){
            actionBar.setTitle(getString(R.string.app_name));
            actionBar.setSubtitle(getString(R.string.tool_title_verification));

        }


        // Apply any required UI change now that the Fragment is visible.
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ActionBar actionBar = ((SmsActi) getActivity()).getSupportActionBar();
        if (actionBar != null){
            actionBar.setTitle(getString(R.string.app_name));
            actionBar.setSubtitle(getString(R.string.tool_title_recharge_frag));
        }

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        etcode=(EditText)view.findViewById(R.id.etcodrecharge);
        btcheckCode=(Button)view.findViewById(R.id.btstartcheking);
        Log.e("code dans les pref","code"+PrefManager.getInstance().getVerifCode());
        btcheckCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startChecking();
            }
        });
    }
    public void startChecking(){
        if (etcode.getText().toString().trim().equals(PrefManager.getInstance().getVerifCode().trim()))
        {
            Intent intentRefresh = new Intent(getContext(), DashboardActi.class);
            //intentRefresh.putExtra("CODE",etcode.getText().toString().trim());
            getActivity().startActivity(intentRefresh);

        }
        else
        {
            Toast.makeText(getContext(),"Code incorrect!", Toast.LENGTH_LONG).show();
        }

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *

     * @return A new instance of fragment RechargeFrag.
     */
    // TODO: Rename and change types and number of parameters
    public static RechargeFrag newInstance() {
        RechargeFrag fragment = new RechargeFrag();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        Log.e("frag","Recharge frag");
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
        return inflater.inflate(R.layout.fragment_recharge, container, false);
    }

}
