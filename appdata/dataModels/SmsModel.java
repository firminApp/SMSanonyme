package com.firminapp.smsanonyme.appdata.dataModels;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by firmin on 14/09/17.
 */

@Entity
public class SmsModel {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String msg;
    public String from;
    //public String to;
    //public String statut;
    //public String datecreate;

    public SmsModel(String msg, String from) {
        this.msg = msg;
        this.from = from;
    }
}
