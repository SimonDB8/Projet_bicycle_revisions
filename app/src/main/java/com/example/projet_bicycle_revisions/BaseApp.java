package com.example.projet_bicycle_revisions;

import android.app.Application;

import com.example.projet_bicycle_revisions.database.AppDatabase;
import com.example.projet_bicycle_revisions.database.repository.BikeRepository;
import com.example.projet_bicycle_revisions.database.repository.MechanicRepository;

public class BaseApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public AppDatabase getDatabase() {
        return AppDatabase.getInstance(this);
    }

    public BikeRepository getBikeRepository() {
        return BikeRepository.getInstance();
    }

    public MechanicRepository getMechanicRepository() {
        return MechanicRepository.getInstance();
    }
}
