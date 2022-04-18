package com.example.projet_bicycle_revisions.database.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

@Entity(tableName = "bikes", foreignKeys = @ForeignKey( entity = MechanicEntity.class,
        parentColumns = "email",childColumns = "mechanic",onDelete = ForeignKey.CASCADE), indices = {@Index(value = {"mechanic"})})
public class BikesEntity {

    @PrimaryKey(autoGenerate = true)
    private String id;

    private String typeBike;

    @NonNull
    private String firstNameBike;

    private String lastNameBike;

    private String emailBike;

    private String telephoneBike;

    private String addressBike;

    private String descriptionBike;

    private boolean status;

    public BikesEntity() {
    }

    public BikesEntity(String typeBike, @NonNull String firstNameBike, String lastNameBike, String emailBike, String telephoneBike, String addressBike, String descriptionBike, boolean finished) {
        this.firstNameBike = firstNameBike;
        this.lastNameBike = lastNameBike;
        this.emailBike = emailBike;
        this.telephoneBike = telephoneBike;
        this.addressBike = addressBike;
        this.descriptionBike = descriptionBike;
        this.typeBike = typeBike;
        this.status = finished;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @NonNull
    public String getFirstNameBike() {
        return firstNameBike;
    }

    public void setFirstNameBike(@NonNull String firstNameBike) {
        this.firstNameBike = firstNameBike;
    }

    public String getLastNameBike() {
        return lastNameBike;
    }

    public void setLastNameBike(String lastNameBike) {
        this.lastNameBike = lastNameBike;
    }

    public String getEmailBike() {
        return emailBike;
    }

    public void setEmailBike(String emailBike) {
        this.emailBike = emailBike;
    }

    public String getTelephoneBike() {
        return telephoneBike;
    }

    public void setTelephoneBike(String telephoneBike) {
        this.telephoneBike = telephoneBike;
    }

    public String getAddressBike() {
        return addressBike;
    }

    public void setAddressBike(String addressBike) {
        this.addressBike = addressBike;
    }

    public String getDescriptionBike() {
        return descriptionBike;
    }

    public void setDescriptionBike(String descriptionBike) {
        this.descriptionBike = descriptionBike;
    }

    public String getTypeBike() {
        return typeBike;
    }

    public void setTypeBike(String typeBike) {
        this.typeBike = typeBike;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("firstNameBike",firstNameBike);
        result.put("lastNameBike",lastNameBike);
        result.put("telephoneBike",telephoneBike);
        result.put("addressBike",addressBike);
        result.put("typeBike",typeBike);
        result.put("descriptionBike",descriptionBike);
        result.put("status",status);

        return result;
    }
}
