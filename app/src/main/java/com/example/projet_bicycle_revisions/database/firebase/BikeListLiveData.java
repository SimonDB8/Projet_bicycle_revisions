package com.example.projet_bicycle_revisions.database.firebase;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.projet_bicycle_revisions.database.entity.BikesEntity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class BikeListLiveData extends LiveData<List<BikesEntity>> {

    private static final String TAG = "BikeListLiveData";

    private final DatabaseReference reference;
    private final MyValueEventListener listener = new MyValueEventListener();

    public BikeListLiveData(DatabaseReference ref){
        this.reference = ref;
    }

    @Override
    protected void onActive() {
        Log.d(TAG,"onActive");
        reference.addValueEventListener(listener);
    }

    @Override
    protected void onInactive() {
        Log.d(TAG, "onInactive");
    }

    private class MyValueEventListener implements ValueEventListener{
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            setValue(bikesList(dataSnapshot));
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Log.e(TAG,"Can't listen to query"+reference+databaseError.toException());
        }
    }
    private List<BikesEntity> bikesList(DataSnapshot dataSnapshot){
        List<BikesEntity> bikes = new ArrayList<>();
        for (DataSnapshot childSnapshot : dataSnapshot.getChildren()){
            BikesEntity entity = childSnapshot.getValue(BikesEntity.class);
            entity.setId(childSnapshot.getKey());
            bikes.add(entity);
        }
        return bikes;
    }
}
