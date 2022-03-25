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

    public MechanicViewModel(@NonNull Application application, final String email, MechanicRepository mechanicRepository ) {
        super(application);

        this.application = application;
        repository= mechanicRepository;
        observableMechanic = new MediatorLiveData<>();
        observableMechanic.setValue(null);

        LiveData<MechanicEntity> mechanic = repository.getMechanic(email,application);
        observableMechanic.addSource(mechanic, observableMechanic::setValue);

    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;

        private final String email;

        private final MechanicRepository repository;

        public Factory(@NonNull Application application, String email) {
            this.application = application;
            this.email = email;
            repository = ((BaseApp) application).getMechanicRepository();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new MechanicViewModel(application, email, repository);
        }
    }

        public LiveData<MechanicEntity> getMechanic() {
            return observableMechanic;
        }

        public void createMechanic(MechanicEntity mechanic, OnAsyncEventListener callback) {
            repository.insert(mechanic, callback, application);
        }

        public void updateMechanic(MechanicEntity mechanic, OnAsyncEventListener callback) {
            repository.update(mechanic, callback, application);
        }

        public void deleteMechanic(MechanicEntity mechanic, OnAsyncEventListener callback) {
            repository.delete(mechanic, callback, application);



    }
}
