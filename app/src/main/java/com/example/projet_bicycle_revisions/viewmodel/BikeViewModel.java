package com.example.projet_bicycle_revisions.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.projet_bicycle_revisions.BaseApp;
import com.example.projet_bicycle_revisions.database.entity.BikesEntity;
import com.example.projet_bicycle_revisions.database.repository.BikeRepository;
import com.example.projet_bicycle_revisions.util.OnAsyncEventListener;

public class BikeViewModel extends AndroidViewModel {

    private BikeRepository repository;

    private Application application;

    private final MediatorLiveData<BikesEntity> observableBike;

    public BikeViewModel(@NonNull Application application, final long id, BikeRepository bikeRepository ) {
        super(application);

        this.application = application;
        repository= bikeRepository;
        observableBike = new MediatorLiveData<>();
        observableBike.setValue(null);

        LiveData<BikesEntity> bike = repository.getBike(id,application);
        observableBike.addSource(bike, observableBike::setValue);

    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;

        private final long id;

        private final BikeRepository repository;

        public Factory(@NonNull Application application, long id) {
            this.application = application;
            this.id = id;
            repository = ((BaseApp) application).getBikeRepository();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new BikeViewModel(application, id, repository);
        }
    }

    public LiveData<BikesEntity> getBike() {
        return observableBike;
    }

    public void createBike(BikesEntity bike, OnAsyncEventListener callback) {
        repository.insert(bike, callback, application);
    }

    public void updateBike(BikesEntity bike, OnAsyncEventListener callback) {
        repository.update(bike, callback, application);
    }

    public void deleteBike(BikesEntity bike, OnAsyncEventListener callback) {
        repository.delete(bike, callback, application);



    }
}
