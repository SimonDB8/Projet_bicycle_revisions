package com.example.projet_bicycle_revisions.database.repository;

import android.provider.ContactsContract;

import androidx.lifecycle.LiveData;

import com.example.projet_bicycle_revisions.database.entity.BikesEntity;
import com.example.projet_bicycle_revisions.database.firebase.BikeListLiveData;
import com.example.projet_bicycle_revisions.database.firebase.BikeLiveData;
import com.example.projet_bicycle_revisions.util.OnAsyncEventListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class BikeRepository {
    private static BikeRepository instance;

    private BikeRepository() {
    }

    public static BikeRepository getInstance() {
        if(instance == null){
            synchronized (BikeRepository.class){
                if(instance == null){
                    instance = new BikeRepository();
                }
            }
        }
        return instance;
    }

    public LiveData<BikesEntity> getBike(final String id){
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("mechanic")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("bikes")
                .child(id);
        System.out.println(reference.getKey());
        return new BikeLiveData(reference);
    }

    public LiveData<List<BikesEntity>> getBikeByStatus(final boolean status){
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("mechanic")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("bikes")
                .child(String.valueOf(status));
        System.out.println(reference.getKey());
        return new BikeListLiveData(reference);


    }

    public void insert(final BikesEntity bike, final OnAsyncEventListener callback){
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("Mechanic")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("bikes");
        String key = reference.push().getKey();
        FirebaseDatabase.getInstance()
                .getReference("mechanic")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("bikes")
                .child(key)
                .setValue(bike,(databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
        FirebaseDatabase.getInstance()
                .getReference("mechanic")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("bikes")
                .child("false")
                .child(key)
                .setValue(bike,(databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });

    }

    public void update(final BikesEntity bike, final OnAsyncEventListener callback){
        FirebaseDatabase.getInstance()
                .getReference("mechanic")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("bikes")
                .child(bike.getId())
                .updateChildren(bike.toMap(), (databaseError, databaseReference) -> {
                    if (databaseError != null){
                        callback.onFailure(databaseError.toException());
                    }else{
                        callback.onSuccess();
                    }
                });
        //remove status
        FirebaseDatabase.getInstance()
        .getReference("mechanic")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("bikes")
                .child("true")
                .child(bike.getId())
                .removeValue((databaseError, databaseReference)->{
                    if (databaseError != null){
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
        FirebaseDatabase.getInstance()
                .getReference("mechanic")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("bikes")
                .child("false")
                .child(bike.getId())
                .removeValue((databaseError, databaseReference)->{
                    if (databaseError != null){
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
        //set new status
        FirebaseDatabase.getInstance()
                .getReference("mechanic")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("bikes")
                .child(String.valueOf(bike.isStatus()))
                .child(bike.getId())
                .setValue(bike,(databaseError, databaseReference)->{
                    if (databaseError != null){
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });


    }


    public void delete(final BikesEntity bike, OnAsyncEventListener callback){
        FirebaseDatabase.getInstance()
                .getReference("mechanic")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("bikes")
                .child(bike.getId())
                .removeValue((databaseError, databaseReference)->{
                    if (databaseError != null){
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
        FirebaseDatabase.getInstance()
                .getReference("mechanic")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("bikes")
                .child(String.valueOf(bike.isStatus()))
                .removeValue((databaseError, databaseReference)->{
                    if (databaseError != null){
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }
}
