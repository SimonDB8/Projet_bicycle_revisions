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

import java.util.List;

public class ListBikeViewModel extends AndroidViewModel {

    private BikeRepository repository;

    private Application application;

    private final MediatorLiveData<List<BikesEntity>> observableBike;

    public ListBikeViewModel(@NonNull Application application, BikeRepository bikeRepository ) {
        super(application);

        this.application = application;
        repository= bikeRepository;
        observableBike = new MediatorLiveData<>();
        observableBike.setValue(null);

        LiveData<List<BikesEntity>> bike = repository.getBikeByStatus(false);

        observableBike.addSource(bike, observableBike::setValue);

    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;

        private final BikeRepository repository;

        public Factory(@NonNull Application application) {
            this.application = application;
            repository = ((BaseApp) application).getBikeRepository();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new ListBikeViewModel(application, repository);
        }
    }

    public LiveData<List<BikesEntity>> getAllBikesOfMechanic() {
        return observableBike;
    }

}
