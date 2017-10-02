package com.firminapp.smsanonyme.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.firminapp.smsanonyme.R;
import com.firminapp.smsanonyme.appdata.dataModels.ContactModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by firmin on 14/09/17.
 */

public class ContactsRecyclerAdapter extends RecyclerView.Adapter<ContactsRecyclerAdapter.RecyclerViewHolder> {
    private List<ContactModel> contactList;
    private ArrayList<ContactModel> selectedContact;
    private View.OnLongClickListener longClickListener;
    private boolean canselect;
    public ContactsRecyclerAdapter(List<ContactModel> contactList, View.OnLongClickListener longClickListener) {
        this.contactList = contactList;
        this.longClickListener = longClickListener;

    }
    public ContactsRecyclerAdapter(List<ContactModel> contactList, View.OnLongClickListener longClickListener, boolean canselect) {
        this.contactList = contactList;
        this.longClickListener = longClickListener;
        this.canselect=canselect;
        this.selectedContact=new ArrayList<>();
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RecyclerViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_contact_itm, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecyclerViewHolder holder, int position) {
        final ContactModel contactModel = contactList.get(position);
        if (canselect)
        {
            holder.checkBox.setVisibility(View.VISIBLE);
            holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b)
                    {
                        selectedContact.add(contactModel);
                    }
                }
            });
        }
        else
        {
            holder.checkBox.setVisibility(View.GONE);
        }
        holder.tvnom.setText(contactModel.nom);
        holder.tvtel.setText(contactModel.tel);
        holder.itemView.setOnLongClickListener(longClickListener);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedContact.add(contactModel);
            }
        });
        holder.itemView.setTag(contactModel);
        holder.setIsRecyclable(true);



    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    public void addItems(List<ContactModel> contactList) {
        this.contactList = contactList;
        notifyDataSetChanged();
    }

    static class RecyclerViewHolder extends RecyclerView.ViewHolder {
        private TextView tvnom;
        private TextView tvtel;
        private CheckBox checkBox;

        RecyclerViewHolder(View view) {
            super(view);
            tvnom = (TextView) view.findViewById(R.id.contact_item_tvnom);
            tvtel = (TextView) view.findViewById(R.id.contact_item_tvtel);
            checkBox = (CheckBox) view.findViewById(R.id.contactcheckbox);
        }
    }
    public   ArrayList<ContactModel> getSelection()
    {
       return selectedContact;
    }

}