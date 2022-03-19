package com.example.projet_bicycle_revisions.database.async.mechanic;

import android.app.Application;
import android.os.AsyncTask;

import com.example.projet_bicycle_revisions.BaseApp;
import com.example.projet_bicycle_revisions.database.entity.MechanicEntity;
import com.example.projet_bicycle_revisions.util.OnAsyncEventListener;

public class EditMechanic extends AsyncTask<MechanicEntity, Void, Void> {

    private Application application;
    private OnAsyncEventListener calback;
    private Exception exception;

    public EditMechanic(Application application, OnAsyncEventListener callback) {
        this.application = application;
        calback = callback;
    }

    @Override
    protected Void doInBackground(MechanicEntity... params) {
        try {
            for (MechanicEntity mechanic : params)
                ((BaseApp) application).getDatabase().mechanicDao()
                        .update(mechanic);
        } catch (Exception e) {
            exception = e;
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        if (calback != null) {
            if (exception == null) {
                calback.onSuccess();
            } else {
                calback.onFailure(exception);
            }
        }
    }
}
