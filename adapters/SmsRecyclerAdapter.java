package com.firminapp.smsanonyme.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firminapp.smsanonyme.R;
import com.firminapp.smsanonyme.appdata.AppDatabase;
import com.firminapp.smsanonyme.appdata.dataModels.MessageToContactModel;
import com.firminapp.smsanonyme.appdata.dataModels.SmsModel;

import java.util.List;

/**
 * Created by firmin on 14/09/17.
 */

public class SmsRecyclerAdapter extends RecyclerView.Adapter<SmsRecyclerAdapter.RecyclerViewHolder> {
    private List<SmsModel> smsList;
    private View.OnLongClickListener longClickListener;

    public SmsRecyclerAdapter(List<SmsModel> smsList, View.OnLongClickListener longClickListener) {
        this.smsList = smsList;
        this.longClickListener = longClickListener;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RecyclerViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_sms_itm, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecyclerViewHolder holder, int position) {
        SmsModel SmsModel = smsList.get(position);
        holder.tvfrom.setText(SmsModel.from);
        holder.tvmsg.setText(SmsModel.msg);
        holder.itemView.setOnLongClickListener(longClickListener);
        holder.itemView.setTag(SmsModel);
    }

    @Override
    public int getItemCount() {
        return smsList.size();
    }

    public void addItems(List<SmsModel> smsList) {
        this.smsList = smsList;
        notifyDataSetChanged();
    }

    static class RecyclerViewHolder extends RecyclerView.ViewHolder {
        private TextView tvfrom;
        private TextView tvmsg;

        RecyclerViewHolder(View view) {
            super(view);
            tvfrom = (TextView) view.findViewById(R.id.sms_item_tvfrom);
            tvmsg = (TextView) view.findViewById(R.id.sms_item_tvmsg);
        }
    }
}