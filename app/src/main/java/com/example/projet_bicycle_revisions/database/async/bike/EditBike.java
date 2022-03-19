package com.example.projet_bicycle_revisions.database.async.bike;

import android.app.Application;
import android.os.AsyncTask;

import com.example.projet_bicycle_revisions.BaseApp;
import com.example.projet_bicycle_revisions.database.entity.BikesEntity;
import com.example.projet_bicycle_revisions.util.OnAsyncEventListener;

public class EditBike extends AsyncTask<BikesEntity, Void, Void> {

    private Application application;
    private OnAsyncEventListener calback;
    private Exception exception;

    public EditBike(Application application, OnAsyncEventListener callback) {
        this.application = application;
        calback = callback;
    }

    @Override
    protected Void doInBackground(BikesEntity... params) {
        try {
            for (BikesEntity bike : params)
                ((BaseApp) application).getDatabase().bikeDao()
                        .update(bike);
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
