package com.example.projet_bicycle_revisions.database.firebase;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.projet_bicycle_revisions.database.entity.BikesEntity;
import com.example.projet_bicycle_revisions.database.entity.MechanicEntity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class BikeLiveData extends LiveData<BikesEntity> {
    private static final String TAG = "MechanicLiveData";

    private final DatabaseReference reference;
    private final BikeLiveData.MyValueListener listener = new BikeLiveData.MyValueListener();

    public BikeLiveData(DatabaseReference ref){this.reference = ref;}

    @Override
    protected void onActive() {
        Log.d(TAG,"onActive");
        reference.addValueEventListener(listener);
    }

    @Override
    protected void onInactive() {
        Log.d(TAG,"onInactive");
    }

    private class MyValueListener implements ValueEventListener {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            BikesEntity entity = dataSnapshot.getValue(BikesEntity.class);
            entity.setId((dataSnapshot.getKey()));
            setValue(entity);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Log.e(TAG,"Can't Listen to query"+reference,databaseError.toException());

        }
    }
}

