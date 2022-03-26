package com.example.projet_bicycle_revisions.database.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.projet_bicycle_revisions.BaseApp;
import com.example.projet_bicycle_revisions.database.async.bike.CreateBike;
import com.example.projet_bicycle_revisions.database.async.bike.DeleteBike;
import com.example.projet_bicycle_revisions.database.async.bike.EditBike;
import com.example.projet_bicycle_revisions.database.entity.BikesEntity;
import com.example.projet_bicycle_revisions.util.OnAsyncEventListener;

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

    public LiveData<BikesEntity> getBike(final long id, Application application){
        return ((BaseApp)application).getDatabase().bikeDao().getBike(id);
    }

    public LiveData<List<BikesEntity>> getAllofMechanic(final String mechanicId, Application application){
        return ((BaseApp)application).getDatabase().bikeDao().getAllofMechanic(mechanicId);
    }
    public LiveData<List<BikesEntity>> getAllofMechanicByStatus(final String mechanicId,final boolean status, Application application){
        return ((BaseApp)application).getDatabase().bikeDao().getBikeByStatus(mechanicId,status);
    }

    public void insert(final BikesEntity bike, OnAsyncEventListener callback, Application application){
        new CreateBike(application,callback).execute(bike);
    }

    public void update(final BikesEntity bike, OnAsyncEventListener callback, Application application){
        new EditBike(application,callback).execute(bike);
    }

    public void delete(final BikesEntity bike, OnAsyncEventListener callback, Application application){
        new DeleteBike(application,callback).execute(bike);
    }
}
