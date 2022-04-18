package com.example.projet_bicycle_revisions.database.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class MechanicEntity {


    @NonNull
    private String email;

    private String password;

    private String name;

    private String surname;

    private String telephone;

    private String address;


    public MechanicEntity() {
    }

    public MechanicEntity(@NonNull String email, String password, String name, String surname, String telephone, String address) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.telephone = telephone;
        this.address = address;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(@NonNull String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Exclude
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("firstName", name);
        result.put("lastName", surname);
        result.put("email", email);

        return result;
    }

}
