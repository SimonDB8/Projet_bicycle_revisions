package com.example.projet_bicycle_revisions.database.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.projet_bicycle_revisions.BaseApp;
import com.example.projet_bicycle_revisions.database.async.mechanic.CreateMechanic;
import com.example.projet_bicycle_revisions.database.async.mechanic.DeleteMechanic;
import com.example.projet_bicycle_revisions.database.async.mechanic.EditMechanic;
import com.example.projet_bicycle_revisions.database.entity.MechanicEntity;
import com.example.projet_bicycle_revisions.util.OnAsyncEventListener;

public class MechanicRepository {

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

    public LiveData<MechanicEntity> getMechanic(final String email, Application application){
        return ((BaseApp)application).getDatabase().mechanicDao().getMechanic(email);
    }
    public void insert(final MechanicEntity mechanic, OnAsyncEventListener callback, Application application){
        new CreateMechanic(application,callback).execute(mechanic);
    }

    public void update(final MechanicEntity mechanic, OnAsyncEventListener callback, Application application){
        new EditMechanic(application,callback).execute(mechanic);
    }

    public void delete(final MechanicEntity mechanic, OnAsyncEventListener callback, Application application){
        new DeleteMechanic(application,callback).execute(mechanic);
    }

}
