package com.example.projet_bicycle_revisions.database.firebase;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.projet_bicycle_revisions.database.entity.MechanicEntity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class MechanicLiveData extends LiveData<MechanicEntity> {
    private static final String TAG = "MechanicLiveData";

    private final DatabaseReference reference;
    private final MechanicLiveData.MyValueListener listener = new MechanicLiveData.MyValueListener();

    public MechanicLiveData(DatabaseReference ref){this.reference = ref;}

    @Override
    protected void onActive() {
        Log.d(TAG,"onActive");
        reference.addValueEventListener(listener);
    }

    @Override
    protected void onInactive() {
        Log.d(TAG,"onInactive");
    }

    private class MyValueListener implements ValueEventListener{
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            MechanicEntity entity = dataSnapshot.getValue(MechanicEntity.class);
            setValue(entity);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Log.e(TAG,"Can't Listen to query"+reference,databaseError.toException());

        }
    }
}
