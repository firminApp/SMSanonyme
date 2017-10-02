package com.firminapp.smsanonyme.appdata.dataModels;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.util.TableInfo;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by firmin on 14/09/17.
 */

@Entity(indices = {@Index(value = {"smsId", "contactId"})}, foreignKeys ={ @ForeignKey(entity = SmsModel.class,
        parentColumns = "id",
        childColumns = "smsId"),
        @ForeignKey(entity = ContactModel.class,parentColumns = "id",childColumns = "contactId")})
public class MessageToContactModel {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public int contactId;
    public int smsId;
    public String datesend;

    public MessageToContactModel(int contactId, int smsId) {
        this.contactId = contactId;
        this.smsId = smsId;
        this.datesend=new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date());
    }
}
