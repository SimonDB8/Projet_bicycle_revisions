package com.example.projet_bicycle_revisions.database.repository;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.projet_bicycle_revisions.database.entity.MechanicEntity;
import com.example.projet_bicycle_revisions.database.firebase.MechanicLiveData;
import com.example.projet_bicycle_revisions.util.OnAsyncEventListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MechanicRepository {

    private static final String TAG = "MechanicRepository";

    private static MechanicRepository instance;


    private MechanicRepository() {
    }

    public  static MechanicRepository getInstance(){
        if (instance == null) {
            synchronized (MechanicRepository.class) {
                if (instance == null) {
                    instance = new MechanicRepository();
                }
            }
        }
        return instance;
    }

    public void signIn(final String email, final String password,
                       final OnCompleteListener<AuthResult> listener){
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email,password).addOnCompleteListener(listener);
    }

    public void register(final MechanicEntity mechanic, final OnAsyncEventListener callback){
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                mechanic.getEmail(),
                mechanic.getPassword()
        ).addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                mechanic.setEmail(FirebaseAuth.getInstance().getCurrentUser().getEmail());
                insert(mechanic, callback);
            } else {
                callback.onFailure(task.getException());
            }
        });
    }

    public LiveData<MechanicEntity> getMechanic(){
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("mechanic")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        return new MechanicLiveData(reference);
    }
    public void insert(final MechanicEntity mechanic, OnAsyncEventListener callback){
        FirebaseDatabase.getInstance()
                .getReference("mechanic")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .setValue(mechanic, (databaseError, databaseReference) -> {
                    if (databaseError != null){
                        callback.onFailure(databaseError.toException());
                        FirebaseAuth.getInstance().getCurrentUser().delete()
                                .addOnCompleteListener(task -> {
                                    if (task.isSuccessful()){
                                        callback.onFailure(null);
                                        Log.d(TAG,"Rollback successful: User account deleted");
                                    } else {
                                        callback.onFailure(task.getException());
                                        Log.d(TAG, "Rollback failed: signInWithEmail:failure",
                                        task.getException());
                                    }
                                });
                    } else {
                        callback.onSuccess();
                    }
                });
    }

    public void update(final MechanicEntity mechanic, OnAsyncEventListener callback){
        FirebaseDatabase.getInstance()
                .getReference("clients")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .updateChildren(mechanic.toMap(), (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
        FirebaseAuth.getInstance().getCurrentUser().updatePassword(mechanic.getPassword())
                .addOnFailureListener(
                        e -> Log.d(TAG, "updatePassword failure!", e)
                );
    }

    public void delete(final MechanicEntity mechanic, OnAsyncEventListener callback){
        FirebaseDatabase.getInstance()
                .getReference("clients")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .removeValue((databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }

}
