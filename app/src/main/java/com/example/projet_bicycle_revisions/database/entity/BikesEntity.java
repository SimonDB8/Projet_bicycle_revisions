package com.example.projet_bicycle_revisions.database.entity;

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

    public BikesEntity() {
    }

    public BikesEntity(String bike, String mechanic) {
        this.bike = bike;
        this.mechanic = mechanic;
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
}
