package com.example.projet_bicycle_revisions.database.async.mechanic;

import android.app.Application;
import android.os.AsyncTask;

import com.example.projet_bicycle_revisions.BaseApp;
import com.example.projet_bicycle_revisions.database.entity.MechanicEntity;
import com.example.projet_bicycle_revisions.util.OnAsyncEventListener;

public class CreateMechanic extends AsyncTask<MechanicEntity, Void, Void> {

    private Application application;
    private OnAsyncEventListener callback;
    private Exception exception;

    public CreateMechanic(Application application, OnAsyncEventListener callback) {
        this.application = application;
        this.callback = callback;
    }

    @Override
    protected Void doInBackground(MechanicEntity... params) {
        try {
            for (MechanicEntity mechanic : params)
                ((BaseApp) application).getDatabase().mechanicDao()
                        .insert(mechanic);
        } catch (Exception e) {
            exception = e;
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        if (callback != null) {
            if (exception == null) {
                callback.onSuccess();
            } else {
                callback.onFailure(exception);
            }
        }
    }
}

