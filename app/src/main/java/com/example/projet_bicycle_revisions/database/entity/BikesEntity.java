package com.example.projet_bicycle_revisions.database.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "bikes", foreignKeys = @ForeignKey( entity = MechanicEntity.class, parentColumns = "email",childColumns = "mechanic"), indices = {@Index(value = {"mechanic"})})
public class BikesEntity {

    @PrimaryKey(autoGenerate = true)
    private Long id;
    private String bike;
    private String mechanic;

    @NonNull

    private String firstNameBike;

    private String lastNameBike;

    private String emailBike;

    private String telephoneBike;

    private String addressBike;

    private String descriptionBike;

    public BikesEntity() {
    }

    public BikesEntity(String mechanic, @NonNull String firstNameBike, String lastNameBike, String emailBike, String telephoneBike, String addressBike, String descriptionBike) {
        this.mechanic = mechanic;
        this.firstNameBike = firstNameBike;
        this.lastNameBike = lastNameBike;
        this.emailBike = emailBike;
        this.telephoneBike = telephoneBike;
        this.addressBike = addressBike;
        this.descriptionBike = descriptionBike;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBike() {
        return bike;
    }

    public void setBike(String bike) {
        this.bike = bike;
    }

    public String getMechanic() {
        return mechanic;
    }

    public void setMechanic(String mechanic) {
        this.mechanic = mechanic;
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
}
