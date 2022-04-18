package com.example.projet_bicycle_revisions.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.projet_bicycle_revisions.BaseApp;
import com.example.projet_bicycle_revisions.database.entity.MechanicEntity;
import com.example.projet_bicycle_revisions.database.repository.MechanicRepository;
import com.example.projet_bicycle_revisions.util.OnAsyncEventListener;

public class MechanicViewModel extends AndroidViewModel {

    private MechanicRepository repository;

    private Application application;

    private final MediatorLiveData<MechanicEntity> observableMechanic;

    public MechanicViewModel(@NonNull Application application, MechanicRepository mechanicRepository ) {
        super(application);

        this.application = application;
        repository= mechanicRepository;
        observableMechanic = new MediatorLiveData<>();
        observableMechanic.setValue(null);

        LiveData<MechanicEntity> mechanic = repository.getMechanic();
        observableMechanic.addSource(mechanic, observableMechanic::setValue);

    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;

        private final MechanicRepository repository;

        public Factory(@NonNull Application application) {
            this.application = application;
            repository = ((BaseApp) application).getMechanicRepository();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new MechanicViewModel(application, repository);
        }
    }

        public LiveData<MechanicEntity> getMechanic() {
            return observableMechanic;
        }


        public void updateMechanic(MechanicEntity mechanic, OnAsyncEventListener callback) {
            ((BaseApp) getApplication()).getMechanicRepository()
                    .update(mechanic, callback);
        }

        public void deleteMechanic(MechanicEntity mechanic, OnAsyncEventListener callback) {
            ((BaseApp) getApplication()).getMechanicRepository()
                    .delete(mechanic, callback);



    }
}
