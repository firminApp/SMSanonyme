package com.firminapp.smsanonyme.appdata.dataModels;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by firmin on 14/09/17.
 */
@Entity(indices = {@Index(value = {"nom", "tel"},
        unique = true)})

public class ContactModel {
    @PrimaryKey(autoGenerate = true)
    public  int id;
    public String nom;
    public  String tel;

    public ContactModel(String nom, String tel) {
        this.nom = nom;
        this.tel = tel;
    }
}
